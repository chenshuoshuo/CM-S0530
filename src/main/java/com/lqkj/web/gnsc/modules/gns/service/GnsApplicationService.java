package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.GnsApplicationDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsApplicationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.SaslClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GnsApplicationService{
    @Autowired
    private GnsApplicationDao applicationDao;

    public List<Map<String,Object>> getParentAppList(Integer schoolId) {

        return applicationDao.appList(schoolId);
    }

    public List<Map<String,Object>> getSubAppList(String enName,Integer schoolId) {
        GnsApplication application = applicationDao.existWithEnName(enName,schoolId);
        if(application != null){
            return applicationDao.findAllByParentId(application.getApplicationId(),schoolId);
        }else {
            return new ArrayList<>();
        }

    }


    public Page<GnsApplication> page(Integer schoolId,String appName, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page,pageSize,new Sort(Sort.Direction.ASC,"orderId"));
        return applicationDao.findAll(loadApplicationExample(schoolId,appName),pageRequest);
    }


    public GnsApplication existWithEnName(String enName,Integer schoolId) {
        return applicationDao.existWithEnName(enName,schoolId);
    }


    public GnsApplication get(Integer appId) {
        return applicationDao.findById(appId).get();
    }


    public GnsApplication add(GnsApplication application) {
        //增加自动排序
        GnsApplication app = applicationDao.existWithEnName("more_application",application.getSchoolId());
        Integer maxOrder = applicationDao.getMaxOrder(application.getSchoolId());
        if (maxOrder !=null && application.getOrderId() < maxOrder) {
            applicationDao.autoOrder(application.getOrderId(),application.getSchoolId());
        }else {
            if (maxOrder == null) {
                application.setOrderId(1);
            }else {
                application.setOrderId(maxOrder + 1);
            }
        }
        application.setPreset(false);
        application.setApplicationOpen(true);
        application.setParentId(app.getApplicationId());
        return applicationDao.save(application);
    }


    public GnsApplication update(GnsApplication application) {
        //增加自动排序
        Integer maxOrder = applicationDao.getMaxOrder(application.getSchoolId());
        GnsApplication oldApplication = applicationDao.findById(application.getApplicationId()).get();
        if(oldApplication.getOrderId() > application.getOrderId()){
            applicationDao.autoOrderForUpdateDesc(oldApplication.getOrderId(),application.getOrderId(),application.getSchoolId());
        }else {
            if (maxOrder > application.getOrderId() && oldApplication.getOrderId() < application.getOrderId()) {
                applicationDao.autoOrderForUpdateAsc(oldApplication.getOrderId(),application.getOrderId(),application.getSchoolId());
            }else if(oldApplication.getOrderId() == application.getOrderId()){
                application.setOrderId(oldApplication.getOrderId());
            }else{
                applicationDao.autoOrderForUpdateAsc(oldApplication.getOrderId(),maxOrder,application.getSchoolId());
                application.setOrderId(maxOrder);
            }
        }
        return applicationDao.save(application);
    }


    public GnsApplication updateOpen(Integer appId, Boolean open) {
        GnsApplication application = applicationDao.findById(appId).get();
        application.setApplicationOpen(open);
        return applicationDao.save(application);
    }


    public Integer delete(Integer appId) {
        GnsApplication application = this.get(appId);
        applicationDao.deleteById(appId);
        //重新排序
        if(application != null){
            applicationDao.autoOrderForDelete(application.getOrderId(),application.getSchoolId());
        }
        return 1;
    }


    public Integer bulkDelete(String ids) {
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }


    public Example<GnsApplication> loadApplicationExample(Integer schoolId,String appName){
        GnsApplication ap = new GnsApplication();
        ap.setApplicationName(appName);
        ap.setSchoolId(schoolId);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("applicationName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("schoolId",ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("applicationId");

        return Example.of(ap,exampleMatcher);
    }
}
