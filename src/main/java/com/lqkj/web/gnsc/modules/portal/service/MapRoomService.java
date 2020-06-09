package com.lqkj.web.gnsc.modules.portal.service;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.base.BaseService;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapRoomDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapRoomExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.model.excel.MapRoomExcelModel;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapRoomVO;
import com.lqkj.web.gnsc.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class MapRoomService extends BaseService {
    @Autowired
    MapRoomDao mapRoomDao;
    @Autowired
    MapRoomImgService roomImgService;
    @Autowired
    MapRoomExtendsService roomExtendsService;
    @Autowired
    MapRtExtendsDefineService rtExtendsDefineService;
    @Autowired
    MapRoomTypeService roomTypeService;
    @Autowired
    SchoolCampusDao campusDao;
    @Autowired
    MapRoomExcelModelDao mapRoomExcelModelDao;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private boolean dataTransferStatus = true;


    public Page<MapRoom> pageQuery(Long buildingMapCode, Integer campusCode, Integer typeCode, String roomName, Integer page, Integer pageSize) {

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        Page<MapRoom> pageInfo =  mapRoomDao.findAll(loadExample(buildingMapCode, campusCode, typeCode, roomName), pageRequest);
        try {
            List<LinkedHashMap> linkedHashMapList = new ArrayList<>();
            for(MapRoom room : pageInfo.getContent()){

                room.setCampusName(campusDao.findByVectorZoomCode(room.getCampusCode()).getCampusName());
                room.setMapRoomImgList(roomImgService.queryListWithRoomCode(room.getRoomCode()));
                room.setMapRoomExtendsList(roomExtendsService.queryList(room.getRoomCode()));
            }
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<MapRoom> listQuery(Long buildingMapCode, Integer campusCode, Integer typeCode, String RoomName) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapRoomDao.findAll(loadExample(buildingMapCode, campusCode, typeCode, RoomName), sort);
    }

    public MapRoom add(MapRoom mapRoomVO) {
        return mapRoomDao.save(mapRoomVO);
    }

    public MapRoomVO get(Integer roomCode) {
        try {
            MapRoomVO mapRoomVO = objectMapper.readValue(JSON.toJSONString(mapRoomDao.findById(roomCode).get()), MapRoomVO.class);
            mapRoomVO.setMapRoomExtendsList(
                   roomExtendsService.queryList(roomCode));
            mapRoomVO.setMapRoomImgList(
                    roomImgService.queryListWithRoomCode(roomCode));
            mapRoomVO.setGeoJson(loadGeoJson(mapRoomVO.getMapCode()));

            return mapRoomVO;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    public MessageBean update(MapRoom mapRoom) {
        mapRoom.setSynStatus(false);
        mapRoomDao.save(mapRoom);

        List<MapRoomImg> mapRoomImgList  = mapRoom.getMapRoomImgList();
        roomImgService.deleteAllByRoomCode(mapRoom.getRoomCode());
        if(mapRoomImgList != null && mapRoomImgList.size() > 0){
            roomImgService.saveImgList(mapRoomImgList);
        }

        List<MapRoomExtends> mapRoomExtendsList = mapRoom.getMapRoomExtendsList();
        roomExtendsService.save(mapRoomExtendsList);

        // 更新一个边/面的名称，同时更新搜索引擎
//        try {
//            String reStr = updateWayName(
//                    String.valueOf(mapRoom.getCampusCode()),
//                    mapRoom.getRoomName(),
//                    "",
//                    mapRoom.getBrief(),
//                    mapRoom.getHourseNumber(),
//                    mapRoom.getAlias(),
//                    mapRoom.getEnName(),
//                    mapRoom.getMapCode());
//            logger.info("推送cmgis:"+reStr);
//        }catch (Exception e){
//            logger.info(e.getMessage(),e);
//            return MessageBean.construct(mapRoom,"推送cmgis失败");
//        }
        return MessageBean.construct(mapRoom,"更新成功");
    }

    public Integer delete(Integer roomCode) {
        // 删除搜索引擎索引
        MapRoom mapRoom = mapRoomDao.findById(roomCode).get();
        MapIndexTemplate mapIndexTemplate = new MapIndexTemplate(SystemType.map,
                mapRoom.getMapCode().toString(), GeomType.polygon, mapRoom.getCampusCode());
        String result = removeSearchIndex(mapIndexTemplate);
        logger.info(result);
        mapRoom.setDelete(true);
        mapRoom.setSynStatus(false);
        mapRoomDao.save(mapRoom);
        return 1;
    }

    public Integer bulkDelete(String ids) {
        Integer[] idArray = Arrays
                .stream(ids.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new)
                ;
        List<MapRoomVO> roomVOList = JSON
                .parseArray(JSON.toJSONString(mapRoomDao.queryWithRoomCodes(idArray)), MapRoomVO.class);
        String result = bulkRemoveSearchIndex(loadPushMapIndexTemplateFromRoom(roomVOList));
        logger.info(result);
        for (Integer id : idArray) {
            MapRoom mapRoom = mapRoomDao.findById(id).get();
            mapRoom.setDelete(true);
            mapRoom.setSynStatus(false);
            mapRoomDao.save(mapRoom);
        }
        return idArray.length;
    }

    public InputStream exportTemplate() throws IOException {
        List<MapRoomType> typeList = roomTypeService.queryList(null);
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapRoomType roomType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, null);
            sheet.setSheetName(ExcelUtils.removeSpecialCharacter(roomType.getTypeName()));
            sheet.setHead(loadHeadList(roomType.getTypeCode()));

            writer.write(new ArrayList<>(), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public InputStream download(String userId) throws IOException {
        List<MapRoomType> typeList = roomTypeService.queryList(null);
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapRoomType roomType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, ExcelModel.class);
            sheet.setSheetName(ExcelUtils.removeSpecialCharacter(roomType.getTypeName()));
            sheet.setHead(loadHeadList(roomType.getTypeCode()));

            writer.write(loadExportList(roomType.getTypeCode()), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public void upload(InputStream inputStream)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        List<MapRoomType> typeList = roomTypeService.queryList(null);

        AnalysisEventListener<ExcelModel> listener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);

        DataImportLog dataImportLog = new DataImportLog();
        dataImportLog.setCategory("房间信息导入");

        if(excelReader.getSheets().size() != typeList.size()){
            dataImportLog.setErrorCode(-1);
            dataImportLog.setErrorMsg("请重新下载数据导入模板");
        } else{
            List<MapRoomVO> roomVOList = new ArrayList<>();
            for(int i = 0; i < typeList.size(); i++){
                excelReader.read(new Sheet(i + 1, 1, ExcelModel.class));
                List<Object> dataList = ((ExcelListener) listener).getDatas();

                MapRoomType roomType = typeList.get(i);
                List<MapRtExtendsDefine> rtExtendsDefineList = rtExtendsDefineService.queryList(roomType.getTypeCode());

                dataImportLog.setSubCategory(roomType.getTypeName());
                dataImportLog.setSubTotalCount(dataList.size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());
                for(int j = 0; j < dataImportLog.getSubTotalCount(); j++){
                    ExcelModel excelModel = (ExcelModel) dataList.get(j);
                    MapRoomVO roomVO = transferFromExcelModel(roomType.getTypeCode(),
                            rtExtendsDefineList, excelModel);
                    if(dataTransferStatus){
                        if(roomVO.getRoomCode() == null){
                            if(mapRoomDao.existsByMapCode(roomVO.getMapCode())){
                                roomVO.setErrMsg(roomVO.getErrMsg() + "地图编码不能重复；");
                                dataImportLog.addError(true, roomVO);
                            } else{
                                // 添加信息
                                MapRoomVO newRoomVO = JSON.parseObject(
                                       JSON.toJSONString(this.add(loadFromMapRoomVO(roomVO))),
                                        MapRoomVO.class);

                                // 添加扩展属性
                                List<MapRoomExtends> roomExtendsList = roomVO
                                        .getMapRoomExtendsList();
                                for(MapRoomExtends roomExtends : roomExtendsList){
                                    roomExtends.setRoomCode(newRoomVO.getRoomCode());
                                }
                                roomExtendsService.save(roomExtendsList);
                                // 添加图片
                                List<MapRoomImg> roomImgList = roomVO.getMapRoomImgList();
                                if(roomImgList != null && roomImgList.size() > 0){
                                    for(MapRoomImg roomImg : roomImgList){
                                        roomImg.setRoomCode(newRoomVO.getRoomCode());
                                    }
                                    roomImgService.saveImgList(roomImgList);
                                }

                                roomVOList.add(newRoomVO);
                            }
                        } else {
                            // 更新信息
                            this.update(loadFromMapRoomVO(roomVO));

                            roomVOList.add(roomVO);
                        }
                        dataImportLog.addImportedCount(true);
                    } else {
                        dataImportLog.addError(true, roomVO);
                    }
                }
                ((ExcelListener) listener).clearData();
            }
            String result = bulkPushSearchIndex(loadPushMapIndexTemplateFromRoom(roomVOList));
            logger.info(result);
        }
    }



    private JSONObject loadGeoJson(Long mapCode){
        Long[] id = new Long[]{mapCode};
        JSONObject jsonObject = JSON.parseObject(queryWayGeoJson( id));
        return jsonObject.getJSONArray("features").getJSONObject(0);
    }

    private Example<MapRoom> loadExample(Long buildingMapCode, Integer campusCode, Integer typeCode, String roomName){
        MapRoom mapRoom = new MapRoom();
        mapRoom.setCampusCode(campusCode);
        mapRoom.setTypeCode(typeCode);
        mapRoom.setRoomName(roomName);
        mapRoom.setBuildingMapCode(buildingMapCode);
        mapRoom.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("roomName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("buildingMapCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("roomCode");

        return Example.of(mapRoom, exampleMatcher);
    }

    private MapRoom loadFromMapRoomVO(MapRoomVO mapRoomVO){
        MapRoom mapRoom = new MapRoom();

        if(mapRoomVO.getRoomCode() != null){
            mapRoom = mapRoomDao.findById(mapRoomVO.getRoomCode()).get();
        }

        mapRoom.setAlias(mapRoomVO.getAlias());
        mapRoom.setBuildingMapCode(mapRoomVO.getBuildingMapCode());
        mapRoom.setBuildingName(mapRoomVO.getBuildingName());
        mapRoom.setCampusCode(mapRoomVO.getCampusCode());
        mapRoom.setEnName(mapRoomVO.getEnName());
        mapRoom.setHourseNumber(mapRoomVO.getHourseNumber());
        mapRoom.setMapCode(mapRoomVO.getMapCode());
        mapRoom.setMemo(mapRoomVO.getMemo());
        mapRoom.setOrderId(mapRoomVO.getOrderId());
        mapRoom.setRoomCode(mapRoomVO.getRoomCode());
        mapRoom.setRoomName(mapRoomVO.getRoomName());
        mapRoom.setTypeCode(mapRoomVO.getTypeCode());
        mapRoom.setBrief(mapRoomVO.getBrief());
        mapRoom.setLeaf(mapRoomVO.getLeaf());
        mapRoom.setVersionCode(mapRoomVO.getVersionCode());
        mapRoom.setSynStatus(false);
        mapRoom.setDelete(false);
        return mapRoom;
    }

    private List<List<String>> loadHeadList(Integer typeCode){
        String headString = "编号,名称,英文名称,别名"
                + ",门牌号,校区编码,地图编码,楼层编码"
                + ",所属大楼编号,所属大楼名称,简介,同步状态(true/false)"
                + ",删除状态(true/false),版本号,图片,排序,备注";

        List<MapRtExtendsDefine> rtExtendsDefineList = rtExtendsDefineService.loadAll();
        for(MapRtExtendsDefine extendsDefine : rtExtendsDefineList){
            headString += "," + extendsDefine.getColumnCnname();
        }
        return ExcelUtils.loadHead(headString);
    }

    private List<ExcelModel> loadExportList(Integer typeCode){
        List<ExcelModel> list = new ArrayList<>();
        List<MapRoomVO> roomList = JSON.parseArray(
                JSON.toJSONString(mapRoomExcelModelDao.queryExcelList(typeCode)), MapRoomVO.class);
        for(MapRoomVO room : roomList){
            List<String> columns = new ArrayList<>();
            columns.add(room.getRoomCode().toString());
            columns.add(room.getRoomName());
            columns.add(room.getEnName());
            columns.add(room.getAlias());
            columns.add(room.getHourseNumber());
            columns.add(room.getCampusCode().toString());
            columns.add(room.getMapCode().toString());
            columns.add(room.getLeaf().toString());
            if(room.getBuildingMapCode() != null){
                columns.add(room.getBuildingMapCode().toString());
            }
            columns.add(room.getBuildingName());
            columns.add(room.getBrief());
            columns.add(room.getSynStatus() ? "TRUE" : "FALSE");
            columns.add(room.getDelete() ? "TRUE" : "FALSE");
            columns.add(room.getVersionCode() == null ? "" : room.getVersionCode().toString());
            columns.add(room.getPicUrl());
            columns.add(room.getOrderId() == null ? "" : room.getOrderId().toString());
            columns.add(room.getMemo());

            List<MapRoomExtends> extendsList = roomExtendsService.queryList(room.getRoomCode());
            for(int i = 0; i < extendsList.size(); i++){
                columns.add(extendsList.get(i).getExtendsValue());
            }
            list.add(ExcelUtils.loadExcelModel(columns));
        }
        return list;
    }

    /**
     * 读取属性构建实体对象
     * @param typeCode 分类代码
     * @param extendsList 分类扩展属性列表
     * @param excelModel excelReader读取出来的model
     * @return
     */
    private MapRoomVO transferFromExcelModel(Integer typeCode,
                                             List<MapRtExtendsDefine> extendsList,
                                             ExcelModel excelModel)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapRoomVO roomVO = new MapRoomVO();

        dataTransferStatus = true;
        String errMsg = "";
        if(excelModel.getColumn1() != null){
            if(StringUtils.isNumeric(excelModel.getColumn1())){
                roomVO.setRoomCode(Integer.parseInt(excelModel.getColumn1()));
            }else{
                dataTransferStatus = false;
                errMsg += "大楼编码必须是数字；";
            }

        }
        roomVO.setTypeCode(typeCode);

        roomVO.setRoomName(excelModel.getColumn2());
        roomVO.setEnName(excelModel.getColumn3());
        roomVO.setAlias(excelModel.getColumn4());
        roomVO.setHourseNumber(excelModel.getColumn5());
        if(excelModel.getColumn6() != null && StringUtils.isNumeric(excelModel.getColumn6())){
            roomVO.setCampusCode(Integer.parseInt(excelModel.getColumn6()));
        } else {
            dataTransferStatus = false;
            errMsg += "校区编码必填且是数字；";
        }

        if(excelModel.getColumn7() != null && StringUtils.isNumeric(excelModel.getColumn7())){
            roomVO.setMapCode(Long.parseLong(excelModel.getColumn7()));
        }else{
            dataTransferStatus = false;
            errMsg += "地图编号必填且数字；";
        }

        if(excelModel.getColumn8() != null && StringUtils.isNumeric(excelModel.getColumn8())){
            roomVO.setLeaf(Integer.parseInt(excelModel.getColumn8()));
        }else{
            dataTransferStatus = false;
            errMsg += "楼层必填且数字；";
        }

        if(excelModel.getColumn9() != null && StringUtils.isNumeric(excelModel.getColumn9())){
            roomVO.setBuildingMapCode(Long.parseLong(excelModel.getColumn9()));
        }else{
            dataTransferStatus = false;
            errMsg += "所属大楼地图编号必填且数字；";
        }

        roomVO.setBuildingName(excelModel.getColumn10());
        roomVO.setBrief(excelModel.getColumn11());

        // 同步状态
        if(excelModel.getColumn12() != null && "true".equals(excelModel.getColumn12().toLowerCase())){
            roomVO.setSynStatus(true);
        } else {
            roomVO.setSynStatus(false);
        }
        // 删除状态
        if(excelModel.getColumn13() != null && "true".equals(excelModel.getColumn13().toLowerCase())){
            roomVO.setDelete(true);
        } else {
            roomVO.setDelete(false);
        }
        // 版本号
        if(excelModel.getColumn14() != null && StringUtils.isNumeric(excelModel.getColumn14())){
            roomVO.setVersionCode(Integer.parseInt(excelModel.getColumn14()));
        }

        if(StringUtils.isNotBlank(excelModel.getColumn15())){
            List<MapRoomImg> imgList = new ArrayList<>();
            String[] pics = excelModel.getColumn15().trim().split(",");
            for(String str : pics){
                imgList.add(new MapRoomImg(roomVO.getRoomCode(), str));
            }
            roomVO.setMapRoomImgList(imgList);
        }

        if(excelModel.getColumn16() != null && StringUtils.isNumeric(excelModel.getColumn16())){
            roomVO.setOrderId(Integer.parseInt(excelModel.getColumn16()));
        }
        roomVO.setMemo(excelModel.getColumn17());
        if(errMsg.length() > 0){
            roomVO.setErrMsg(errMsg);
        }


        List<MapRoomExtends> roomExtendsList = new ArrayList<>();
        for(int i = 0; i < extendsList.size(); i++){
            MapRtExtendsDefine extendsDefine = extendsList.get(i);
            String getMethod = "getColumn" + (18 + i);
            Object extendsValueObject = excelModel.getClass()
                    .getDeclaredMethod(getMethod, null)
                    .invoke(excelModel);
            String extendsValue = extendsValueObject == null ? null : String.valueOf(extendsValueObject);
            MapRoomExtends roomExtends = new MapRoomExtends();
            roomExtends.setRoomCode(roomVO.getRoomCode());
            roomExtends.setColumnId(extendsDefine.getColumnId());
            roomExtends.setTypeCode(extendsDefine.getTypeCode());
            roomExtends.setExtendsValue(extendsValue);

            roomExtendsList.add(roomExtends);
        }

        roomVO.setMapRoomExtendsList(roomExtendsList);
        roomVO.setMapRoomImgList(new ArrayList<>());
        return roomVO;
    }
}
