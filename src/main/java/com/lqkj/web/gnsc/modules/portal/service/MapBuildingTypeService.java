package com.lqkj.web.gnsc.modules.portal.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingTypeDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingType;
import com.lqkj.web.gnsc.utils.DataImportLog;
import com.lqkj.web.gnsc.utils.ExcelModel;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MapBuildingTypeService {
    @Autowired
    MapBuildingTypeDao mapBuildingTypeDao;
    @Autowired
    MapBtExtendsDefineService btExtendsDefineService;

    public Page<MapBuildingType> pageQuery(String typeName, Integer page, Integer pageSize) {

        PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
        return mapBuildingTypeDao.findAll(loadExample(typeName), pageRequest);
    }

    public List<MapBuildingType> queryList(String typeName) {

        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapBuildingTypeDao.findAll(loadExample(typeName), sort);
    }

    public MapBuildingType add(MapBuildingType mapBuildingType) {

        mapBuildingType.setTypeCode(mapBuildingTypeDao.queryMaxTypeCode());
        return mapBuildingTypeDao.save(mapBuildingType);
    }

    public MapBuildingType queryById(Integer typeCode) {
        return mapBuildingTypeDao.findById(typeCode).get();
    }


    public MapBuildingType update(MapBuildingType mapBuildingType) {
        return mapBuildingTypeDao.save(mapBuildingType);
    }


    public Integer delete(Integer typeCode) {
        mapBuildingTypeDao.deleteById(typeCode);
        return 1;

    }


    public Integer bulkDelete(String ids) {
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            return this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }


    public List<MapBuildingType> saveAll(List<MapBuildingType> buildingTypeList) {
        if(buildingTypeList.size() > 0){
            for(MapBuildingType type : buildingTypeList){
                if(type.getTypeCode() == null){
                    type.setTypeCode(mapBuildingTypeDao.queryMaxTypeCode());
                }
            }
            return mapBuildingTypeDao.saveAll(buildingTypeList);
        }
        return null;
    }

    public ResponseEntity<InputStreamResource> exportTemplate() throws IOException {
        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                ExcelUtils.loadEmptyDataList(2),
                Arrays.asList("大楼分类信息", "扩展属性定义"),
                "大楼分类信息导入模板.xlsx");
    }

    public ResponseEntity<InputStreamResource> download(String userId) throws IOException {
        List<MapBuildingType> list = this.queryList(null);

        Map<Integer, MapBuildingType> buildingTypeMap = list
                .stream()
                .collect(Collectors.toMap(MapBuildingType :: getTypeCode,
                        MapBuildingType -> MapBuildingType));

        List<MapBtExtendsDefine> extendsDefineList = btExtendsDefineService.loadAll();

        List<List<? extends BaseRowModel>> dataList = new ArrayList<>();
        dataList.add(list);
        dataList.add(loadExtendsDefineList(buildingTypeMap, extendsDefineList));

        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                dataList,
                Arrays.asList("大楼分类信息", "扩展属性定义"),
                "大楼分类信息.xlsx");
        //return ExcelUtils.downloadOneSheetExcel(MapBuildingType.class, list, "大楼分类信息", "大楼分类信息.xlsx");
    }

    public void upload(InputStream inputStream) {
        DataImportLog dataImportLog = new DataImportLog("大楼分类信息导入", 0);
        List<List<Object>> readList = ExcelUtils.readMultipleSheetExcel(inputStream, loadClazzList());

        dataImportLog.setSubCategory("分类信息导入");
        dataImportLog.setSubTotalCount(readList.get(0).size());
        dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

        if(readList.get(0).size() > 0){
            List<MapBuildingType> buildingTypeList = new ArrayList<>();
            for(Object object : readList.get(0)){
                MapBuildingType buildingType = (MapBuildingType) object;
                String errMsg = "";
                if(buildingType.getTypeName() == null){
                    errMsg += "大楼分类名称不能为空";
                }


                if(errMsg.length() > 0){
                    Map<String, Object> errMap = new HashMap<>();
                    errMap.put("errMsg", errMsg);
                    dataImportLog.addError(true, errMsg);
                } else {
                    buildingTypeList.add(buildingType);
                    dataImportLog.addImportedCount(true);
                }
            }

           this.saveAll(buildingTypeList);

            if(readList.get(1).size() > 0){
                dataImportLog.setSubCategory("扩展属性导入");
                dataImportLog.setSubTotalCount(readList.get(1).size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

                Map<String, MapBuildingType> mapBuildingTypeMap = this.queryList(null)
                        .stream()
                        .collect(Collectors.toMap(MapBuildingType::getTypeName, k -> k));

                List<MapBtExtendsDefine> extendsDefineList = new ArrayList<>();
                for(Object object : readList.get(1)){
                    ExcelModel excelModel = (ExcelModel) object;
                    String errMsg = "";

                    MapBtExtendsDefine extendsDefine = new MapBtExtendsDefine();
                    if(excelModel.getColumn1() != null && StringUtils.isNumeric(excelModel.getColumn1())){
                        extendsDefine.setColumnId(Integer.parseInt(excelModel.getColumn1()));
                    }
                    if(StringUtils.isNotBlank(excelModel.getColumn2())
                            && mapBuildingTypeMap.containsKey(excelModel.getColumn2())){
                        extendsDefine.setTypeCode(mapBuildingTypeMap.get(excelModel.getColumn2()).getTypeCode());
                    } else {
                        errMsg += "分类名称必须与分类信息相匹配";
                    }

                    extendsDefine.setColumnName(excelModel.getColumn3());
                    extendsDefine.setColumnCnname(excelModel.getColumn4());
                    if(StringUtils.isNotBlank(excelModel.getColumn5()) && StringUtils.isNumeric(excelModel.getColumn5())){
                        extendsDefine.setColumnType(Integer.parseInt(excelModel.getColumn5()));
                    } else {
                        extendsDefine.setColumnType(0);
                    }

                    if(StringUtils.isNotBlank(excelModel.getColumn6())
                            && "true".equals(excelModel.getColumn6().toLowerCase().trim())){
                        extendsDefine.setRequired(true);
                    } else {
                        extendsDefine.setRequired(false);
                    }

                    if(StringUtils.isNotBlank(excelModel.getColumn7())
                            && "true".equals(excelModel.getColumn7().toLowerCase().trim())){
                        extendsDefine.setShow(true);
                    } else {
                        extendsDefine.setShow(false);
                    }

                    if(StringUtils.isNotBlank(excelModel.getColumn8()) && StringUtils.isNumeric(excelModel.getColumn8())){
                        extendsDefine.setOrderid(Integer.parseInt(excelModel.getColumn8()));
                    }

                    extendsDefine.setMemo(excelModel.getColumn9());

                    if(errMsg.length() > 0){
                        Map<String, Object> errMap = new HashMap<>();
                        errMap.put("errMsg", errMsg);
                        dataImportLog.addError(true, errMap);
                    } else {
                        extendsDefineList.add(extendsDefine);
                        dataImportLog.addImportedCount(true);
                    }
                }

                btExtendsDefineService.bulkSave(extendsDefineList);
            }
        }

    }

    List<Class<? extends BaseRowModel>> loadClazzList(){
        List<Class<? extends BaseRowModel>> clazzList = new ArrayList<>();
        clazzList.add(MapBuildingType.class);
        clazzList.add(ExcelModel.class);

        return clazzList;
    }

    List<List<List<String>>> loadHeadList(){
        List<List<List<String>>> headList = new ArrayList<>();
        String dataHead = "分类编号,分类名称,是否可点击(true/false),是否可搜索(true/false),分类描述,排序,备注";
        headList.add(ExcelUtils.loadHead(dataHead));
        String extendsHead = "属性ID,分类名称,英文名,中文名,数据类型：0：文本；1：日期；2：富文本"
                + ",是否必填(true/false),是否显示(true/false),排序,备注";
        headList.add(ExcelUtils.loadHead(extendsHead));

        return headList;
    }

    List<ExcelModel> loadExtendsDefineList(Map<Integer, MapBuildingType> buildingTypeMap,
                                           List<MapBtExtendsDefine> extendsDefineList){
        List<ExcelModel> list = new ArrayList<>();
        for(MapBtExtendsDefine extendsDefine : extendsDefineList){
            List<String> columns = new ArrayList<>();
            columns.add(extendsDefine.getColumnId().toString());
            columns.add(buildingTypeMap.get(extendsDefine.getTypeCode()).getTypeName());
            columns.add(extendsDefine.getColumnName());
            columns.add(extendsDefine.getColumnCnname());
            columns.add(extendsDefine.getColumnType().toString());
            columns.add(extendsDefine.getRequired() ? "TRUE" : "FALSE");
            columns.add(extendsDefine.getShow() ? "TRUE" : "FALSE");
            columns.add(extendsDefine.getOrderid() == null ? "" : extendsDefine.getOrderid().toString());
            columns.add(extendsDefine.getMemo());

            list.add(ExcelUtils.loadExcelModel(columns));
        }

        return list;
    }

    private Example<MapBuildingType> loadExample(String typeName){
        MapBuildingType mapBuildingType = new MapBuildingType();
        mapBuildingType.setTypeName(typeName);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("typeCode");

        return Example.of(mapBuildingType, exampleMatcher);
    }
}
