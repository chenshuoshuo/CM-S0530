package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsAccessRecordDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsUserInfoDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAccessRecord;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import com.lqkj.web.gnsc.utils.ServletUtils;
import com.lqkj.web.gnsc.utils.WeixinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class GnsUserInfoService {

    @Autowired
    private GnsStoreItemService storeItemService;

    @Autowired
    private GnsAccessRecordDao accessRecordDao;

    @Autowired
    private GnsUserInfoDao userInfoDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageBean<Object> getUserInfo(HttpServletRequest request, Integer schoolId, String code) {

        //获取成功后保存用户信息到微信用户表sa_wx_user,并且增加一条系统访问记录，返回排序ID
        JSONObject userInfo = null;
        GnsStoreItem appIdStore = null;
        GnsStoreItem secretStore = null;
        try {
            appIdStore = storeItemService.getByNameAndKey("otherConfigurations", "weChatAppId",schoolId);
            secretStore = storeItemService.getByNameAndKey("otherConfigurations", "weChatAppSecret",schoolId);
            userInfo = WeixinUtils.getUserInfo(appIdStore.getItemValue(), secretStore.getItemValue(), code);
            if(userInfo.containsKey("errcode")){
                return MessageBean.error("获取用户失败");
            }
            logger.info("=========weixinUserInfo:"+JSONObject.toJSONString(userInfo));
            //判断当前学校用户是否存在
            GnsUserInfo gnsUserInfo = userInfoDao.findBySchoolIdAndOpenid(schoolId,userInfo.getString("openid"));
            if(gnsUserInfo == null){
                gnsUserInfo = new GnsUserInfo(schoolId,userInfo.getString("openid"),userInfo.getString("nickname"),userInfo.getString("sex"),
                        userInfo.getString("province"),userInfo.getString("city"),userInfo.getString("country"),userInfo.getString("headimgurl"),userInfo.getString("unionid"));
                userInfoDao.save(gnsUserInfo);
            }
            //添加访问记录
            String ip = ServletUtils.getIpAddress(request);
            SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
            String createDate = simpleDateFormatDate.format( new Date());
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            String hour = String.valueOf(new Date().getHours());

            GnsAccessRecord accessRecord = new GnsAccessRecord(gnsUserInfo.getUserId(),ip,createDate,String.valueOf(month),hour);
            accessRecordDao.save(accessRecord);

            return MessageBean.ok(gnsUserInfo.getUserId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return MessageBean.error(e.getMessage());
        }
    }
}
