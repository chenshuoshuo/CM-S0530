package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.RegisteringNoticeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionPlace;
import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import com.lqkj.web.gnsc.utils.DataImportLog;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import com.lqkj.web.gnsc.utils.GeoJSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
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
public class RegisteringNoticeService {

    @Autowired
    private RegisteringNoticeDao registeringNoticeDao;

    /**
     * 分页
     * @param schoolId
     * @param title
     * @return
     */
    public Page<GnsRegisteringNotice> page(Integer schoolId,String title,Integer page,Integer pageSize){

        PageRequest pageRequest = PageRequest.of(page,pageSize,new Sort(Sort.Direction.DESC,"updateTime"));
        return registeringNoticeDao.findAll(loadGnsRegisteringNoticeExample(schoolId,title),pageRequest);
    }

    /**
     * h5获取列表
     * @return
     */
    public List<Map<String,Object>> queryList(Integer schoolId){
        return registeringNoticeDao.queryList(schoolId);
    }

    public GnsRegisteringNotice get(Integer noticeId){
        return registeringNoticeDao.findById(noticeId).get();
    }

    public GnsRegisteringNotice checkIsExistWithTitle(Integer schoolId,String title){
        return registeringNoticeDao.findBySchoolIdAndTitle(schoolId,title);
    }

    public MessageBean<GnsRegisteringNotice> add(GnsRegisteringNotice registeringNotice){
        GnsRegisteringNotice notice = this.checkIsExistWithTitle(registeringNotice.getSchoolId(),registeringNotice.getTitle());
        if(notice != null){
            return MessageBean.error("报名须知名称重复");
        }
        Integer maxOrder = registeringNoticeDao.getMaxOrder(registeringNotice.getSchoolId());
        if (maxOrder !=null && registeringNotice.getOrderId() != null && registeringNotice.getOrderId() < maxOrder) {
            registeringNoticeDao.autoOrder(registeringNotice.getOrderId(),registeringNotice.getSchoolId());
        }else {
            if (maxOrder == null) {
                registeringNotice.setOrderId(1);
            }else {
                registeringNotice.setOrderId(maxOrder + 1);
            }
        }

        return MessageBean.ok(registeringNoticeDao.save(registeringNotice));
    }

    public MessageBean update(GnsRegisteringNotice registeringNotice){
        GnsRegisteringNotice notice = this.checkIsExistWithTitle(registeringNotice.getSchoolId(),registeringNotice.getTitle());
        if(notice != null && notice.getNoticeId() != registeringNotice.getNoticeId()){
            return MessageBean.error("报名须知名称重复");
        }
        Integer maxOrder = registeringNoticeDao.getMaxOrder(registeringNotice.getSchoolId());
        GnsRegisteringNotice oldGnsNotice = registeringNoticeDao.findById(registeringNotice.getNoticeId()).get();
        if(oldGnsNotice.getOrderId() > registeringNotice.getOrderId()){
            registeringNoticeDao.autoOrderForUpdateDesc(oldGnsNotice.getOrderId(),registeringNotice.getOrderId(),registeringNotice.getSchoolId());
        }else {
            if (maxOrder > registeringNotice.getOrderId() && oldGnsNotice.getOrderId() < registeringNotice.getOrderId()) {
                registeringNoticeDao.autoOrderForUpdateAsc(oldGnsNotice.getOrderId(),registeringNotice.getOrderId(),registeringNotice.getSchoolId());
            }else if(oldGnsNotice.getOrderId() == registeringNotice.getOrderId()){
                registeringNotice.setOrderId(oldGnsNotice.getOrderId());
            }else{
                registeringNoticeDao.autoOrderForUpdateAsc(oldGnsNotice.getOrderId(),maxOrder,registeringNotice.getSchoolId());
                registeringNotice.setOrderId(maxOrder);
            }
        }
        return MessageBean.ok(registeringNoticeDao.save(registeringNotice));
    }

    public int delete(Integer noticeId){
        GnsRegisteringNotice registeringNotice = this.get(noticeId);
        registeringNoticeDao.deleteById(noticeId);
        //重新排序
        if(registeringNotice != null){
            registeringNoticeDao.autoOrderForDelete(registeringNotice.getOrderId(),registeringNotice.getSchoolId());
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
     * 导出
     * @return
     */
    public ResponseEntity<InputStreamResource> download(Integer schoolId) throws IOException {
        List<GnsRegisteringNotice> noticeList = registeringNoticeDao.findAllBySchoolId(schoolId);
        return ExcelUtils.downloadOneSheetExcel(GnsRegisteringNotice.class, noticeList, "报道须知", "报道须知.xlsx");
    }

    public Object upload(InputStream inputStream, Integer schoolId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {

        List<Object> dataList = ExcelUtils.readOneSheetExcel(inputStream, GnsRegisteringNotice.class);
        DataImportLog dataImportLog = new DataImportLog("迎新接待点导入", dataList.size());
        List<GnsRegisteringNotice> list = new ArrayList<>();
        for (int i = 0; i < dataImportLog.getTotalCount(); i++) {
            GnsRegisteringNotice notice = (GnsRegisteringNotice) dataList.get(i);
            String errMsg = "";

            if (notice.getSchoolId() == null || !StringUtils.isNumeric(notice.getSchoolId().toString())) {
                errMsg += "学校编号不能为空且只能是数字；";
            }

            if (notice.getTitle() == null) {
                errMsg += "接待点名称不能为空；";
            }

            if (notice.getOrderId() != null && !StringUtils.isNumeric(notice.getOrderId().toString())) {
                errMsg += "排序只能是数字；";
            }

            if (errMsg.length() > 0) {
                Map<String, Object> errMap = new HashMap<>();
                errMap.put("errMsg", errMsg);
                dataImportLog.addError(false, errMap);
            } else {
                list.add(notice);
            }
        }
        List<String> errMsgList = new ArrayList<>();
        list.forEach(v ->{
            MessageBean messageBean = this.add(v);
            String errMsg = "";
            if(!messageBean.isStatus()){
                errMsg =  v.getTitle() + messageBean.getMessage();
                errMsgList.add(errMsg);
            }
        });
        if(errMsgList.size() > 0){
            dataImportLog.addError(false,errMsgList);
            dataImportLog.setErrorCount(errMsgList.size());
        }
        return dataImportLog;
    }


    public Example<GnsRegisteringNotice> loadGnsRegisteringNoticeExample(Integer schoolId, String title){
        GnsRegisteringNotice ap = new GnsRegisteringNotice();
        ap.setTitle(title);
        ap.setSchoolId(schoolId);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("schoolId",ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("noticeId");

        return Example.of(ap,exampleMatcher);
    }
}
