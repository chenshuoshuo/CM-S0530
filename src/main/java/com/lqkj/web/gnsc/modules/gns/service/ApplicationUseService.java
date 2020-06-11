package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.ApplicationUseDao;
import com.lqkj.web.gnsc.modules.gns.dao.MapUseDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplicationUse;
import com.lqkj.web.gnsc.modules.gns.domain.GnsMapUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/1 19:53
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class ApplicationUseService {
    @Autowired
    private ApplicationUseDao applicationUseDao;

    public GnsApplicationUse save(String userCode, Integer appId) {
        GnsApplicationUse applicationUse = new GnsApplicationUse(UUID.randomUUID().toString(),appId,userCode);
        return applicationUseDao.save(applicationUse);
    }
}
