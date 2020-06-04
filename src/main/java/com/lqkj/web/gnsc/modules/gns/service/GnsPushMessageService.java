package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSONArray;
import com.lqkj.web.gnsc.modules.gns.dao.DormNavigationDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsPushMessageDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsPushMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author cs
 */
@Service
@Transactional
public class GnsPushMessageService {

    @Autowired
    private GnsPushMessageDao pushMessageDao;

    /**
     *
     * 设置消息已读
     */
    public GnsPushMessage changeValid(String pushId){
        GnsPushMessage message = pushMessageDao.findByPushId(UUID.fromString(pushId));
        if(message != null){
            message.setValid(false);
            return pushMessageDao.save(message);
        }
        return null;
    }

}
