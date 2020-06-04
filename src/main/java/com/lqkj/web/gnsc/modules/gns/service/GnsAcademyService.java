package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSONArray;
import com.lqkj.web.gnsc.modules.gns.dao.GnsAcademyDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsPushMessageDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAcademy;
import com.lqkj.web.gnsc.modules.gns.domain.GnsPushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author cs
 */
@Service
@Transactional
public class GnsAcademyService {

    @Autowired
    private GnsAcademyDao academyDao;

    /**
     *
     * 设置消息已读
     */
    public JSONArray findBySchoolId(Integer schoolId){
        String result = academyDao.findAllBySchoolId(schoolId);
        if(result != null){
           return JSONArray.parseArray(result);
        }
        return new JSONArray();
    }

}
