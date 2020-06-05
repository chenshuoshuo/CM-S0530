package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.FeedbackDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsUserInfoDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/1 19:53
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private GnsUserInfoDao userInfoDao;

    public MessageBean save(String userId, String content) {
        if (content.equals(""))
            return MessageBean.error("反馈内容不能为空");
        GnsUserInfo userInfo = userInfoDao.findByUUID(userId);
        if (userInfo == null)
            return MessageBean.error("不存在该用户");
        feedbackDao.saveFeedback(UUID.fromString(userId),
                userInfo.getNickname() == null ? "" : userInfo.getNickname(),
                content,
                new Timestamp(System.currentTimeMillis()));
        return MessageBean.ok("添加成功");
    }
}
