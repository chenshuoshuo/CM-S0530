package com.lqkj.web.gnsc.modules.gns.service;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsApplicationDao;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolCampusDao;
import com.lqkj.web.gnsc.modules.gns.dao.SchoolInfoDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.domain.GnsCampusInfo;
import com.lqkj.web.gnsc.modules.gns.domain.GnsSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SchoolInfoService {

    @Autowired
    private SchoolInfoDao schoolInfoDao;
    @Autowired
    private SchoolCampusDao campusDao;
    @Autowired
    private GnsApplicationDao applicationDao;

    public List<GnsSchool> listAll(){ return schoolInfoDao.findAll(); }

    public Page<GnsSchool> page(Integer schoolId, String schoolName,Integer page,Integer pageSize){
        PageRequest pageRequest = PageRequest.of(page,pageSize,new Sort(Sort.Direction.ASC,"schoolId"));
        Page<GnsSchool> pageInfo = schoolInfoDao.findAll(loadGnsSchoolExample(schoolId,schoolName),pageRequest);
        if(pageInfo.getContent().size() > 0){
            pageInfo.getContent().forEach(v ->{
                Integer campusCount = campusDao.campusCount(v.getSchoolId());
                v.setCampusCount(campusCount);
            });
        }
        return  pageInfo;
    }

    public GnsSchool getSchoolByName(String schoolName){return schoolInfoDao.findBySchoolName(schoolName);}

    public GnsSchool get(Integer schoolId){ return schoolInfoDao.findBySchoolId(schoolId); }

    public GnsSchool getDefaultSchool(){
        List<GnsSchool> schoolInfoList = schoolInfoDao.findAll();
        return  schoolInfoList.size() > 0 ? schoolInfoList.get(0) : null;
    }

    public MessageBean add(GnsSchool schoolInfo, String ids) {
        GnsSchool school = schoolInfoDao.findBySchoolName(schoolInfo.getSchoolName());
        if (school == null) {
            GnsSchool s = schoolInfoDao.save(schoolInfo);
            String[] idArray = ids.split(",");
            for (String str : idArray) {
                GnsCampusInfo schoolCampus = campusDao.findByCampusCode(Integer.parseInt(str));
                schoolCampus.setSchoolId(s.getSchoolId());
                campusDao.save(schoolCampus);
            }

            GnsApplication application = new GnsApplication();

            application = new GnsApplication(null, null, s.getSchoolId(), "迎新引导", "gns_enrollment",
                    true, null, null,null, null, true, 1, null);
            GnsApplication enrollment = applicationDao.save(application);
            //二级应用
            application = new GnsApplication(null, enrollment.getApplicationId(), s.getSchoolId(), "去学校", "go_school",
                    true, null, null, null, null,true, 1, null);
            applicationDao.save(application);

            application = new GnsApplication(null, enrollment.getApplicationId(), s.getSchoolId(), "报道须知", "register_notice",
                    true, null, null, null,null, true, 2, null);
            applicationDao.save(application);

            application = new GnsApplication(null, enrollment.getApplicationId(), s.getSchoolId(), "更多应用", "more_application",
                    true, null, null,null, null, true, 3, null);
            GnsApplication moreApplication = applicationDao.save(application);
            //三级应用
            application = new GnsApplication(null, moreApplication.getApplicationId(), s.getSchoolId(), "迎新接待", "reception_place",
                    true, null, null, null,null, false, 1, null);
            applicationDao.save(application);

            application = new GnsApplication(null, moreApplication.getApplicationId(), s.getSchoolId(), "迎新助手", "gns_helper",
                    true, null, null, null,null, false, 2, null);
            GnsApplication helper = applicationDao.save(application);
            //四级应用
            application = new GnsApplication(null, helper.getApplicationId(), s.getSchoolId(), "迎新通讯录", "contact",
                    true, null, null, null,null, false, 1, null);
            applicationDao.save(application);

            application = new GnsApplication(null, helper.getApplicationId(), s.getSchoolId(), "宿舍导航", "dorm_navigation",
                    true, null, null, null,null, false, 2, null);
            applicationDao.save(application);

            application = new GnsApplication(null, moreApplication.getApplicationId(), s.getSchoolId(), "校园社团", "campus_club",
                    true, null, null,null, null, false, 3, null);
            applicationDao.save(application);

            application = new GnsApplication(null, moreApplication.getApplicationId(), s.getSchoolId(), "迎新高校联盟", "school_league",
                    true, null, null,null, null, false, 4, null);
            applicationDao.save(application);


            application = new GnsApplication(null, null, s.getSchoolId(), "云游校园", "campus_cutural",
                    true, null, null, null,null, true, 2, null);
            applicationDao.save(application);

            application = new GnsApplication(null, null, s.getSchoolId(), "校园VR", "campus_roman",
                    true, null, null, null,null, true, 3, null);
            applicationDao.save(application);

            application = new GnsApplication(null, null, s.getSchoolId(), "个人中心", "personal_center",
                    true, null, null,null, null, true, 4, null);
            applicationDao.save(application);

            return MessageBean.ok(s);
        }else {
            return MessageBean.error("学校名称重复");
        }
    }

    public GnsSchool update(GnsSchool schoolInfo){
        return schoolInfoDao.save(schoolInfo);
    }

    public int delete(Integer schoolId){
        List<GnsCampusInfo> schoolCampusList = campusDao.findBySchoolId(schoolId);
        for(GnsCampusInfo campusInfo : schoolCampusList){
            campusInfo.setSchoolId(null);
            campusDao.save(campusInfo);
        }
       schoolInfoDao.deleteById(schoolId);
        return 1;
    }

    public int bulkDelete(String ids){

        String[] idArray = ids.split(",");
        for(String str : idArray) {
            this.delete(Integer.parseInt(str));
        }
        return idArray.length;
    }

    public int countSchool(){return schoolInfoDao.countSchool();}

    private Example<GnsSchool> loadGnsSchoolExample(Integer schoolId,String schoolName){

        GnsSchool school = new GnsSchool();
        school.setSchoolId(schoolId);
        school.setSchoolName(schoolName);

        ExampleMatcher exampleMatcher  = ExampleMatcher.matching()
                .withMatcher("schoolId",ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("schoolName",ExampleMatcher.GenericPropertyMatchers.contains());

        return Example.of(school,exampleMatcher);

    }

}
