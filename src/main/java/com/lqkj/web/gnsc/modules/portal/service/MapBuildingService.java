package com.lqkj.web.gnsc.modules.portal.service;


import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.base.BaseService;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapBuildingExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.model.excel.MapBuildingExcelModel;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapBuildingVO;
import com.lqkj.web.gnsc.utils.*;
import com.vividsolutions.jts.geom.Geometry;
import io.swagger.annotations.ApiOperation;
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
public class MapBuildingService extends BaseService {
    @Autowired
    MapBuildingDao mapBuildingDao;
    @Autowired
    private MapBuildingImgService buildingImgService;
    @Autowired
    private MapBuildingExtendsService buildingExtendsService;
    @Autowired
    private MapBuildingTypeService buildingTypeService;
    @Autowired
    private SchoolCampusDao campusDao;
    @Autowired
    MapBuildingExcelModelDao mapBuildingExcelModelDao;
    @Autowired
    private MapBtExtendsDefineService btExtendsDefineService;

    private boolean dataTransferStatus = true;

    private Logger logger = LoggerFactory.getLogger("大楼信息管理");


    public Page<MapBuilding> pageQuery(Integer campusCode, Integer typeCode, String buildingName, Integer page, Integer pageSize) {

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        Page<MapBuilding> pageInfo = mapBuildingDao.findAll(loadExample(campusCode, typeCode, buildingName), pageRequest);

        try {
            for(MapBuilding building : pageInfo.getContent()){

                building.setCampusName(campusDao.findByVectorZoomCode(building.getCampusCode()).getCampusName());
                building.setMapBuildingImgList(buildingImgService.queryListWithBuildingCode(building.getBuildingCode()));
                building.setMapBuildingType(buildingTypeService.queryById(building.getTypeCode()));

                Long[] id = new Long[]{building.getMapCode()};
                JSONArray jsonArray = JSON.parseObject(queryWayGeoJson(id))
                        .getJSONArray("features");
                if(jsonArray.size() > 0){
                    Geometry geometry = GeoJSON.gjson.read(JSON.toJSONString(jsonArray.getJSONObject(0)));
                    building.setCenter(geometry.getCentroid().toString());
                }
            }
            return pageInfo;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }
    }


    public List<MapBuilding> listQuery(Integer campusCode, Integer typeCode, String buildingName) {

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapBuildingDao.findAll(loadExample(campusCode, typeCode, buildingName), sort);
    }


    public MapBuilding add(MapBuilding mapBuilding) {

        return mapBuildingDao.save(mapBuilding);
    }

    public MapBuilding get(Integer buildingCode) {
        MapBuilding building = mapBuildingDao.findById(buildingCode).get();
        building.setMapBuildingExtendsList(buildingExtendsService.queryList(buildingCode));
        building.setMapBuildingImgList(buildingImgService.queryListWithBuildingCode(building.getBuildingCode()));
        building.setMapBuildingType(buildingTypeService.queryById(building.getTypeCode()));
        building.setGeoJson(loadGeoJson(building.getMapCode()));
        return  building;
    }

    public MessageBean update(MapBuilding mapBuilding) {
        mapBuilding.setSynStatus(false);
        mapBuildingDao.save(mapBuilding);
        List<MapBuildingImg> mapBuildingImgList = mapBuilding.getMapBuildingImgList();
        buildingImgService.deleteAllByBuildingCode(mapBuilding.getBuildingCode());

        if(mapBuildingImgList != null && mapBuildingImgList.size() > 0){
            buildingImgService.saveImgList(mapBuildingImgList);
        }

        List<MapBuildingExtends> mapBuildingExtendsList = mapBuilding.getMapBuildingExtendsList();
        buildingExtendsService.save(mapBuildingExtendsList);

//        try {
//            // 更新一个边/面的名称，同时更新搜索引擎
//            String reStr = updateWayName(
//                    String.valueOf(mapBuilding.getCampusCode()),
//                    mapBuilding.getBuildingName(),
//                    "",
//                    mapBuilding.getBrief(),
//                    "",
//                    mapBuilding.getAlias(),
//                    mapBuilding.getEnName(),
//                    mapBuilding.getMapCode());
//            logger.info("推送cmgis:"+reStr);
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//            return MessageBean.construct(mapBuilding,"推送cmgis失败");
//        }
        return MessageBean.construct(mapBuilding,"更新成功");
    }

    public Integer delete(Integer buildingCode) {
        MapBuilding mapBuilding =  mapBuildingDao.findById(buildingCode).get();
        mapBuilding.setSynStatus(false);
        mapBuilding.setDelete(true);
        mapBuildingDao.save(mapBuilding);
        MapIndexTemplate mapIndexTemplate = new MapIndexTemplate(
                SystemType.map, mapBuilding.getMapCode().toString(), GeomType.polygon, mapBuilding.getCampusCode());
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
        List<MapBuildingVO> buildingList = JSONArray.parseArray(JSON.toJSONString(mapBuildingDao.queryWithBuildingCodes(idArray)),MapBuildingVO.class);
        String result = bulkRemoveSearchIndex(loadPushMapIndexTemplateFromBuilding(buildingList));
        logger.info(result);
        for(Integer str : idArray){
            MapBuilding mapBuilding =  mapBuildingDao.findById(str).get();
            mapBuilding.setSynStatus(false);
            mapBuilding.setDelete(true);
            mapBuildingDao.save(mapBuilding);
        }
        return idArray.length;
    }

    public InputStream exportTemplate() throws IOException {
        List<MapBuildingType> typeList = buildingTypeService.queryList(null);

        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapBuildingType buildingType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, null);
            sheet.setSheetName(ExcelUtils.removeSpecialCharacter(buildingType.getTypeName()));
            sheet.setHead(loadHeadList(buildingType.getTypeCode()));

            writer.write(new ArrayList<>(), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public InputStream download(String userId) throws IOException {
        List<MapBuildingType> typeList = buildingTypeService.queryList(null);
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapBuildingType buildingType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, ExcelModel.class);
            sheet.setSheetName(ExcelUtils.removeSpecialCharacter(buildingType.getTypeName()));
            sheet.setHead(loadHeadList(buildingType.getTypeCode()));

            writer.write(loadExportList(buildingType.getTypeCode()), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public void upload(InputStream inputStream)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        List<MapBuildingType> typeList =buildingTypeService.queryList(null);

        AnalysisEventListener<ExcelModel> listener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);

        DataImportLog dataImportLog = new DataImportLog();
        dataImportLog.setCategory("大楼信息导入");

        if(excelReader.getSheets().size() != typeList.size()){
            dataImportLog.setErrorCode(-1);
            dataImportLog.setErrorMsg("请重新下载数据导入模板");
        } else{
            List<MapBuildingVO> buildingVOList = new ArrayList<>();
            for(int i = 0; i < typeList.size(); i++){
                excelReader.read(new Sheet(i + 1, 1, ExcelModel.class));
                List<Object> dataList = ((ExcelListener) listener).getDatas();

                MapBuildingType buildingType = typeList.get(i);
                List<MapBtExtendsDefine> extendsList = btExtendsDefineService.queryList(buildingType.getTypeCode());

                dataImportLog.setSubCategory(buildingType.getTypeName());
                dataImportLog.setSubTotalCount(dataList.size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());
                for(int j = 0; j < dataImportLog.getSubTotalCount(); j++){
                    ExcelModel excelModel = (ExcelModel) dataList.get(j);
                    MapBuildingVO buildingVO = transferFromExcelModel(buildingType.getTypeCode(),
                            extendsList, excelModel);
                    if(dataTransferStatus){
                        if(buildingVO.getBuildingCode() == null){
                            if(this.existsByMapCode(buildingVO.getMapCode())){
                                buildingVO.setErrMsg(buildingVO.getErrMsg() + "地图编码不能重复；");
                                dataImportLog.addError(true, buildingVO);
                            } else{
                                // 添加大楼信息
                                MapBuildingVO newBuildingVO = JSON.parseObject(
                                        JSON.toJSONString(this.add(loadFromMapBuildingVO(buildingVO))),
                                        MapBuildingVO.class);

                                // 添加扩展属性
                                List<MapBuildingExtends> buildingExtendsList = buildingVO
                                        .getMapBuildingExtendsList();
                                for(MapBuildingExtends buildingExtends : buildingExtendsList){
                                    buildingExtends.setBuildingCode(newBuildingVO.getBuildingCode());
                                }
                                buildingExtendsService.save(buildingExtendsList);

                                // 添加图片
                                List<MapBuildingImg> buildingImgList = buildingVO.getMapBuildingImgList();
                                if(buildingImgList != null && buildingImgList.size() > 0){
                                    for(MapBuildingImg buildingImg : buildingImgList){
                                        buildingImg.setBuildingCode(newBuildingVO.getBuildingCode());
                                    }
                                    buildingImgService.saveImgList(buildingImgList);
                                }
                                buildingVOList.add(newBuildingVO);
                            }
                        } else {
                            // 更新大楼信息
                            this.update(loadFromMapBuildingVO(buildingVO));

                            buildingVOList.add(buildingVO);
                        }
                        dataImportLog.addImportedCount(true);
                    } else {
                        dataImportLog.addError(true, buildingVO);
                    }
                }
                ((ExcelListener) listener).clearData();
            }
            String result = bulkPushSearchIndex(loadPushMapIndexTemplateFromBuilding(buildingVOList));
            logger.info(result);
        }
    }



    public MapBuilding queryByMapCode(Long mapCode) {
        return mapBuildingDao.queryByMapCode(mapCode);
    }


    public Boolean existsByMapCode(Long mapCode) {
        return mapBuildingDao.existsByMapCode(mapCode);
    }

    public List<MapBuilding> queryChangeList(Integer zoneId) {
        return mapBuildingDao.queryChangeList(zoneId);
    }


    public void updateSynStatusAfterSyn(Integer zoneId) {
        mapBuildingDao.updateSynStatusAfterSyn(zoneId);
    }


    public void deleteAfterSyn(Long[] mapCodes,Integer zoneId) {
        mapBuildingDao.deleteAfterSyn(mapCodes, zoneId);
    }


    public void saveAll(List<MapBuilding> mapBuildingList) {

        mapBuildingDao.saveAll(mapBuildingList);
    }

    public List<MapBuildingExcelModel> queryExcelList(Integer typeCode) {
        return mapBuildingExcelModelDao.queryExcelList(typeCode);
    }

    public List<MapBuilding> queryWithBuildingCodes(Integer[] buildingCodes) {
        return mapBuildingDao.queryWithBuildingCodes(buildingCodes);
    }

    private Example<MapBuilding> loadExample(Integer campusCode, Integer typeCode, String buildingName){
        MapBuilding mapBuilding = new MapBuilding();
        mapBuilding.setCampusCode(campusCode);
        mapBuilding.setTypeCode(typeCode);
        if(buildingName!=null){
            mapBuilding.setBuildingName(buildingName);
        }
        mapBuilding.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("buildingName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("buildingCode");

        return Example.of(mapBuilding, exampleMatcher);
    }

    private JSONObject loadGeoJson(Long mapCode){
        Long[] id = new Long[]{mapCode};
        JSONObject jsonObject = JSON.parseObject(queryWayGeoJson( id));
        return jsonObject.getJSONArray("features").getJSONObject(0);
    }

    private List<List<String>> loadHeadList(Integer typeCode){
        String headString = "编号(新增不用填写),大楼名称,校区编号,地图编号"
                + ",英文名称,别名,简介,同步状态(true/false)"
                + ",删除状态(true/false),版本号,图片地址,排序,备注";


        List<MapBtExtendsDefine> btExtendsDefineList = btExtendsDefineService.queryList(typeCode);
        for(MapBtExtendsDefine extendsDefine : btExtendsDefineList){
            headString += "," + extendsDefine.getColumnCnname();
        }

        return ExcelUtils.loadHead(headString);
    }

    private List<ExcelModel> loadExportList(Integer typeCode){
        List<ExcelModel> list = new ArrayList<>();
        List<MapBuildingVO> buildingList = JSON.parseArray(
                JSON.toJSONString(mapBuildingExcelModelDao.queryExcelList(typeCode)), MapBuildingVO.class);
        for(MapBuildingVO building : buildingList){
            List<String> columns = new ArrayList<>();
            columns.add(building.getBuildingCode().toString());
            columns.add(building.getBuildingName());
            columns.add(building.getCampusCode().toString());
            columns.add(building.getMapCode().toString());
            columns.add(building.getEnName());
            columns.add(building.getAlias());
            columns.add(building.getBrief());
            columns.add(building.getSynStatus() ? "TRUE" : "FALSE");
            columns.add(building.getDelete() ? "TRUE" : "FALSE");
            columns.add(building.getVersionCode() == null ? "" : building.getVersionCode().toString());
            columns.add(building.getPicUrl());
            columns.add(building.getOrderId() == null ? "" : building.getOrderId().toString());
            columns.add(building.getMemo());

            List<MapBuildingExtends> extendsList = JSON.parseArray(
                    JSON.toJSONString(buildingExtendsService.queryList(building.getBuildingCode())), MapBuildingExtends.class);
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
    private MapBuildingVO transferFromExcelModel(Integer typeCode,
                                                 List<MapBtExtendsDefine> extendsList,
                                                 ExcelModel excelModel)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapBuildingVO buildingVO = new MapBuildingVO();

        dataTransferStatus = true;
        String errMsg = "";
        if(excelModel.getColumn1() != null){
            if(StringUtils.isNumeric(excelModel.getColumn1())){
                buildingVO.setBuildingCode(Integer.parseInt(excelModel.getColumn1()));
            }else{
                dataTransferStatus = false;
                errMsg += "大楼编码必须是数字；";
            }

        }
        buildingVO.setTypeCode(typeCode);

        buildingVO.setBuildingName(excelModel.getColumn2());
        if(excelModel.getColumn3() != null && StringUtils.isNumeric(excelModel.getColumn3())){
            buildingVO.setCampusCode(Integer.parseInt(excelModel.getColumn3()));
        } else {
            dataTransferStatus = false;
            errMsg += "校区编码必填且是数字；";
        }

        if(excelModel.getColumn4() != null && StringUtils.isNumeric(excelModel.getColumn4())){
            buildingVO.setMapCode(Long.parseLong(excelModel.getColumn4()));
        }else{
            dataTransferStatus = false;
            errMsg += "地图编号必填且数字；";
        }
        buildingVO.setEnName(excelModel.getColumn5());
        buildingVO.setAlias(excelModel.getColumn6());
        buildingVO.setBrief(excelModel.getColumn7());
        // 同步状态
        if(excelModel.getColumn8() != null && ("true".equals(excelModel.getColumn8().toLowerCase()) || "1".equals(excelModel.getColumn8()))){
            buildingVO.setSynStatus(true);
        } else {
            buildingVO.setSynStatus(false);
        }
        String s = excelModel.getColumn9().toLowerCase();
        // 删除状态
        if(excelModel.getColumn9() != null && ("true".equals(excelModel.getColumn9().toLowerCase()) || "1".equals(excelModel.getColumn9()))){
            buildingVO.setDelete(true);
        } else {
            buildingVO.setDelete(false);
        }
        // 版本号
        if(excelModel.getColumn10() != null && StringUtils.isNumeric(excelModel.getColumn10())){
            buildingVO.setVersionCode(Integer.parseInt(excelModel.getColumn10()));
        }

        if(StringUtils.isNotBlank(excelModel.getColumn11())){
            List<MapBuildingImg> imgList = new ArrayList<>();
            String[] pics = excelModel.getColumn11().trim().split(",");
            for(String str : pics){
                imgList.add(new MapBuildingImg(buildingVO.getBuildingCode(), str));
            }
            buildingVO.setMapBuildingImgList(imgList);
        }

        if(excelModel.getColumn12() != null && StringUtils.isNumeric(excelModel.getColumn12())){
            buildingVO.setOrderId(Integer.parseInt(excelModel.getColumn12()));
        }
        buildingVO.setMemo(excelModel.getColumn13());
        if(errMsg.length() > 0){
            buildingVO.setErrMsg(errMsg);
        }


        List<MapBuildingExtends> buildingExtendsList = new ArrayList<>();
        for(int i = 0; i < extendsList.size(); i++){
            MapBtExtendsDefine extendsDefine = extendsList.get(i);
            String getMethod = "getColumn" + (14 + i);
            Object extendsValueObject = excelModel.getClass()
                    .getDeclaredMethod(getMethod, null)
                    .invoke(excelModel);
            String extendsValue = extendsValueObject == null ? null : String.valueOf(extendsValueObject);
            if(StringUtils.isNotBlank(extendsValue)){
                MapBuildingExtends buildingExtends = new MapBuildingExtends();
                buildingExtends.setBuildingCode(buildingVO.getBuildingCode());
                buildingExtends.setColumnId(extendsDefine.getColumnId());
                buildingExtends.setTypeCode(extendsDefine.getTypeCode());
                buildingExtends.setExtendsValue(extendsValue);

                buildingExtendsList.add(buildingExtends);
            }

        }

        buildingVO.setMapBuildingExtendsList(buildingExtendsList);
        if(buildingVO.getMapBuildingImgList() == null){
            buildingVO.setMapBuildingImgList(new ArrayList<>());
        }
        return buildingVO;
    }

    private MapBuilding loadFromMapBuildingVO(MapBuildingVO mapBuildingVO){
        MapBuilding mapBuilding = new MapBuilding();

        if(mapBuildingVO.getBuildingCode() != null){
            mapBuilding = this.get(mapBuildingVO.getBuildingCode());
        }


        mapBuilding.setAlias(mapBuildingVO.getAlias());
        mapBuilding.setBuildingCode(mapBuildingVO.getBuildingCode());
        mapBuilding.setBuildingName(mapBuildingVO.getBuildingName());
        mapBuilding.setCampusCode(mapBuildingVO.getCampusCode());
        mapBuilding.setDelete(mapBuildingVO.getDelete());
        mapBuilding.setEnName(mapBuildingVO.getEnName());
        mapBuilding.setMapCode(mapBuildingVO.getMapCode());
        mapBuilding.setMemo(mapBuildingVO.getMemo());
        mapBuilding.setOrderId(mapBuildingVO.getOrderId());
        mapBuilding.setTypeCode(mapBuildingVO.getTypeCode());
        mapBuilding.setBrief(mapBuildingVO.getBrief());
        mapBuilding.setSynStatus(mapBuildingVO.getSynStatus());
        mapBuilding.setDelete(mapBuildingVO.getDelete());
        return mapBuilding;
    }

}
