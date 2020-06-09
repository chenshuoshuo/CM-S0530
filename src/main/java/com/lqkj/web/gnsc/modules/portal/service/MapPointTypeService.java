package com.lqkj.web.gnsc.modules.portal.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.gnsc.modules.portal.dao.MapPointDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapPointTypeDao;
import com.lqkj.web.gnsc.modules.portal.model.MapPointType;
import com.lqkj.web.gnsc.modules.portal.model.MapPointTypeQuerySpecification;
import com.lqkj.web.gnsc.modules.portal.model.MapPtExtendsDefine;
import com.lqkj.web.gnsc.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MapPointTypeService  {
    @Autowired
    MapPointTypeDao mapPointTypeDao;
    @Autowired
    MapPointDao mapPointDao;
    @Autowired
    MapPointExtendsService extendsService;
    @Autowired
    MapPtExtendsDefineService extendsDefineService;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String PropertyDirStaticPath = "./upload/mapPointType/init/";


    public MapPointType queryById(Integer typeCode) {
        return mapPointTypeDao.findById(typeCode).get();
    }


    public MapPointType queryParentTypeById(Integer typeCode) {
        MapPointType mapPointType = mapPointTypeDao.findById(typeCode).get();
        if(mapPointType.getParentCode() == null){
            return null;
        } else{
            return mapPointTypeDao.findById(mapPointType.getParentCode()).get();
        }
    }


    public UtilPage<MapPointType> pageQuery(String typeName, String parentTypeName, Integer page, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        Page<MapPointType> pageInfo = pageQuery(typeName, parentTypeName, pageRequest);
        for(MapPointType mapPointType : pageInfo.getContent()){
            mapPointType.setChildrenMapPointTypeList(mapPointTypeDao.findAll(loadExample(typeName, mapPointType.getTypeCode(), null), sort));
        }
        try {
            UtilPage utilPage = objectMapper.readValue(
                    JSON.toJSONString(pageInfo),
                    UtilPage.class);
            return utilPage;
        }catch (IOException e) {
            logger.error(e.getMessage(),e);
            return new UtilPage<>();
        }

    }


    public List<MapPointType> listQuery(String typeName, String parentTypeName, Integer campusCode) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        List<MapPointType> pointTypeList =  mapPointTypeDao.findAll(new MapPointTypeQuerySpecification(parentTypeName, null), sort);
        for(MapPointType mapPointType : pointTypeList){
            List<MapPointType> childrenList = mapPointTypeDao.findAll(loadExample(typeName, mapPointType.getTypeCode(), true), sort);
            for(MapPointType childType : childrenList){
                childType.setMapPointCount(mapPointDao.countWithTypeCode(childType.getTypeCode(), campusCode));
            }
            mapPointType.setChildrenMapPointTypeList(childrenList);
        }
        return pointTypeList;
    }


    public List<MapPointType> queryByTypeAndCampusCode(String typeName, String parentTypeName, Integer campusCode,Boolean isVector) {

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        List<MapPointType> mapPointTypeList = mapPointTypeDao.findAll(new MapPointTypeQuerySpecification(parentTypeName, null), sort);
        for(MapPointType mapPointType : mapPointTypeList){
            List<MapPointType> childrenList = mapPointTypeDao.findAll(loadExample(typeName, mapPointType.getTypeCode(), true), sort);
            for(MapPointType childType : childrenList){
                if(isVector){
                    childType.setMapPointCount(mapPointDao.countWithTypeCode(childType.getTypeCode(), campusCode));
                }else {
                    childType.setMapPointCount(mapPointDao.countWithTypeCodeFor3D(childType.getTypeCode(), campusCode));
                }

            }
            mapPointType.setChildrenMapPointTypeList(childrenList);
        }
        return mapPointTypeList;
    }


    public List<MapPointType> queryParentList() {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapPointTypeDao.findAll(new MapPointTypeQuerySpecification(null, null), sort);
    }


    public List<MapPointType> queryListWithDisplayParentCode(Boolean display, Integer[] parentCodes, Integer campusCode,Boolean isVector) {
        if(display){
            if(parentCodes == null || parentCodes.length == 0){
                return mapPointTypeDao.findAll(new MapPointTypeQuerySpecification(null, display),
                        new Sort(Sort.Direction.ASC, "orderId"));
            } else{
                List<MapPointType> mapPointTypeList = mapPointTypeDao.queryByParentCodesAndDisplay(parentCodes, display);
                for(MapPointType mapPointType : mapPointTypeList){
                    if(isVector){
                        mapPointType.setMapPointCount(mapPointDao.countWithTypeCode(mapPointType.getTypeCode(), campusCode));
                    }else {
                        mapPointType.setMapPointCount(mapPointDao.countWithTypeCodeFor3D(mapPointType.getTypeCode(), campusCode));
                    }
                }
                return mapPointTypeList;
            }
        }
        return null;
    }

    public Map<String, Integer> countListWithDisplayParentCode(String[] roles, Integer campusCode, Boolean isVector) {
        //获取当前校区下所有分类
        int pointCount = 0;
        Map<String, Integer> countAll = new HashMap<>();
        List<MapPointType> parentMapPointList = this.queryByTypeAndCampusCode(null,null ,campusCode,isVector);
        if (parentMapPointList.size() > 0) {
            for (MapPointType mapPointType : parentMapPointList) {
                List<MapPointType> childrenMapPointTypeList = mapPointType.getChildrenMapPointTypeList();
                if (childrenMapPointTypeList.size() > 0) {
                    for (MapPointType childrenMapPointType : childrenMapPointTypeList) {
                        pointCount += childrenMapPointType.getMapPointCount();
                    }
                }
            }
        }
       // Integer thematicInfoCount = cmDbeRpc.thematicInfoCount(roles, campusCode);
        countAll.put("totalPoint", pointCount);
        countAll.put("totalThematicInfo", 0);
        return countAll;
    }


    public MapPointType add(MapPointType mapPointType) {
        mapPointType.setTypeCode(mapPointTypeDao.queryMaxTypeCode());
        return mapPointTypeDao.save(mapPointType);
    }


    public MapPointType update(MapPointType mapPointType) {
        return mapPointTypeDao.save(mapPointType);
    }


    public Integer delete(Integer typeCode) {
        mapPointTypeDao.deleteById(typeCode);
        return 1;
    }


    public Integer bulkDelete(String ids) {
        String[] idArray = ids.split(",");
        for(String str : idArray){
            mapPointTypeDao.deleteById(Integer.parseInt(str));
        }
        return idArray.length;
    }


    public List<MapPointType> queryByParentCodes(String parentCodes) {
        Integer[] idArray = loadIntegerArrayFromString(parentCodes, ",");
        return mapPointTypeDao.queryByParentCodes(idArray);
    }


    public List<MapPointType> listAllSubType() {
        return mapPointTypeDao.queryAllSubType();
    }


    public List<MapPointType> listAll() {
        return mapPointTypeDao.listAll();
    }


    public List<MapPointType> saveAll(List<MapPointType> pointTypeList) {
        for(MapPointType type : pointTypeList){
            if(type.getTypeCode() == null){
                type.setTypeCode(mapPointTypeDao.queryMaxTypeCode());
            }
        }
        return mapPointTypeDao.saveAll(pointTypeList);
    }

    public ResponseEntity<InputStreamResource> exportTemplate() throws IOException {
        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                ExcelUtils.loadEmptyDataList(2),
                Arrays.asList("点标注分类信息", "扩展属性定义"),
                "点标注分类信息导入模板.xlsx");
    }


    public ResponseEntity<InputStreamResource> download(String userId) throws IOException {
        List<MapPointType> list = mapPointTypeDao.listAll();

        Map<Integer, MapPointType> parentTypeMap = this.queryParentList()
                .stream()
                .collect(Collectors.toMap(MapPointType::getTypeCode, k -> k));

        Map<Integer, String> pointTypeNameMap = this.listAllSubType()
                .stream()
                .collect(Collectors.toMap(
                        MapPointType::getTypeCode,
                        k -> k.getTypeName() + "(" + parentTypeMap.get(k.getParentCode()).getTypeName() + ")"));

        List<MapPtExtendsDefine> extendsDefineList = extendsDefineService.loadAll();

        List<List<? extends BaseRowModel>> dataList = new ArrayList<>();
        dataList.add(list);
        dataList.add(loadExtendsDefineList(pointTypeNameMap, extendsDefineList));

        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                dataList,
                Arrays.asList("点标注分类信息", "扩展属性定义"),
                "点标注分类信息.xlsx");
    }


    public void upload(InputStream inputStream) {
        DataImportLog dataImportLog = new DataImportLog("点标注分类信息导入", 0);
        List<List<Object>> readList = ExcelUtils.readMultipleSheetExcel(inputStream, loadClazzList());

        dataImportLog.setSubCategory("点标注分类信息导入");
        dataImportLog.setSubTotalCount(readList.get(0).size());
        dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

        if (readList.get(0).size() > 0) {
            List<MapPointType> pointTypeList = new ArrayList<>();
            for (Object object : readList.get(0)) {
                MapPointType pointType = (MapPointType) object;
                String errMsg = "";

                if (pointType.getTypeName() == null) {
                    errMsg += "点标注名称不能为空";
                }


                if (errMsg.length() > 0) {
                    Map<String, Object> errMap = new HashMap<>();
                    errMap.put("errMsg", errMsg);
                    dataImportLog.addError(true,errMap);
                } else {
                    pointTypeList.add(pointType);
                    dataImportLog.addImportedCount(true);
                }
            }

            mapPointTypeDao.saveAll(pointTypeList);

            if (readList.get(1).size() > 0) {
                dataImportLog.setSubCategory("扩展属性导入");
                dataImportLog.setSubTotalCount(readList.get(1).size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

                Map<Integer, MapPointType> parentTypeMap = this.queryParentList()
                        .stream()
                        .collect(Collectors.toMap(MapPointType::getTypeCode, k -> k));

                Map<String, Integer> pointTypeNameMap = this.listAllSubType()
                        .stream()
                        .collect(Collectors.toMap(
                                k -> k.getTypeName() + "(" + parentTypeMap.get(k.getParentCode()).getTypeName() + ")",
                                MapPointType::getTypeCode));

                List<MapPtExtendsDefine> extendsDefineList = new ArrayList<>();
                for (Object object : readList.get(1)) {
                    ExcelModel excelModel = (ExcelModel) object;
                    String errMsg = "";

                    MapPtExtendsDefine extendsDefine = new MapPtExtendsDefine();
                    if (excelModel.getColumn1() != null && StringUtils.isNumeric(excelModel.getColumn1())) {
                        extendsDefine.setColumnId(Integer.parseInt(excelModel.getColumn1()));
                    }
                    if (StringUtils.isNotBlank(excelModel.getColumn2())
                            && pointTypeNameMap.containsKey(excelModel.getColumn2())) {
                        extendsDefine.setTypeCode(pointTypeNameMap.get(excelModel.getColumn2()));
                    } else {
                        errMsg += "分类名称必须与分类信息相匹配";
                    }

                    extendsDefine.setColumnName(excelModel.getColumn3());
                    extendsDefine.setColumnCnname(excelModel.getColumn4());
                    if (StringUtils.isNotBlank(excelModel.getColumn5())
                            && StringUtils.isNumeric(excelModel.getColumn5())) {
                        extendsDefine.setColumnType(Integer.parseInt(excelModel.getColumn5()));
                    } else {
                        extendsDefine.setColumnType(0);
                    }

                    if (StringUtils.isNotBlank(excelModel.getColumn6())
                            && "true".equals(excelModel.getColumn6().toLowerCase().trim())) {
                        extendsDefine.setRequired(true);
                    } else {
                        extendsDefine.setRequired(false);
                    }

                    if (StringUtils.isNotBlank(excelModel.getColumn7())
                            && "true".equals(excelModel.getColumn7().toLowerCase().trim())) {
                        extendsDefine.setShow(true);
                    } else {
                        extendsDefine.setShow(false);
                    }

                    if (StringUtils.isNotBlank(excelModel.getColumn8())
                            && StringUtils.isNumeric(excelModel.getColumn8())) {
                        extendsDefine.setOrderid(Integer.parseInt(excelModel.getColumn8()));
                    }

                    extendsDefine.setMemo(excelModel.getColumn9());

                    if (errMsg.length() > 0) {
                        Map<String, Object> errMap = new HashMap<>();
                        errMap.put("errMsg", errMsg);
                        dataImportLog.addError(true, errMap);
                    } else {
                        extendsDefineList.add(extendsDefine);
                        dataImportLog.addImportedCount(true);
                    }
                }

                extendsDefineService.bulkSave(extendsDefineList);
            }
        }
    }

//    @PostConstruct
//    @Async
    public void initMapPointTypeImg() {

        if(!Files.exists(Paths.get(PropertyDirStaticPath))){
            logger.info("初始化点分类图片开始。。");
            List<MapPointType> pointTypeList = mapPointTypeDao.findAll();
            if (pointTypeList != null && pointTypeList.size() > 0){
                for (MapPointType mapPointType : pointTypeList) {
                    String rasterIcon = mapPointType.getRasterIcon();
                    String vectorIcon = mapPointType.getVectorIcon();

                    if (rasterIcon!=null && rasterIcon.contains("init")){
                        try {
                            String rasterIconName = rasterIcon.substring(rasterIcon.lastIndexOf("/") + 1, rasterIcon.length());
                            logger.info("rasterIconName---"+rasterIconName);
                            InputStream in = new ClassPathResource("static/mapPointType/"+rasterIconName).getInputStream();
                            FileUtil.fileCopyIn(in,PropertyDirStaticPath,rasterIconName);
                        }catch (Exception e){
                            logger.error(rasterIcon,e);

                        }
                    }
                    if (vectorIcon!=null && vectorIcon.contains("init")){
                        try {
                            String vectorIconName = vectorIcon.substring(vectorIcon.lastIndexOf("/") + 1, vectorIcon.length());
                            logger.info("vectorIconName---"+vectorIconName);
                            InputStream in = new ClassPathResource("static/mapPointType/"+vectorIconName).getInputStream();
                            FileUtil.fileCopyIn(in,PropertyDirStaticPath,vectorIconName);
                        }catch (Exception e){
                            logger.error(vectorIcon,e);
                        }
                    }
                }
            }
            logger.info("初始化点分类图片完成。。。");
        }
    }

    List<Class<? extends BaseRowModel>> loadClazzList() {
        List<Class<? extends BaseRowModel>> clazzList = new ArrayList<>();
        clazzList.add(MapPointType.class);
        clazzList.add(ExcelModel.class);

        return clazzList;
    }

    List<List<List<String>>> loadHeadList() {
        List<List<List<String>>> headList = new ArrayList<>();
        String dataHead = "分类编号,上级分类编号,分类名称,三维图标,二维图标,显示级别(默认18)"
                + ",是否可点击(true/false),是否可搜索(true/false),是否显示(true/false),分类描述,分类排序,备注";
        headList.add(ExcelUtils.loadHead(dataHead));
        String extendsHead = "属性ID,分类名称(上级分类名称),英文名,中文名,数据类型：0：文本；1：日期；2：富文本"
                + ",是否必填(true/false),是否显示(true/false),排序,备注";
        headList.add(ExcelUtils.loadHead(extendsHead));

        return headList;
    }

    List<ExcelModel> loadExtendsDefineList(Map<Integer, String> pointTypeMap,
                                           List<MapPtExtendsDefine> extendsDefineList) {
        List<ExcelModel> list = new ArrayList<>();
        for (MapPtExtendsDefine extendsDefine : extendsDefineList) {
            List<String> columns = new ArrayList<>();
            columns.add(extendsDefine.getColumnId().toString());
            columns.add(pointTypeMap.get(extendsDefine.getTypeCode()));
            columns.add(extendsDefine.getColumnName());
            columns.add(extendsDefine.getColumnCnname());
            columns.add(extendsDefine.getColumnType() == null ? "0" : extendsDefine.getColumnType().toString());
            columns.add(extendsDefine.getRequired() ? "TRUE" : "FALSE");
            columns.add(extendsDefine.getShow() ? "TRUE" : "FALSE");
            columns.add(extendsDefine.getOrderid() == null ? "" : extendsDefine.getOrderid().toString());
            columns.add(extendsDefine.getMemo());

            list.add(ExcelUtils.loadExcelModel(columns));
        }

        return list;
    }

    private Example<MapPointType> loadParentExample(String parentTypeName){

        MapPointType mapPointType = new MapPointType();
        mapPointType.setTypeName(parentTypeName);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("typeCode");

        return Example.of(mapPointType, exampleMatcher);
    }

    private Example<MapPointType> loadExample(String typeName, Integer parentCode, Boolean display){

        MapPointType mapPointType = new MapPointType();
        mapPointType.setTypeName(typeName);
        mapPointType.setParentCode(parentCode);
        mapPointType.setDisplay(display);

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("parentCode", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("display", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("typeCode");

        return Example.of(mapPointType, exampleMatcher);
    }

    private Integer[] loadIntegerArrayFromString(String str, String separator){
        String[] stringArray = str.split(separator);
        Integer[] integerArray = new Integer[stringArray.length];
        for(int i = 0; i < stringArray.length; i++){
            integerArray[i] = Integer.parseInt(stringArray[i]);
        }
        return integerArray;
    }

    private Page<MapPointType> pageQuery(String typeName, String parentTypeName, PageRequest pageRequest){
        if(StringUtils.isNotEmpty(typeName) && StringUtils.isNotEmpty(parentTypeName)){
            return mapPointTypeDao.pageQueryWithTypeNameAndParentName(typeName, parentTypeName, pageRequest);
        } else if(StringUtils.isNotEmpty(typeName)){
            return mapPointTypeDao.pageQueryWithTypeName(typeName, pageRequest);
        } else if(StringUtils.isNotEmpty(parentTypeName)){
            return mapPointTypeDao.pageQueryWithParentName(parentTypeName, pageRequest);
        } else {
            return mapPointTypeDao.pageQueryParentType(pageRequest);
        }
    }

}
