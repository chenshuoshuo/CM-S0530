package com.lqkj.web.gnsc.modules.portal.service;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.base.BaseService;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapOthersPolygonExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapOthersPolygonVO;
import com.lqkj.web.gnsc.utils.*;
import com.vividsolutions.jts.geom.Geometry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@Transactional
public class MapOthersPolygonService extends BaseService {
    @Autowired
    MapOthersPolygonDao mapOthersPolygonDao;
    @Autowired
    private SchoolCampusDao campusDao;
    @Autowired
    MapOthersPolygonImgService othersPolygonImgService;
    @Autowired
    MapOthersPolygonTypeService othersPolygonTypeService;
    @Autowired
    MapOptExtendsDefineService optExtendsDefineService;
    @Autowired
    MapOthersPolygonExtendsService othersPolygonExtendsService;
    @Autowired
    MapOthersPolygonExcelModelDao mapOthersPolygonExcelModelDao;

    private boolean dataTransferStatus = true;

    protected static ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public UtilPage<MapOthersPolygonVO> pageQuery(Integer campusCode, Integer typeCode, String polygonName, Integer page, Integer pageSize) {
        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        Page<MapOthersPolygon> pageInfo = mapOthersPolygonDao.findAll(loadExample(campusCode, typeCode, polygonName), pageRequest);

        try {
            UtilPage utilPage = objectMapper.readValue(JSON.toJSONString(pageInfo),UtilPage.class);
            List<LinkedHashMap> linkedHashMapList = new ArrayList<>();
            for(Object object : utilPage.getContent()){
                LinkedHashMap linkedHashMap = (LinkedHashMap) object;
                linkedHashMap.put("mapOthersPolygonImgList",othersPolygonImgService.queryListWithPolygonCode((Integer) linkedHashMap.get("polygonCode")));
                linkedHashMap.put("mapOthersPolygonType",othersPolygonTypeService.queryById((Integer) linkedHashMap.get("typeCode")));
                linkedHashMap.put("campusName", campusDao.findByVectorZoomCode((Integer)linkedHashMap.get("campusCode")).getCampusName());
                linkedHashMapList.add(linkedHashMap);
            }
            utilPage.setContent(linkedHashMapList);
            return utilPage;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }


    public List<MapOthersPolygon> listQuery(Integer campusCode, Integer typeCode, String polygonName) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapOthersPolygonDao.findAll(loadExample(campusCode, typeCode, polygonName), sort);
    }

    public MapOthersPolygon add(MapOthersPolygon mapOthersPolygon) {
        return mapOthersPolygonDao.save(mapOthersPolygon);
    }

    public MapOthersPolygonVO get(Integer polygonCode) {
        try {
            MapOthersPolygonVO mapOthersPolygonVO = objectMapper.readValue(
                    JSON.toJSONString(mapOthersPolygonDao.findById(polygonCode).get()),
                    MapOthersPolygonVO.class);
            mapOthersPolygonVO.setMapOthersPolygonExtendsList(othersPolygonExtendsService.queryList(polygonCode));
            mapOthersPolygonVO.setMapOthersPolygonImgList(othersPolygonImgService.queryListWithPolygonCode(polygonCode));
            mapOthersPolygonVO.setGeoJson(loadGeoJson(mapOthersPolygonVO.getMapCode()));

            return mapOthersPolygonVO;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public MessageBean<MapOthersPolygon> update(MapOthersPolygon mapOthersPolygon) {
        mapOthersPolygon.setSynStatus(false);
        mapOthersPolygonDao.save(mapOthersPolygon);

        List<MapOthersPolygonImg> mapOthersPolygonImgList  = mapOthersPolygon.getMapOthersPolygonImgList();
        othersPolygonImgService.deleteAllByPolygonCode(mapOthersPolygon.getPolygonCode());
        if(mapOthersPolygonImgList != null && mapOthersPolygonImgList.size() > 0){
            othersPolygonImgService.saveImgList(mapOthersPolygonImgList);
        }

        List<MapOthersPolygonExtends> mapOthersPolygonExtendsList = mapOthersPolygon.getMapOthersPolygonExtendsList();
        othersPolygonExtendsService.save(mapOthersPolygonExtendsList);


        // 更新一个边/面的名称，同时更新搜索引擎
//        try {
//            String reStr = updateWayName(
//                    String.valueOf(mapOthersPolygon.getCampusCode()),
//                    mapOthersPolygon.getPolygonName(),
//                    "",
//                    mapOthersPolygon.getBrief(),
//                    "",
//                    mapOthersPolygon.getAlias(),
//                    mapOthersPolygon.getEnName(),
//                    mapOthersPolygon.getMapCode());
//            logger.info("推送cmgis:"+reStr);
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//            return MessageBean.construct(mapOthersPolygonVO,"推送cmigs失败");
//        }
        return MessageBean.construct(mapOthersPolygon,"更新成功");
    }

    public Integer delete(Integer polygonCode) {
        // 删除索引
        MapOthersPolygon othersPolygon = mapOthersPolygonDao.findById(polygonCode).get();
        othersPolygon.setSynStatus(false);
        othersPolygon.setDelete(true);
        mapOthersPolygonDao.save(othersPolygon);

        MapIndexTemplate mapIndexTemplate = new MapIndexTemplate(
                SystemType.map, polygonCode.toString(), GeomType.polygon, othersPolygon.getCampusCode());
        String result = removeSearchIndex(mapIndexTemplate);
        logger.info(result);
        return 1;
    }

    public Integer bulkDelete(String ids) {
        Integer[] idArray = Arrays
                .stream(ids.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new)
                ;
        List<MapOthersPolygonVO> othersPolygonVOList = JSON
                .parseArray(JSON.toJSONString(mapOthersPolygonDao.queryWithPolygonCodes(idArray)),
                        MapOthersPolygonVO.class);
        String result = bulkRemoveSearchIndex(loadPushMapIndexTemplateFromOthersPolygon(othersPolygonVOList));
        logger.info(result);
        for(Integer str : idArray){
            MapOthersPolygon othersPolygon = mapOthersPolygonDao.findById(str).get();
            othersPolygon.setSynStatus(false);
            othersPolygon.setDelete(true);
            mapOthersPolygonDao.save(othersPolygon);
        }
        return idArray.length;
    }

    public InputStream exportTemplate() throws IOException {
        List<MapOthersPolygonType> typeList = othersPolygonTypeService.queryList(null);
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapOthersPolygonType polygonType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, null);
            sheet.setSheetName(ExcelUtils.removeSpecialCharacter(polygonType.getTypeName()));
            sheet.setHead(loadHeadList(polygonType.getTypeCode()));

            writer.write(new ArrayList<>(), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public InputStream download(String userId) throws IOException {
        List<MapOthersPolygonType> typeList = othersPolygonTypeService.queryList(null);
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapOthersPolygonType polygonType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, ExcelModel.class);
            sheet.setSheetName(ExcelUtils.removeSpecialCharacter(polygonType.getTypeName()));
            sheet.setHead(loadHeadList(polygonType.getTypeCode()));

            writer.write(loadExportList(polygonType.getTypeCode()), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public void upload(InputStream inputStream)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        List<MapOthersPolygonType> typeList = othersPolygonTypeService.queryList(null);

        AnalysisEventListener<ExcelModel> listener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);

        DataImportLog dataImportLog = new DataImportLog();
        dataImportLog.setCategory("其他面图元信息导入");

        if(excelReader.getSheets().size() != typeList.size()){
            dataImportLog.setErrorCode(-1);
            dataImportLog.setErrorMsg("请重新下载数据导入模板");
        } else{
            List<MapOthersPolygonVO> polygonVOList = new ArrayList<>();
            for(int i = 0; i < typeList.size(); i++){
                excelReader.read(new Sheet(i + 1, 1, ExcelModel.class));
                List<Object> dataList = ((ExcelListener) listener).getDatas();

                MapOthersPolygonType polygonType = typeList.get(i);
                List<MapOptExtendsDefine> extendsList = optExtendsDefineService.loadAll();

                dataImportLog.setSubCategory(polygonType.getTypeName());
                dataImportLog.setSubTotalCount(dataList.size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());
                for(int j = 0; j < dataImportLog.getSubTotalCount(); j++){
                    ExcelModel excelModel = (ExcelModel) dataList.get(j);
                    MapOthersPolygonVO polygonVO = transferFromExcelModel(polygonType.getTypeCode(),
                            extendsList, excelModel);
                    if(dataTransferStatus){
                        if(polygonVO.getPolygonCode() == null){
                            if(mapOthersPolygonDao.existsByMapCode(polygonVO.getMapCode())){
                                polygonVO.setErrMsg(polygonVO.getErrMsg() + "地图编码不能重复；");
                                dataImportLog.addError(true, polygonVO);
                            } else{
                                // 添加信息
                                MapOthersPolygonVO newPolygonVO = JSON.parseObject(
                                        JSON.toJSONString(this.add(loadFromMapOthersPolygonVO(polygonVO))),
                                        MapOthersPolygonVO.class);

                                // 添加扩展属性
                                List<MapOthersPolygonExtends> polygonExtendsList = polygonVO
                                        .getMapOthersPolygonExtendsList();
                                for(MapOthersPolygonExtends polygonExtends : polygonExtendsList){
                                    polygonExtends.setPolygonCode(newPolygonVO.getPolygonCode());
                                }
                                othersPolygonExtendsService.save(polygonExtendsList);
                                polygonVOList.add(newPolygonVO);
                            }
                        } else {
                            // 更新信息
                            this.update(loadFromMapOthersPolygonVO(polygonVO));
                            polygonVOList.add(polygonVO);
                        }
                        dataImportLog.addImportedCount(true);
                    } else {
                        dataImportLog.addError(true, polygonVO);
                    }
                }
                ((ExcelListener) listener).clearData();
            }
            String result = bulkPushSearchIndex(loadPushMapIndexTemplateFromOthersPolygon(polygonVOList));
            logger.info(result);
        }
    }


    private JSONObject loadGeoJson(Long mapCode){
        Long[] id = new Long[]{mapCode};
        JSONObject jsonObject = JSON.parseObject(queryWayGeoJson( id));
        return jsonObject.getJSONArray("features").getJSONObject(0);
    }

    private MapOthersPolygon loadFromMapOthersPolygonVO(MapOthersPolygonVO mapOthersPolygonVO){
        MapOthersPolygon mapOthersPolygon = new MapOthersPolygon();

        if(mapOthersPolygonVO.getPolygonCode() != null){
            mapOthersPolygon = mapOthersPolygonDao.findById(mapOthersPolygonVO.getPolygonCode()).get();
        }

        mapOthersPolygon.setAlias(mapOthersPolygonVO.getAlias());
        mapOthersPolygon.setCampusCode(mapOthersPolygonVO.getCampusCode());
        mapOthersPolygon.setEnName(mapOthersPolygonVO.getEnName());
        mapOthersPolygon.setMapCode(mapOthersPolygonVO.getMapCode());
        mapOthersPolygon.setMemo(mapOthersPolygonVO.getMemo());
        mapOthersPolygon.setOrderId(mapOthersPolygonVO.getOrderId());
        mapOthersPolygon.setPolygonCode(mapOthersPolygonVO.getPolygonCode());
        mapOthersPolygon.setPolygonName(mapOthersPolygonVO.getPolygonName());
        mapOthersPolygon.setTypeCode(mapOthersPolygonVO.getTypeCode());
        mapOthersPolygon.setBrief(mapOthersPolygonVO.getBrief());
        mapOthersPolygon.setLeaf(mapOthersPolygonVO.getLeaf());
        mapOthersPolygon.setVersionCode(mapOthersPolygonVO.getVersionCode());
        mapOthersPolygon.setDelete(false);
        mapOthersPolygon.setSynStatus(false);

        return mapOthersPolygon;
    }

    private List<List<String>> loadHeadList(Integer typeCode){
        String headString = "编号,名称,校区编号,地图编号,楼层编码"
                + ",英文名称,别名,简介,同步状态(true/false)"
                + ",删除状态(true/false),版本号,图片地址,排序,备注";

        List<MapOptExtendsDefine> btExtendsDefineList = optExtendsDefineService.queryList(typeCode);
        for(MapOptExtendsDefine extendsDefine : btExtendsDefineList){
            headString += "," + extendsDefine.getColumnCnname();
        }
        return ExcelUtils.loadHead(headString);
    }

    private List<ExcelModel> loadExportList(Integer typeCode){
        List<ExcelModel> list = new ArrayList<>();
        List<MapOthersPolygonVO> polygonList = JSON.parseArray(
                JSON.toJSONString(mapOthersPolygonExcelModelDao.queryExcelList(typeCode)), MapOthersPolygonVO.class);
        for(MapOthersPolygonVO polygon : polygonList){
            List<String> columns = new ArrayList<>();
            columns.add(polygon.getPolygonCode().toString());
            columns.add(polygon.getPolygonName());
            columns.add(polygon.getCampusCode().toString());
            columns.add(polygon.getMapCode().toString());
            columns.add(polygon.getLeaf() == null ? null : polygon.getLeaf().toString());
            columns.add(polygon.getEnName());
            columns.add(polygon.getAlias());
            columns.add(polygon.getBrief());
            columns.add(polygon.getSynStatus() ? "TRUE" : "FALSE");
            columns.add(polygon.getDelete() ? "TRUE" : "FALSE");
            columns.add(polygon.getVersionCode() == null ? "" : polygon.getVersionCode().toString());
            columns.add(polygon.getPicUrl());
            columns.add(polygon.getOrderId() == null ? "" : polygon.getOrderId().toString());
            columns.add(polygon.getMemo());

            List<MapOthersPolygonExtends> extendsList = othersPolygonExtendsService.queryList(polygon.getPolygonCode());
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
    private MapOthersPolygonVO transferFromExcelModel(Integer typeCode,
                                                      List<MapOptExtendsDefine> extendsList,
                                                      ExcelModel excelModel)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapOthersPolygonVO polygonVO = new MapOthersPolygonVO();

        dataTransferStatus = true;
        String errMsg = "";
        if(excelModel.getColumn1() != null){
            if(StringUtils.isNumeric(excelModel.getColumn1())){
                polygonVO.setPolygonCode(Integer.parseInt(excelModel.getColumn1()));
            }else{
                dataTransferStatus = false;
                errMsg += "编码必须是数字；";
            }

        }
        polygonVO.setTypeCode(typeCode);

        polygonVO.setPolygonName(excelModel.getColumn2());
        if(excelModel.getColumn3() != null && StringUtils.isNumeric(excelModel.getColumn3())){
            polygonVO.setCampusCode(Integer.parseInt(excelModel.getColumn3()));
        } else {
            dataTransferStatus = false;
            errMsg += "校区编码必填且是数字；";
        }

        if(excelModel.getColumn4() != null && StringUtils.isNumeric(excelModel.getColumn4())){
            polygonVO.setMapCode(Long.parseLong(excelModel.getColumn4()));
        }else{
            dataTransferStatus = false;
            errMsg += "地图编号必填且数字；";
        }
        if(excelModel.getColumn5() != null && StringUtils.isNumeric(excelModel.getColumn5())){
            polygonVO.setLeaf(Integer.parseInt(excelModel.getColumn5()));
        }
        polygonVO.setEnName(excelModel.getColumn6());
        polygonVO.setAlias(excelModel.getColumn7());
        polygonVO.setBrief(excelModel.getColumn8());
        // 同步状态
        if(excelModel.getColumn9() != null && "true".equals(excelModel.getColumn9().toLowerCase())){
            polygonVO.setSynStatus(true);
        } else {
            polygonVO.setSynStatus(false);
        }
        // 删除状态
        if(excelModel.getColumn10() != null && "true".equals(excelModel.getColumn10().toLowerCase())){
            polygonVO.setDelete(true);
        } else {
            polygonVO.setDelete(false);
        }
        // 版本号
        if(excelModel.getColumn11() != null && StringUtils.isNumeric(excelModel.getColumn11())){
            polygonVO.setVersionCode(Integer.parseInt(excelModel.getColumn11()));
        }

        if(StringUtils.isNotBlank(excelModel.getColumn12())){
            List<MapOthersPolygonImg> imgList = new ArrayList<>();
            String[] pics = excelModel.getColumn12().trim().split(",");
            for(String str : pics){
                imgList.add(new MapOthersPolygonImg(polygonVO.getPolygonCode(), str));
            }
            polygonVO.setMapOthersPolygonImgList(imgList);
        }

        if(excelModel.getColumn13() != null && StringUtils.isNumeric(excelModel.getColumn13())){
            polygonVO.setOrderId(Integer.parseInt(excelModel.getColumn13()));
        }
        polygonVO.setMemo(excelModel.getColumn14());
        if(errMsg.length() > 0){
            polygonVO.setErrMsg(errMsg);
        }

        List<MapOthersPolygonExtends> mapOthersPolygonExtendsList = new ArrayList<>();
        for(int i = 0; i < extendsList.size(); i++){
            MapOptExtendsDefine extendsDefine = extendsList.get(i);
            String getMethod = "getColumn" + (15 + i);
            Object extendsValueObject = excelModel.getClass()
                    .getDeclaredMethod(getMethod, null)
                    .invoke(excelModel);
            String extendsValue = extendsValueObject == null ? null : String.valueOf(extendsValueObject);
            if(StringUtils.isNotBlank(extendsValue)){
                MapOthersPolygonExtends polygonExtends = new MapOthersPolygonExtends();
                polygonExtends.setPolygonCode(polygonVO.getPolygonCode());
                polygonExtends.setColumnId(extendsDefine.getColumnId());
                polygonExtends.setTypeCode(extendsDefine.getTypeCode());
                polygonExtends.setExtendsValue(extendsValue);

                mapOthersPolygonExtendsList.add(polygonExtends);
            }

        }

        polygonVO.setMapOthersPolygonExtendsList(mapOthersPolygonExtendsList);
        if(polygonVO.getMapOthersPolygonImgList() == null){
            polygonVO.setMapOthersPolygonImgList(new ArrayList<>());
        }

        return polygonVO;
    }

    private Example<MapOthersPolygon> loadExample(Integer campusCode, Integer typeCode, String polygonName){
        MapOthersPolygon mapOthersPolygon = new MapOthersPolygon();
        mapOthersPolygon.setCampusCode(campusCode);
        mapOthersPolygon.setTypeCode(typeCode);
        mapOthersPolygon.setPolygonName(polygonName);
        mapOthersPolygon.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("polygonName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("polygonCode");

        return Example.of(mapOthersPolygon, exampleMatcher);
    }
}
