package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.HelperDao;
import com.lqkj.web.gnsc.modules.gns.dao.HelperTypeDao;
import com.lqkj.web.gnsc.modules.gns.dao.HelperVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelperType;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsHelperVO;
import com.lqkj.web.gnsc.utils.ExcelModel;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HelperService {
    @Autowired
    private HelperDao helperDao;
    @Autowired
    private HelperVODao helperVODao;
    @Autowired
    private HelperTypeDao helperTypeDao;

    public Page<GnsHelperVO> page(Integer typeCode, String title, Integer page, Integer pageSize){

        Pageable pageable = PageRequest.of(page,pageSize);
        return helperVODao.page(typeCode,title,pageable);
    }

    public List<GnsHelper> queryList(Integer categoryId){
        return helperDao.findAllByTypeCode(categoryId);
    }


    public GnsHelper get(Integer helperId){
        return helperDao.findById(helperId).get();
    }

    public GnsHelper checkByContact(String contact){
        return helperDao.findByContact(contact);
    }

    public MessageBean add(GnsHelper helper){
        GnsHelper he = this.checkByContact(helper.getContact());
        if(he != null){
            return MessageBean.error("联系方式已存在");
        }else {
            Integer maxOrder = helperDao.getMaxOrder();
            if (maxOrder !=null && helper.getOrderId() != null && helper.getOrderId() < maxOrder) {
                helperDao.autoOrder(helper.getOrderId());
            }else {
                if (maxOrder == null) {
                    helper.setOrderId(1);
                }else {
                    helper.setOrderId(maxOrder + 1);
                }
            }
            return MessageBean.ok(helperDao.save(helper));
        }
    }

    public MessageBean update(GnsHelper helper){
        GnsHelper he = this.checkByContact(helper.getContact());
        if(he != null && he.getHelperId() != helper.getHelperId()){
            return MessageBean.error("联系方式已存在");
        }
        Integer maxOrder = helperDao.getMaxOrder();
        GnsHelper oldGnsHelper = helperDao.findById(helper.getHelperId()).get();
        if(oldGnsHelper.getOrderId() > helper.getOrderId()){
            helperDao.autoOrderForUpdateDesc(oldGnsHelper.getOrderId(),helper.getOrderId());
        }else {
            if (maxOrder > helper.getOrderId() && oldGnsHelper.getOrderId() < helper.getOrderId()) {
                helperDao.autoOrderForUpdateAsc(oldGnsHelper.getOrderId(),helper.getOrderId());
            }else if(oldGnsHelper.getOrderId() == helper.getOrderId()){
                helper.setOrderId(oldGnsHelper.getOrderId());
            }else{
                helperDao.autoOrderForUpdateAsc(oldGnsHelper.getOrderId(),maxOrder);
                helper.setOrderId(maxOrder);
            }
        }
        return MessageBean.ok(helperDao.save(helper));
    }


    public int delete(Integer helperId){
        GnsHelper helper = this.get(helperId);
        helperDao.deleteById(helperId);
        //重新排序
        if(helper != null){
            helperDao.autoOrderForDelete(helper.getOrderId());
        }
        return 1;
    }

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
        List<GnsHelperType> typeList = helperTypeDao.findAllBySchoolId(schoolId);

        return ExcelUtils.downloadMultipleSheetExcel(
                ExcelUtils.loadClazzListWithOneClass(ExcelModel.class, typeList.size()),
                loadHeadList(typeList),
                ExcelUtils.loadEmptyDataList(typeList.size()),
                loadSheetNameList(typeList),
                "通讯录导入模板.xlsx");
    }

    /**
     * 导出
     * @return
     */
    public ResponseEntity<InputStreamResource> download(Integer schoolId) throws IOException {
        List<GnsHelperType> typeList = helperTypeDao.findAllBySchoolId(schoolId);

        return ExcelUtils.downloadMultipleSheetExcel(
                ExcelUtils.loadClazzListWithOneClass(ExcelModel.class, typeList.size()),
                loadHeadList(typeList),
                loadExportList(typeList),
                loadSheetNameList(typeList),
                "通讯录信息.xlsx");
    }


    private List<List<List<String>>> loadHeadList(List<GnsHelperType> typeList) {
        List<List<List<String>>> headList = new ArrayList<>();

        String publicHeadString = "分类编号,名称,联系号码,排序,备注";
        for (GnsHelperType helperType : typeList) {
            String headString = publicHeadString;
            headList.add(ExcelUtils.loadHead(headString));
        }
        return headList;
    }

    private List<String> loadSheetNameList(List<GnsHelperType> typeList) {
        List<String> sheetNameList = new ArrayList<>();
        for (GnsHelperType type : typeList) {
            sheetNameList.add(type.getTypeName());
        }
        return sheetNameList;
    }

    private List<List<? extends BaseRowModel>> loadExportList(List<GnsHelperType> typeList) {
        List<List<? extends BaseRowModel>> list = new ArrayList<>();
        for (GnsHelperType helperType : typeList) {
            List<ExcelModel> subList = new ArrayList<>();

            List<GnsHelper> helperList = helperDao.findAllByTypeCode(helperType.getTypeCode());

            for (GnsHelper helper : helperList) {
                List<String> columns = new ArrayList<>();
                columns.add(helper.getTypeCode().toString());
                columns.add(helper.getTitle());
                columns.add(helper.getContact());
                columns.add(helper.getOrderId().toString());
                columns.add(helper.getMemo());
                subList.add(ExcelUtils.loadExcelModel(columns));
            }
            list.add(subList);
        }
        return list;
    }
}
