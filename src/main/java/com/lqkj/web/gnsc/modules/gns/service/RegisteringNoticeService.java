package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.RegisteringNoticeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return MessageBean.ok(registeringNoticeDao.save(registeringNotice));
    }

    public MessageBean update(GnsRegisteringNotice registeringNotice){
        GnsRegisteringNotice notice = this.checkIsExistWithTitle(registeringNotice.getSchoolId(),registeringNotice.getTitle());
        if(notice != null && notice.getNoticeId() != registeringNotice.getNoticeId()){
            return MessageBean.error("报名须知名称重复");
        }
        return MessageBean.ok(registeringNoticeDao.save(registeringNotice));
    }

    public int delete(Integer noticeId){
        registeringNoticeDao.deleteById(noticeId);
        return 1;
    }

    public int bulkDelete(String ids){
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
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
