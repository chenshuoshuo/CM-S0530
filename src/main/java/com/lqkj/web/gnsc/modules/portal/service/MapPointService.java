package com.lqkj.web.gnsc.modules.portal.service;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.modules.base.BaseService;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapPointDao;
import com.lqkj.web.gnsc.modules.portal.dao.excel.MapPointExcelModelDao;
import com.lqkj.web.gnsc.modules.portal.model.*;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapPointVO;
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
public class MapPointService extends BaseService {
    @Autowired
    MapPointDao mapPointDao;
    @Autowired
    MapPtExtendsDefineService  ptExtendsDefineService;
    @Autowired
    MapPointExtendsService pointExtendsService;
    @Autowired
    MapPointTypeService pointTypeService;
    @Autowired
    MapPointImgService pointImgService;
    @Autowired
    MapPointExcelModelDao mapPointExcelModelDao;
    @Autowired
    private SchoolCampusDao campusDao;

    private boolean dataTransferStatus = true;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Page<MapPoint> pageQuery(Integer campusCode, Integer typeCode, Integer parentTypeCode, String pointName, Integer page, Integer pageSize) {

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        Page<MapPoint> pageInfo = mapPointDao.findAll(loadExample(campusCode, typeCode, parentTypeCode, pointName), pageRequest);
        try {
            for(MapPoint point : pageInfo.getContent()){

                point.setCampusName(campusDao.findByVectorZoomCode(point.getCampusCode()).getCampusName());
                point.setMapPointImgList(pointImgService.queryListWithPointCode(point.getPointCode()));
                point.setMapPointType(pointTypeService.queryById(point.getTypeCode()));
            }
            return pageInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<MapPoint> queryList(Integer campusCode, Integer typeCode, Integer parentTypeCode, String pointName) {

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapPointDao.findAll(loadExample(campusCode, typeCode, parentTypeCode, pointName), sort);
    }

    public MapPointVO add(MapPoint mapPoint) {
        try {
            // 添加点标注信息
            MapPointVO newMapPointVO = objectMapper.readValue(JSON.toJSONString(mapPointDao.save(mapPoint)), MapPointVO.class);
            newMapPointVO.setLngLatString(GeoJSON.gjson.toString(mapPoint.getLngLat()));
            newMapPointVO.setRasterLngLatString(GeoJSON.gjson.toString(mapPoint.getRasterLngLat()));

            // 添加图片
            List<MapPointImg> mapPointImgList = mapPoint.getMapPointImgList();
            if(mapPointImgList != null && mapPointImgList.size() > 0){
                for(MapPointImg mapPointImg : mapPointImgList){
                    mapPointImg.setPointCode(newMapPointVO.getPointCode());
                }
                pointImgService.saveImgList(mapPointImgList);
                newMapPointVO.setMapPointImgList(mapPointImgList);
            }

            // 添加扩展属性
            List<MapPointExtends> mapPointExtendsList = mapPoint.getMapPointExtendsList();
            if(mapPointExtendsList != null && mapPointExtendsList.size() > 0){
                for(MapPointExtends mapPointExtends : mapPointExtendsList){
                    mapPointExtends.setPointCode(newMapPointVO.getPointCode());
                }
                pointExtendsService.save(mapPointExtendsList);
                newMapPointVO.setMapPointExtendsList(mapPointExtendsList);
            }
            // 搜索引擎索引推送
            String result = bulkPushSearchIndex(loadPushMapIndexTemplateFromPoint(Collections.singletonList(newMapPointVO)));
            logger.info(result);
            return newMapPointVO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public MapPointVO get(Integer pointCode) {
        try {
            MapPointVO mapPointVO = objectMapper.readValue(JSON.toJSONString(mapPointDao.findById(pointCode).get()), MapPointVO.class);

            mapPointVO.setMapPointImgList(
                    pointImgService.queryListWithPointCode(pointCode));
            mapPointVO.setMapPointExtendsList(
                    pointExtendsService.queryList(pointCode));

            return mapPointVO;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public MapPointVO update(MapPointVO mapPointVO) throws IOException {
        // 更新点标注信息
        MapPoint mapPoint = loadFromMapPointVO(mapPointVO);
        mapPointDao.save(mapPoint);

        // 更新图片
        List<MapPointImg> mapPointImgList = mapPointVO.getMapPointImgList();
        pointImgService.deleteAllByPointCode(mapPoint.getPointCode());
        if(mapPointImgList != null && mapPointImgList.size() > 0){
            pointImgService.saveImgList(mapPointImgList);
        }

        // 更新扩展属性
        List<MapPointExtends> mapPointExtendsList = mapPointVO.getMapPointExtendsList();
        if(mapPointExtendsList != null && mapPointExtendsList.size() > 0){
            pointExtendsService.save(mapPointExtendsList);
        }

        // 搜索引擎索引推送
        String result = bulkPushSearchIndex(loadPushMapIndexTemplateFromPoint(Collections.singletonList(mapPointVO)));
        logger.info(result);
        return mapPointVO;
    }

    public Integer delete(Integer pointCode) {
        MapPoint point = mapPointDao.findById(pointCode).get();

        MapIndexTemplate mapIndexTemplate = new MapIndexTemplate(SystemType.map, pointCode.toString(),
                GeomType.point, point.getCampusCode());
        String removeSearchIndex = removeSearchIndex(mapIndexTemplate);
        logger.info("removeSearchIndex:"+removeSearchIndex);
        point.setDelete(true);
        point.setSynStatus(false);
        mapPointDao.save(point);
        logger.info("removePoint",point);
        return 1;
    }

    public Integer bulkDelete(String ids) {
        Integer[] idArray = Arrays
                .stream(ids.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new)
                ;

        List<MapPointVO> pointVOList = JSON
                .parseArray(JSON.toJSONString(mapPointDao.queryWithPointCodes(idArray)), MapPointVO.class);

        String result = bulkRemoveSearchIndex(loadPushMapIndexTemplateFromPoint(pointVOList));
        logger.info(result);
        for (Integer id : idArray) {
            MapPoint mapPoint = mapPointDao.findById(id).get();
            mapPoint.setDelete(true);
            mapPoint.setSynStatus(false);
            mapPointDao.save(mapPoint);
        }
        return idArray.length;
    }

    public List<MapPoint> queryAllByTypeCodesCampusCode(Integer[] typeCodes, Integer campusCode, Boolean isVector) {
        if (isVector) {
            return mapPointDao.queryAllByTypeCodes(typeCodes,campusCode);
        }else {
            List<MapPoint> pointListForRaster = new ArrayList<>();
            List<MapPoint> pointList = mapPointDao.queryAllByTypeCodes(typeCodes,campusCode);
            if(pointList.size() > 0){
                pointList.forEach(v ->{
                    if(v.getLeaf() == null || (v.getRasterLngLat() != null)){
                        pointListForRaster.add(v);
                    }
                });
            }
            return pointListForRaster;
        }
    }

    public Integer refreshRasterGeom() {
        List<MapPoint> mapPointList = this.queryList(null,null,null,null);
        Integer updateCount = 0;
        try {
            for(MapPoint mapPoint : mapPointList){
                if(mapPoint.getLngLat() != null){
                    mapPoint.setRasterLngLat(GeoJSON.gjson.read(transferVectorToRaster(JSONObject.parseObject(JSON.toJSONString(mapPoint.getLngLat())))));
                    mapPointDao.save(mapPoint);
                    updateCount += 1;
                }
            }
            return updateCount;
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }

    public InputStream exportTemplate() throws IOException {
        List<MapPointType> typeList = pointTypeService.listAllSubType();
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapPointType pointType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, null);
            sheet.setSheetName(loadSheetName(pointType));
            sheet.setHead(loadHeadList(pointType.getTypeCode()));

            writer.write(new ArrayList<>(), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }

    public InputStream download(String userId) throws IOException {
        List<MapPointType> typeList = pointTypeService.listAllSubType();
        OutputStream outputStream = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < typeList.size(); i++){
            MapPointType pointType = typeList.get(i);
            Sheet sheet = new Sheet(i + 1, 0, ExcelModel.class);
            sheet.setSheetName(loadSheetName(pointType));
            sheet.setHead(loadHeadList(pointType.getTypeCode()));

            writer.write(loadExportList(pointType.getTypeCode()), sheet);
        }

        writer.finish();
        outputStream.close();
        return new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
    }


    public void upload(InputStream inputStream)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        List<MapPointType> typeList = pointTypeService.listAllSubType();

        AnalysisEventListener<ExcelModel> listener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);

        DataImportLog dataImportLog = new DataImportLog();
        dataImportLog.setCategory("点标注信息导入");

        if(excelReader.getSheets().size() != typeList.size()){
            dataImportLog.setErrorCode(-1);
            dataImportLog.setErrorMsg("请重新下载数据导入模板");
        } else{
            List<MapPointVO> pointVOList = new ArrayList<>();
            for(int i = 0; i < typeList.size(); i++){
                excelReader.read(new Sheet(i + 1, 1, ExcelModel.class));
                List<Object> dataList = ((ExcelListener) listener).getDatas();

                MapPointType pointType = typeList.get(i);
                List<MapPtExtendsDefine> extendsList = ptExtendsDefineService.loadAll();

                dataImportLog.setSubCategory(pointType.getTypeName());
                dataImportLog.setSubTotalCount(dataList.size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());
                for(int j = 0; j < dataImportLog.getSubTotalCount(); j++){
                    ExcelModel excelModel = (ExcelModel) dataList.get(j);
                    MapPointVO pointVO = transferFromExcelModel(pointType.getTypeCode(),
                            extendsList, excelModel);
                    if(dataTransferStatus){
                        if(pointVO.getPointCode() == null){
                            // 添加信息
                            MapPointVO newPointVO =  this.add(loadFromMapPointVO(pointVO));

                            // 添加扩展属性
                            List<MapPointExtends> pointExtendsList = pointVO
                                    .getMapPointExtendsList();
                            for(MapPointExtends pointExtends : pointExtendsList){
                                pointExtends.setPointCode(newPointVO.getPointCode());
                            }
                            pointExtendsService.save(pointExtendsList);

                            // 添加图片
                            List<MapPointImg> imgList = pointVO.getMapPointImgList();
                            if(imgList.size() > 0){
                                for(MapPointImg img : imgList){
                                    img.setPointCode(newPointVO.getPointCode());
                                }
                                pointImgService.saveImgList(imgList);
                            }

                            pointVOList.add(newPointVO);
                        } else {
                            // 更新信息
                            this.update(pointVO);

                            pointVOList.add(pointVO);
                        }
                        dataImportLog.addImportedCount(true);
                    } else {
                        dataImportLog.addError(true, pointVO);
                    }
                }
                ((ExcelListener) listener).clearData();
            }
            String result = bulkPushSearchIndex(loadPushMapIndexTemplateFromPoint(pointVOList));
            logger.info(result);
        }
    }


    private Example<MapPoint> loadExample(Integer campusCode, Integer typeCode, Integer parentTypeCode, String pointName){
        MapPointType mapPointType = new MapPointType();
        mapPointType.setTypeCode(typeCode);
        mapPointType.setParentCode(parentTypeCode);

        MapPoint mapPoint = new MapPoint();
        mapPoint.setCampusCode(campusCode);
        mapPoint.setMapPointType(mapPointType);
        mapPoint.setPointName(pointName);
        mapPoint.setDelete(false);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("mapPointType.typeCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("mapPointType.parentCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("campusCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("delete", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("pointName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("pointCode");

        return Example.of(mapPoint, exampleMatcher);
    }

    private MapPoint loadFromMapPointVO(MapPointVO mapPointVO)throws IOException{
        MapPoint mapPoint = new MapPoint();

        if(mapPointVO.getPointCode() != null){
            mapPoint = mapPointDao.findById(mapPointVO.getPointCode()).get();
        }
        mapPoint.setCampusCode(mapPointVO.getCampusCode());
        mapPoint.setLeaf(mapPointVO.getLeaf());
        mapPoint.setLngLat(GeoJSON.gjson.read(mapPointVO.getLngLatString()));
        mapPoint.setLocation(mapPointVO.getLocation());
        mapPoint.setMemo(mapPointVO.getMemo());
        mapPoint.setOrderId(mapPointVO.getOrderId());
        mapPoint.setPointCode(mapPointVO.getPointCode());
        mapPoint.setPointName(mapPointVO.getPointName());
        mapPoint.setTypeCode(mapPointVO.getTypeCode());
        mapPoint.setBrief(mapPointVO.getBrief());
        mapPoint.setRasterLngLat(GeoJSON.gjson.read(mapPointVO.getRasterLngLatString()));
        mapPoint.setMapCode(mapPointVO.getMapCode());
        mapPoint.setVersionCode(mapPointVO.getVersionCode());
        mapPoint.setSynStatus(false);
        mapPoint.setDelete(false);
        return mapPoint;
    }

    private String transferVectorToRaster(JSONObject vectorLngLat){
        JSONArray lngLatArray = vectorLngLat.getJSONArray("coordinates");
        JSONObject jsonObject = JSON.parseObject(
                transferVectorToRaster(Double.parseDouble(lngLatArray.getString(1)),
                        Double.parseDouble(lngLatArray.getString(0))));
        return JSON.toJSONString(jsonObject);
    }

    private List<List<String>> loadHeadList(Integer typeCode){
        String headString = "编号(新增不用填写),名称,校区编号,楼层编号"
                + ",位置,坐标,三维坐标,简介,排序,备注,图片"
                + ",地图编码,版本号,同步状态(true/false),删除状态(true/false)";

        List<MapPtExtendsDefine> ptExtendsDefineList = ptExtendsDefineService.loadAll();
        for(MapPtExtendsDefine extendsDefine : ptExtendsDefineList){
            headString += "," + extendsDefine.getColumnCnname();
        }
        return ExcelUtils.loadHead(headString);
    }

    private List<ExcelModel> loadExportList(Integer typeCode){
        List<ExcelModel> list = new ArrayList<>();
        List<MapPointVO> pointList = JSON.parseArray(
                JSON.toJSONString(mapPointExcelModelDao.queryExcelList(typeCode)), MapPointVO.class);
        for(MapPointVO point : pointList){
            List<String> columns = new ArrayList<>();
            columns.add(point.getPointCode().toString());
            columns.add(point.getPointName());
            columns.add(point.getCampusCode().toString());
            columns.add(point.getLeaf() == null ? "" : point.getLeaf().toString());
            columns.add(point.getLocation());
            columns.add(point.getLngLatString());
            columns.add(point.getRasterLngLatString());
            columns.add(point.getBrief());
            columns.add(point.getOrderId() == null ? "" : point.getOrderId().toString());
            columns.add(point.getMemo());
            columns.add(point.getPicUrl());
            columns.add(point.getMapCode() == null ? "" : point.getMapCode().toString());
            columns.add(point.getVersionCode() == null ? "" : point.getVersionCode().toString());
            columns.add(point.getSynStatus() ? "TRUE" : "FALSE");
            columns.add(point.getDelete() ? "TRUE" : "FALSE");

            List<MapPointExtends> extendsList = pointExtendsService.queryList(point.getPointCode());
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
    private MapPointVO transferFromExcelModel(Integer typeCode,
                                              List<MapPtExtendsDefine> extendsList,
                                              ExcelModel excelModel)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MapPointVO pointVO = new MapPointVO();

        dataTransferStatus = true;
        String errMsg = "";
        if(excelModel.getColumn1() != null && StringUtils.isNumeric(excelModel.getColumn1())){
            pointVO.setPointCode(Integer.parseInt(excelModel.getColumn1()));

        }
        pointVO.setTypeCode(typeCode);

        pointVO.setPointName(excelModel.getColumn2());
        if(excelModel.getColumn3() != null && StringUtils.isNumeric(excelModel.getColumn3())){
            pointVO.setCampusCode(Integer.parseInt(excelModel.getColumn3()));
        } else {
            dataTransferStatus = false;
            errMsg += "校区编码必填且是数字；";
        }

        if(excelModel.getColumn4() != null && StringUtils.isNumeric(excelModel.getColumn4())){
            pointVO.setLeaf(Integer.parseInt(excelModel.getColumn4()));
        }
        pointVO.setLocation(excelModel.getColumn5());
        if(excelModel.getColumn6() != null && excelModel.getColumn6().split(",").length == 2){
            pointVO.setLngLatString(excelModel.getColumn6());
        }

        if(excelModel.getColumn7() != null && excelModel.getColumn7().split(",").length == 2){
            pointVO.setRasterLngLatString(excelModel.getColumn7());
        }

        pointVO.setBrief(excelModel.getColumn8());
        if(excelModel.getColumn9() != null && StringUtils.isNumeric(excelModel.getColumn9())){
            pointVO.setOrderId(Integer.parseInt(excelModel.getColumn9()));
        }

        pointVO.setMemo(excelModel.getColumn10());

        if(StringUtils.isNotBlank(excelModel.getColumn11())){
            List<MapPointImg> imgList = new ArrayList<>();
            String[] pics = excelModel.getColumn11().trim().split(",");
            for(String str : pics){
                imgList.add(new MapPointImg(pointVO.getPointCode(), str));
            }
            pointVO.setMapPointImgList(imgList);
        }

        if(excelModel.getColumn12() != null && StringUtils.isNumeric(excelModel.getColumn12())){
            pointVO.setMapCode(Long.parseLong(excelModel.getColumn12()));
        }

        // 版本号
        if(excelModel.getColumn13() != null && StringUtils.isNumeric(excelModel.getColumn13())){
            pointVO.setVersionCode(Integer.parseInt(excelModel.getColumn13()));
        }


        // 同步状态
        if(excelModel.getColumn14() != null && "true".equals(excelModel.getColumn14().toLowerCase())){
            pointVO.setSynStatus(true);
        } else {
            pointVO.setSynStatus(false);
        }
        // 删除状态
        if(excelModel.getColumn15() != null && "true".equals(excelModel.getColumn15().toLowerCase())){
            pointVO.setDelete(true);
        } else {
            pointVO.setDelete(false);
        }

        if(errMsg.length() > 0){
            pointVO.setErrMsg(errMsg);
        }

        List<MapPointExtends> pointExtendsList = new ArrayList<>();
        for(int i = 0; i < extendsList.size(); i++){
            MapPtExtendsDefine extendsDefine = extendsList.get(i);
            String getMethod = "getColumn" + (16 + i);
            Object extendsValueObject = excelModel.getClass()
                    .getDeclaredMethod(getMethod, null)
                    .invoke(excelModel);
            String extendsValue = extendsValueObject == null ? null : String.valueOf(extendsValueObject);
            if(StringUtils.isNotBlank(extendsValue)){
                MapPointExtends pointExtends = new MapPointExtends();
                pointExtends.setPointCode(pointVO.getPointCode());
                pointExtends.setColumnId(extendsDefine.getColumnId());
                pointExtends.setTypeCode(extendsDefine.getTypeCode());
                pointExtends.setExtendsValue(extendsValue);

                pointExtendsList.add(pointExtends);
            }
        }

        pointVO.setMapPointExtendsList(pointExtendsList);
        if(pointVO.getMapPointImgList() == null){
            pointVO.setMapPointImgList(new ArrayList<>());
        }

        return pointVO;
    }

    private String loadSheetName(MapPointType pointType){
        MapPointType parentType = pointTypeService.queryById(pointType.getParentCode());
        return pointType.getTypeName() + "(" + parentType.getTypeName() + ")";
    }
}
