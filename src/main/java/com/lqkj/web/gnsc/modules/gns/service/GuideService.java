package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.GuideDao;
import com.lqkj.web.gnsc.modules.gns.dao.GuideVODao;
import com.lqkj.web.gnsc.modules.gns.dao.StudentTypeDao;
import com.lqkj.web.gnsc.modules.gns.domain.*;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import com.lqkj.web.gnsc.utils.DataImportLog;
import com.lqkj.web.gnsc.utils.ExcelModel;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import com.lqkj.web.gnsc.utils.GeoJSON;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.sl.draw.geom.Guide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GuideService {
    @Autowired
    private GuideDao guideDao;
    @Autowired
    private GuideVODao guideVODao;
    @Autowired
    private StudentTypeDao studentTypeDao;

    /**
     * 分页获取
     * @param title
     * @param page
     * @param pageSize
     * @return
     */
    public Page<GnsGuideVO> page(Integer schoolId,Integer campusCode, Integer typeCode, String title, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return guideVODao.page(schoolId,campusCode,typeCode, title, pageable);
    }

    /**
     * 根据校区和学生分类获取
     * @return
     */
    public List<GnsGuide> queryAll(Integer campusCode,Integer typeCode){return guideDao.findAllByCampusCodeAndAndStudnetTypeCode(campusCode,typeCode);}

    /**
     * 根据主键获取
     * @param guideId
     * @return
     */
    public GnsGuide get(Integer guideId){
        return guideDao.findById(guideId).get();
    }

    public GnsGuide checkIsExistWithCampusId( String guideName, Integer campusId){
        return guideDao.checkIsExistWithCampusId(guideName, campusId);
    }

    /**
     * 新增
     * @param guide
     * @return
     */
    public GnsGuide add(GnsGuide guide){
        //增加自动排序
        Integer maxOrder = guideDao.getMaxOrder(guide.getCampusCode());
        if (maxOrder !=null && guide.getOrderId() != null && guide.getOrderId() < maxOrder) {
            guideDao.autoOrder(guide.getOrderId(),guide.getCampusCode());
        }else {
            if (maxOrder == null) {
                guide.setOrderId(1);
            }else {
                guide.setOrderId(maxOrder + 1);
            }
        }
        return guideDao.save(guide);
    }

    /**
     * 更新
     * @param guide
     * @return
     */
    public GnsGuide update(GnsGuide guide){
        Integer maxOrder = guideDao.getMaxOrder(guide.getCampusCode());
        GnsGuide oldGnsGuide = guideDao.findById(guide.getGuideId()).get();
        if(oldGnsGuide.getOrderId() > guide.getOrderId()){
            guideDao.autoOrderForUpdateDesc(oldGnsGuide.getOrderId(),guide.getOrderId(),guide.getCampusCode());
        }else {
            if (maxOrder > guide.getOrderId() && oldGnsGuide.getOrderId() < guide.getOrderId()) {
                guideDao.autoOrderForUpdateAsc(oldGnsGuide.getOrderId(),guide.getOrderId(),guide.getCampusCode());
            }else if(oldGnsGuide.getOrderId() == guide.getOrderId()){
                guide.setOrderId(oldGnsGuide.getOrderId());
            }else{
                guideDao.autoOrderForUpdateAsc(oldGnsGuide.getOrderId(),maxOrder,guide.getCampusCode());
                guide.setOrderId(maxOrder);
            }
        }
        return guideDao.save(guide);
    }

    /**
     * 删除
     * @param guideId
     * @return
     */
    public int delete(Integer guideId){
        GnsGuide oldGnsGuide = this.get(guideId);
        guideDao.deleteById(guideId);
        //重新排序
        if(oldGnsGuide != null){
            guideDao.autoOrderForDelete(oldGnsGuide.getOrderId(),oldGnsGuide.getCampusCode());
        }
        return 1;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int bulkDelete(String ids){
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }

    /**
     * 导入模板
     */
    public ResponseEntity<InputStreamResource> exportTemplate(Integer schoolId) throws IOException {
        List<GnsStudentType> typeList = studentTypeDao.findAllBySchoolId(schoolId);

        return ExcelUtils.downloadMultipleSheetExcel(
                ExcelUtils.loadClazzListWithOneClass(ExcelModel.class, typeList.size()),
                loadHeadList(typeList),
                ExcelUtils.loadEmptyDataList(typeList.size()),
                loadSheetNameList(typeList),
                "迎新引导信息.xlsx");
    }

    /**
     * 导出
     * @return
     */
    public ResponseEntity<InputStreamResource> download(Integer schoolId) throws IOException {
        List<GnsStudentType> typeList = studentTypeDao.findAllBySchoolId(schoolId);

        return ExcelUtils.downloadMultipleSheetExcel(
                ExcelUtils.loadClazzListWithOneClass(ExcelModel.class, typeList.size()),
                loadHeadList(typeList),
                loadExportList(schoolId,typeList),
                loadSheetNameList(typeList),
                "迎新引导信息.xlsx");
    }

    public Object upload(InputStream inputStream, Integer schoolId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {

        List<GnsStudentType> typeList = studentTypeDao.findAllBySchoolId(schoolId);

        List<List<Object>> dataList = ExcelUtils.readMultipleSheetExcel(inputStream,
                ExcelUtils.loadClazzListWithOneClass(ExcelModel.class, typeList.size()));

        DataImportLog dataImportLog = new DataImportLog();
        dataImportLog.setCategory("迎新引导信息导入");
        if (dataList.size() != typeList.size()) {
            dataImportLog.setErrorCode(-1);
            dataImportLog.setErrorMsg("请重新下迎新引导导入模板");
        } else {
            List<GnsGuide> guideList = new ArrayList<>();
            for (int i = 0; i < typeList.size(); i++) {
                GnsStudentType studentType = typeList.get(i);

                List<Object> subDataList = dataList.get(i);

                dataImportLog.setSubCategory(studentType.getTypeName());
                dataImportLog.setSubTotalCount(subDataList.size());
                dataImportLog.setTotalCount(dataImportLog.getTotalCount() + dataImportLog.getSubTotalCount());

                for (Object object : subDataList) {
                    ExcelModel excelModel = (ExcelModel) object;
                    GnsGuide guide = new GnsGuide();
                    String errMsg = "";
                    guide.setStudnetTypeCode(studentType.getStudnetTypeCode());
                    if (excelModel.getColumn2() != null && StringUtils.isNumeric(excelModel.getColumn2())) {
                        guide.setCampusCode(Integer.parseInt(excelModel.getColumn2()));
                    } else {
                        errMsg += "区域组ID必填且必须是数字；";
                    }
                    guide.setTitle(excelModel.getColumn3());
                    guide.setContent(excelModel.getColumn4());

                    if (excelModel.getColumn5() != null) {
                        guide.setLngLat(GeoJSON.gjson.read(excelModel.getColumn5()));
                    }

                    if (excelModel.getColumn6() != null) {
                        guide.setRasterLngLat(GeoJSON.gjson.read(excelModel.getColumn6()));
                    }

                    if ( StringUtils.isNumeric(excelModel.getColumn7())) {
                        guide.setOrderId(Integer.parseInt(excelModel.getColumn7()));
                    } else {
                        errMsg = "排序必须是数字；";
                    }
                    if(errMsg.length() > 0){
                        Map<String, Object> errMap = new HashMap<>();
                        errMap.put("errMsg", errMsg);
                        dataImportLog.addError(false, errMap);
                        return dataImportLog;
                    }else {
                        guideList.add(guide);
                    }
                }
            }
            List<String> errMsgList = new ArrayList<>();
            guideList.forEach(v ->{
                GnsGuide guide = this.add(v);
                String errMsg = "";
                if(guide == null){
                    errMsg = v.getTitle() +  "导入失败";
                    errMsgList.add(errMsg);
                }
            });
            if(errMsgList.size() > 0){
                dataImportLog.addError(false,errMsgList);
                dataImportLog.setErrorCount(errMsgList.size());
            }
        }
        return dataImportLog;
    }


    private List<List<List<String>>> loadHeadList(List<GnsStudentType> typeList) {
        List<List<List<String>>> headList = new ArrayList<>();

        String publicHeadString = "学生分类编号,校区区域组ID,名称,内容,二维空间信息,三维空间信息,排序";
        for (GnsStudentType type : typeList) {
            String headString = publicHeadString;
            headList.add(ExcelUtils.loadHead(headString));
        }
        return headList;
    }

    private List<String> loadSheetNameList(List<GnsStudentType> typeList) {
        List<String> sheetNameList = new ArrayList<>();
        for (GnsStudentType type : typeList) {
            sheetNameList.add(type.getTypeName());
        }
        return sheetNameList;
    }

    private List<List<? extends BaseRowModel>> loadExportList(Integer schoolId,List<GnsStudentType> typeList) {
        List<List<? extends BaseRowModel>> list = new ArrayList<>();
        for (GnsStudentType studentType : typeList) {
            List<ExcelModel> subList = new ArrayList<>();

            List<GnsGuideVO> guideList = guideVODao.findAllByTypeCodeAndSchoolId(schoolId,studentType.getStudnetTypeCode());

            for (GnsGuideVO guideVO : guideList) {
                List<String> columns = new ArrayList<>();
                columns.add(guideVO.getTypeCode().toString());
                columns.add(guideVO.getCampusCode().toString());
                columns.add(guideVO.getTitle());
                columns.add(guideVO.getContent());
                columns.add(guideVO.getLngLatString());
                columns.add(guideVO.getRasterLngLat());
                columns.add(guideVO.getOrderId().toString());

                subList.add(ExcelUtils.loadExcelModel(columns));
            }
            list.add(subList);
        }
        return list;
    }

}
