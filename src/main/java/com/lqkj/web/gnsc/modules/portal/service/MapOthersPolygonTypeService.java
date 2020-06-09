package com.lqkj.web.gnsc.modules.portal.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonTypeDao;
import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonType;
import com.lqkj.web.gnsc.utils.DataImportLog;
import com.lqkj.web.gnsc.utils.ExcelModel;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import com.lqkj.web.gnsc.utils.UtilPage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 其他面图元分类service实现
 */

@Service
@Transactional
public class MapOthersPolygonTypeService {

    @Autowired
    MapOthersPolygonTypeDao othersPolygonTypeDao;
    @Autowired
    private MapOptExtendsDefineService optExtendsDefineService;

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public UtilPage<MapOthersPolygonType> pageQuery(String typeName, Integer page, Integer pageSize) {
        try {
            PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
            Page<MapOthersPolygonType> pageInfo = othersPolygonTypeDao.findAll(loadExample(typeName), pageRequest);
            return objectMapper.readValue(
                    JSON.toJSONString(pageInfo),
                    UtilPage.class);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    public List<MapOthersPolygonType> queryList(String typeName) {
        try {
            Sort sort = new Sort(Sort.Direction.ASC, "orderId");
            return othersPolygonTypeDao.findAll(loadExample(typeName), sort);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }


    public MapOthersPolygonType add(MapOthersPolygonType mapOthersPolygonType) {

        return othersPolygonTypeDao.save(mapOthersPolygonType);
    }


    public MapOthersPolygonType queryById(Integer typeCode) {
        return othersPolygonTypeDao.findById(typeCode).get();
    }


    public MapOthersPolygonType update(MapOthersPolygonType mapOthersPolygonType) {
        return othersPolygonTypeDao.save(mapOthersPolygonType);
    }


    public Integer delete(Integer typeCode) {
        othersPolygonTypeDao.deleteById(typeCode);
        return 1;
    }


    public Integer bulkDelete(String ids) {
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }


    public ResponseEntity<InputStreamResource> exportTemplate() throws IOException {
        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                ExcelUtils.loadEmptyDataList(2),
                Arrays.asList("其他面图元分类信息", "扩展属性定义"),
                "其他面图元分类信息导入模板.xlsx");
    }


    public ResponseEntity<InputStreamResource> download(String userId) throws IOException {
        List<MapOthersPolygonType> list = this.queryList(null);
        Map<Integer, MapOthersPolygonType> polygonTypeMap = list
                .stream()
                .collect(Collectors.toMap(MapOthersPolygonType :: getTypeCode,
                        MapOthersPolygonType -> MapOthersPolygonType));

        List<MapOptExtendsDefine> extendsDefineList = optExtendsDefineService.loadAll();

        List<List<? extends BaseRowModel>> dataList = new ArrayList<>();
        dataList.add(list);
        dataList.add(loadExtendsDefineList(polygonTypeMap, extendsDefineList));

        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                dataList,
                Arrays.asList("其他面图元分类信息", "扩展属性定义"),
                "其他面图元分类信息.xlsx");
    }


    public void upload(InputStream inputStream) {
        DataImportLog dataImportLog = new DataImportLog("其他面图元分类信息导入", 0);
        List<List<Object>> readList = ExcelUtils.readMultipleSheetExcel(inputStream, loadClazzList());

        dataImportLog.setSubCategory("分类信息导入");
        dataImportLog.setSubTotalCount(readList.get(0).size());
        dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

        if(readList.get(0).size() > 0){
            List<MapOthersPolygonType> polygonTypeList = new ArrayList<>();
            for(Object object : readList.get(0)){
                MapOthersPolygonType polygonType = (MapOthersPolygonType) object;
                String errMsg = "";

                if(polygonType.getTypeName() == null){
                    errMsg += "面图元分类名称不能为空";
                }


                if(errMsg.length() > 0){
                    Map<String, Object> errMap = new HashMap<>();
                    errMap.put("errMsg", errMsg);
                    dataImportLog.addError(true, errMap);
                } else {
                    polygonTypeList.add(polygonType);
                    dataImportLog.addImportedCount(true);
                }
            }

            othersPolygonTypeDao.saveAll(polygonTypeList);

            if(readList.get(1).size() > 0){
                dataImportLog.setSubCategory("扩展属性导入");
                dataImportLog.setSubTotalCount(readList.get(1).size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

                Map<String, MapOthersPolygonType> polygonTypeMap = this.queryList(null)
                        .stream()
                        .collect(Collectors.toMap(MapOthersPolygonType::getTypeName, k -> k));

                List<MapOptExtendsDefine> extendsDefineList = new ArrayList<>();
                for(Object object : readList.get(1)){
                    ExcelModel excelModel = (ExcelModel) object;
                    String errMsg = "";

                    MapOptExtendsDefine extendsDefine = new MapOptExtendsDefine();
                    if(excelModel.getColumn1() != null && StringUtils.isNumeric(excelModel.getColumn1())){
                        extendsDefine.setColumnId(Integer.parseInt(excelModel.getColumn1()));
                    }
                    if(StringUtils.isNotBlank(excelModel.getColumn2())
                            && polygonTypeMap.containsKey(excelModel.getColumn2())){
                        extendsDefine.setTypeCode(polygonTypeMap.get(excelModel.getColumn2()).getTypeCode());
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
                optExtendsDefineService.bulkSave(extendsDefineList);
            }
        }
    }

    List<Class<? extends BaseRowModel>> loadClazzList(){
        List<Class<? extends BaseRowModel>> clazzList = new ArrayList<>();
        clazzList.add(MapOthersPolygonType.class);
        clazzList.add(ExcelModel.class);

        return clazzList;
    }

    List<List<List<String>>> loadHeadList(){
        List<List<List<String>>> headList = new ArrayList<>();
        String dataHead = "分类编号,分类名称,是否可点击(true/false),是否可搜索(true/false),描述,排序,备注";
        headList.add(ExcelUtils.loadHead(dataHead));
        String extendsHead = "属性ID,分类名称,英文名,中文名,数据类型：0：文本；1：日期；2：富文本"
                + ",是否必填(true/false),是否显示(true/false),排序,备注";
        headList.add(ExcelUtils.loadHead(extendsHead));

        return headList;
    }

    List<ExcelModel> loadExtendsDefineList(Map<Integer, MapOthersPolygonType> polygonTypeMap,
                                           List<MapOptExtendsDefine> extendsDefineList){
        List<ExcelModel> list = new ArrayList<>();
        for(MapOptExtendsDefine extendsDefine : extendsDefineList){
            List<String> columns = new ArrayList<>();
            columns.add(extendsDefine.getColumnId().toString());
            columns.add(polygonTypeMap.get(extendsDefine.getTypeCode()).getTypeName());
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

    private Example<MapOthersPolygonType> loadExample(String typeName){
        MapOthersPolygonType mapOthersPolygonType = new MapOthersPolygonType();
        mapOthersPolygonType.setTypeName(typeName);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("typeCode");

        return Example.of(mapOthersPolygonType, exampleMatcher);
    }
}
