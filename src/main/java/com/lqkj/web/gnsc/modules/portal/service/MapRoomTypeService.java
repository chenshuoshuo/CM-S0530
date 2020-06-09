package com.lqkj.web.gnsc.modules.portal.service;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.fastjson.JSON;
import com.lqkj.web.gnsc.modules.base.BaseService;
import com.lqkj.web.gnsc.modules.portal.dao.MapRoomTypeDao;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomType;
import com.lqkj.web.gnsc.modules.portal.model.MapRtExtendsDefine;
import com.lqkj.web.gnsc.utils.DataImportLog;
import com.lqkj.web.gnsc.utils.ExcelModel;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import com.lqkj.web.gnsc.utils.UtilPage;
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
public class MapRoomTypeService extends BaseService {
    @Autowired
    MapRoomTypeDao mapRoomTypeDao;
    @Autowired
    MapRtExtendsDefineService rtExtendsDefineService;

    public UtilPage<MapRoomType> pageQuery(String typeName, Integer page, Integer pageSize)  {
        try {
            PageRequest pageRequest =  PageRequest.of(page, pageSize, new Sort(Sort.Direction.ASC, "orderId"));
            Page<MapRoomType> pageInfo = mapRoomTypeDao.findAll(loadExample(typeName), pageRequest);
            return objectMapper.readValue(JSON.toJSONString(pageInfo), UtilPage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<MapRoomType> queryList(String typeName) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderId");
        return mapRoomTypeDao.findAll(loadExample(typeName), sort);
    }


    public MapRoomType add(MapRoomType mapRoomType) {
        mapRoomType.setTypeCode(mapRoomTypeDao.queryMaxTypeCode());
        return mapRoomTypeDao.save(mapRoomType);
    }


    public MapRoomType queryById(Integer typeCode) {
        return mapRoomTypeDao.findById(typeCode).get();
    }


    public MapRoomType update(MapRoomType mapRoomType) {
        return  mapRoomTypeDao.save(mapRoomType);
    }


    public Integer delete(Integer typeCode) {
        mapRoomTypeDao.deleteById(typeCode);
        return 1;
    }


    public Integer bulkDelete(String ids) {
        String[] idArray = ids.split(",");
        for(String str : idArray){
            mapRoomTypeDao.deleteById(Integer.parseInt(str));
        }
        return idArray.length;
    }


    public List<MapRoomType> saveAll(List<MapRoomType> roomTypeList) {
        for(MapRoomType type : roomTypeList){
            if(type.getTypeCode() == null){
                type.setTypeCode(mapRoomTypeDao.queryMaxTypeCode());
            }
        }

        return  mapRoomTypeDao.saveAll(roomTypeList);
    }

    public ResponseEntity<InputStreamResource> exportTemplate() throws IOException {
        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                ExcelUtils.loadEmptyDataList(2),
                Arrays.asList("房间分类信息", "扩展属性定义"),
                "房间分类信息导入模板.xlsx");
    }


    public ResponseEntity<InputStreamResource> download(String userId) throws IOException {
        List<MapRoomType> list = this.queryList(null);

        Map<Integer, MapRoomType> roomTypeMap = list
                .stream()
                .collect(Collectors.toMap(MapRoomType :: getTypeCode,
                        MapRoomType -> MapRoomType));

        List<MapRtExtendsDefine> extendsDefineList = rtExtendsDefineService.loadAll();

        List<List<? extends BaseRowModel>> dataList = new ArrayList<>();
        dataList.add(list);
        dataList.add(loadExtendsDefineList(roomTypeMap, extendsDefineList));

        return ExcelUtils.downloadMultipleSheetExcel(loadClazzList(),
                loadHeadList(),
                dataList,
                Arrays.asList("房间分类信息", "扩展属性定义"),
                "房间分类信息.xlsx");

    }

    public void upload(InputStream inputStream) {
        DataImportLog dataImportLog = new DataImportLog("房间分类信息导入", 0);
        List<List<Object>> readList = ExcelUtils.readMultipleSheetExcel(inputStream, loadClazzList());

        dataImportLog.setSubCategory("房间分类信息导入");
        dataImportLog.setSubTotalCount(readList.get(0).size());
        dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

        if(readList.get(0).size() > 0){
            List<MapRoomType> roomTypeList = new ArrayList<>();
            for(Object object : readList.get(0)){
                MapRoomType roomType = (MapRoomType) object;
                String errMsg = "";

                if(roomType.getTypeName() == null){
                    errMsg += "分类名称不能为空";
                }


                if(errMsg.length() > 0){
                    Map<String, Object> errMap = new HashMap<>();
                    errMap.put("errMsg", errMsg);
                    dataImportLog.addError(true, errMap);
                } else {
                    roomTypeList.add(roomType);
                    dataImportLog.addImportedCount(true);
                }
            }

            mapRoomTypeDao.saveAll(roomTypeList);

            if(readList.get(1).size() > 0){
                dataImportLog.setSubCategory("扩展属性导入");
                dataImportLog.setSubTotalCount(readList.get(1).size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

                Map<String, MapRoomType> roomTypeMap = this.queryList(null)
                        .stream()
                        .collect(Collectors.toMap(MapRoomType::getTypeName, k -> k));

                List<MapRtExtendsDefine> extendsDefineList = new ArrayList<>();
                for(Object object : readList.get(1)){
                    ExcelModel excelModel = (ExcelModel) object;
                    String errMsg = "";

                    MapRtExtendsDefine extendsDefine = new MapRtExtendsDefine();
                    if(excelModel.getColumn1() != null && StringUtils.isNumeric(excelModel.getColumn1())){
                        extendsDefine.setColumnId(Integer.parseInt(excelModel.getColumn1()));
                    }
                    if(StringUtils.isNotBlank(excelModel.getColumn2())
                            && roomTypeMap.containsKey(excelModel.getColumn2())){
                        extendsDefine.setTypeCode(roomTypeMap.get(excelModel.getColumn2()).getTypeCode());
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

                rtExtendsDefineService.bulkSave(extendsDefineList);
            }
        }
    }

    List<Class<? extends BaseRowModel>> loadClazzList(){
        List<Class<? extends BaseRowModel>> clazzList = new ArrayList<>();
        clazzList.add(MapRoomType.class);
        clazzList.add(ExcelModel.class);

        return clazzList;
    }

    List<List<List<String>>> loadHeadList(){
        List<List<List<String>>> headList = new ArrayList<>();
        String dataHead = "分类编号,分类名称,是否可点击(true/false),是否可搜索(true/false),分类描述,分类排序,备注";
        headList.add(ExcelUtils.loadHead(dataHead));
        String extendsHead = "属性ID,分类名称,英文名,中文名,数据类型：0：文本；1：日期；2：富文本"
                + ",是否必填(true/false),是否显示(true/false),排序,备注";
        headList.add(ExcelUtils.loadHead(extendsHead));

        return headList;
    }

    List<ExcelModel> loadExtendsDefineList(Map<Integer, MapRoomType> roomTypeMap,
                                           List<MapRtExtendsDefine> extendsDefineList){
        List<ExcelModel> list = new ArrayList<>();
        for(MapRtExtendsDefine extendsDefine : extendsDefineList){
            List<String> columns = new ArrayList<>();
            columns.add(extendsDefine.getColumnId().toString());
            columns.add(roomTypeMap.get(extendsDefine.getTypeCode()).getTypeName());
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

    private Example<MapRoomType> loadExample(String typeName){
        MapRoomType mapRoomType = new MapRoomType();
        mapRoomType.setTypeName(typeName);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("typeName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("typeCode");

        return Example.of(mapRoomType, exampleMatcher);
    }
}
