package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.GnsSignDao;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 * @Date 2020/6/1 19:53
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class GnsSignService {

    @Autowired
    private GnsSignDao signDao;

    public List<Map<String,Object>> queryList(Integer schoolId){

        return signDao.queryList(schoolId);
    }
}
