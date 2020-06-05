package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.*;
import com.lqkj.web.gnsc.modules.gns.domain.*;
import com.lqkj.web.gnsc.modules.handler.WebSocketPushHandler;
import com.lqkj.web.gnsc.modules.resultBean.PersonalAchieve;
import com.lqkj.web.gnsc.modules.resultBean.UserSignRankBean;
import com.lqkj.web.gnsc.utils.CommonUtils;
import com.lqkj.web.gnsc.utils.ServletUtils;
import com.lqkj.web.gnsc.utils.WeixinUtils;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GnsUserInfoService {

    @Autowired
    private GnsStoreItemService storeItemService;

    @Autowired
    private GnsAccessRecordDao accessRecordDao;

    @Autowired
    private GnsUserInfoDao userInfoDao;

    @Autowired
    private LocationInfoDao locationInfoDao;

    @Autowired
    private GnsAchievementReachDao achievementReachDao;

    @Autowired
    private GnsAchievementDao achievementDao;

    @Autowired
    private WebSocketPushHandler webSocketPushHandler;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取用户信息
     * @param request
     * @param schoolId
     * @param code
     * @return
     */
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

    public MessageBean<Object> updateUserInfo(String userId, String name, String mobile,
                                              String academyCode, String dormId) {
        GnsUserInfo userInfo = userInfoDao.findByUUID(userId);
        if (userInfo == null)
            return MessageBean.error("不存在该用户");
        if (CommonUtils.ifNotNull(name)) userInfo.setRealName(name);
        if (CommonUtils.ifNotNull(mobile)) userInfo.setMobile(mobile);
        if (CommonUtils.ifNotNull(academyCode)) userInfo.setAcademyCode(academyCode);
        if (CommonUtils.ifNotNull(dormId)) userInfo.setDormId(dormId);
        userInfoDao.save(userInfo);
        if(userInfo.getDormId() != null && userInfo.getAcademyCode() != null
                && userInfo.getRealName() != null && userInfo.getMobile() != null ){
            //身份信息完善后保存成就并推送
            GnsAchievementReach achievementReach = new GnsAchievementReach(userId,1,userInfo.getSchoolId());
            GnsAchievementReach reach = achievementReachDao.save(achievementReach);
            Integer personReachNumber = achievementReachDao.countAllByUserId(userId);
            Integer schoolReachNumber = achievementReachDao.countAllByAchievementIdAndSchoolId(1,userInfo.getSchoolId());
            GnsAchievement achievement = achievementDao.findByAchievementId(1);
            PersonalAchieve personalAchieve = new PersonalAchieve(achievement.getAchievementName(),achievement.getAchievedIcon(),achievement.getBrief(),achievement.getCondition(),
                    true,CommonUtils.formatTimeStamp(reach.getReachTime()),
                    personReachNumber == null ? 0 : personReachNumber,
                    schoolReachNumber == null ? 0 : schoolReachNumber);
            //推送
            webSocketPushHandler.pushMsg(userId, JSON.toJSONString(personalAchieve));
        }
        return MessageBean.ok("更新用户信息成功");
    }

    public MessageListBean<PersonalAchieve> getUserAchievement(String userId) {
        List<Object[]> list = userInfoDao.findAchieveByUserId(userId);
        ArrayList<PersonalAchieve> result = new ArrayList<>();
        for (Object[] object : list) {
            boolean gained = object[5] != null;
            PersonalAchieve achieve = new PersonalAchieve(object[0].toString(),
                    gained ? object[1].toString() : object[2].toString(),
                    object[3].toString(),
                    object[4].toString(),
                    gained,
                    gained ? CommonUtils.formatTimeStamp((Timestamp) object[5]) : "");
            result.add(achieve);
        }
        return MessageListBean.ok(result);
    }

    public MessageBean getUserSignRanking(String userId, boolean sameAcademy) {
        GnsUserInfo userInfo = userInfoDao.findByUUID(userId);
        if (userInfo == null)
            return MessageBean.error("不存在该用户");
        //总排名
        List<Object[]> list = userInfoDao.getRankOfUserSign(userInfo.getSchoolId()
                , sameAcademy ? userInfo.getAcademyCode() : "");

        UserSignRankBean bean = new UserSignRankBean();
        for (Object[] objects : list) {
            UserSignRankBean.SignRank signRank = new UserSignRankBean.SignRank(
                    (BigInteger) objects[0],
                    objects[2] == null ? "" : objects[2].toString(),
                    objects[3] == null ? "" : objects[3].toString(),
                    (Integer) objects[4]);
            if (objects[1].toString().equals(userId))
                bean.userRank = signRank;
            bean.signRankList.add(signRank);
        }
        return MessageBean.ok(bean);
    }

    public MessageBean addShareTimes(String userId) {
        GnsUserInfo userInfo = userInfoDao.findByUUID(userId);
        if (userInfo == null)
            return MessageBean.error("不存在该用户");
        if (userInfo.getShareTimes() == null)
            userInfo.setShareTimes(1);
        else userInfo.setShareTimes(userInfo.getShareTimes() + 1);
        userInfoDao.save(userInfo);
        //分享5次获得成就
        if(userInfo.getShareTimes() == 5){
            GnsAchievementReach achievementReach = new GnsAchievementReach(userId,4,userInfo.getSchoolId());
            GnsAchievementReach reach = achievementReachDao.save(achievementReach);
            Integer personReachNumber = achievementReachDao.countAllByUserId(userId);
            Integer schoolReachNumber = achievementReachDao.countAllByAchievementIdAndSchoolId(4,userInfo.getSchoolId());
            GnsAchievement achievement = achievementDao.findByAchievementId(4);
            PersonalAchieve personalAchieve = new PersonalAchieve(achievement.getAchievementName(),achievement.getAchievedIcon(),achievement.getBrief(),achievement.getCondition(),
                    true,CommonUtils.formatTimeStamp(reach.getReachTime()),
                    personReachNumber == null ? 0 : personReachNumber,
                    schoolReachNumber == null ? 0 : schoolReachNumber);
            //推送
            webSocketPushHandler.pushMsg(userId, JSON.toJSONString(personalAchieve));
        }
        return MessageBean.ok(userInfo.getShareTimes());
    }

    public MessageBean addListenTimes(String userId) {
        GnsUserInfo userInfo = userInfoDao.findByUUID(userId);
        if (userInfo == null)
            return MessageBean.error("不存在该用户");
        if (userInfo.getListenTimes() == null)
            userInfo.setListenTimes(1);
        else userInfo.setListenTimes(userInfo.getListenTimes() + 1);
        userInfoDao.save(userInfo);
        //播放5次获得成就
        if(userInfo.getListenTimes() == 5){
            GnsAchievementReach achievementReach = new GnsAchievementReach(userId,2,userInfo.getSchoolId());
            GnsAchievementReach reach = achievementReachDao.save(achievementReach);
            Integer personReachNumber = achievementReachDao.countAllByUserId(userId);
            Integer schoolReachNumber = achievementReachDao.countAllByAchievementIdAndSchoolId(2,userInfo.getSchoolId());
            GnsAchievement achievement = achievementDao.findByAchievementId(2);
            PersonalAchieve personalAchieve = new PersonalAchieve(achievement.getAchievementName(),achievement.getAchievedIcon(),achievement.getBrief(),achievement.getCondition(),
                    true,CommonUtils.formatTimeStamp(reach.getReachTime()),
                    personReachNumber == null ? 0 : personReachNumber,
                    schoolReachNumber == null ? 0 : schoolReachNumber);
            //推送
            webSocketPushHandler.pushMsg(userId, JSON.toJSONString(personalAchieve));
        }
        return MessageBean.ok(userInfo.getListenTimes());
    }

    /**
     * 上传用户位置
     */
    public Object loadUserLocation(String userCode,Double lng,Double lat){
        String sql = "select gns.load_user_location('" + userCode + "'," + lng + "," + lat +")" ;
        Object result = locationInfoDao.execSqlSingleResult(sql,null);
//        String result = userInfoDao.loadUserLocation(userCode,lng,lat);
        if(result != null){
            return JSONArray.parseArray(result.toString());
        }else {
            return new JSONArray();
        }
    }

    /**
     * 根据用户ID 获取
     */
    public JSONObject getUserById(String userCode){
        String result = userInfoDao.findByUserId(userCode);
        Integer userInfoPerfected  = userInfoDao.userInfoPerfected(userCode);
        if(StringUtils.isNotBlank(result)){
            JSONObject resultJSON = JSONObject.parseObject(result);

            if(userInfoPerfected == 0){
                resultJSON.put("informationPerfection","完善个人信息获得成就奖励哟~");
                resultJSON.put("informationCheck",false);
            }else if(userInfoPerfected == 1) {
                resultJSON.put("informationPerfection","个人信息已完成25%~");
                resultJSON.put("informationCheck",false);
            }else if(userInfoPerfected == 2){
                resultJSON.put("informationPerfection","个人信息已完成50%~");
                resultJSON.put("informationCheck",false);
            }else if(userInfoPerfected == 3){
                resultJSON.put("informationPerfection","个人信息已完成75%~");
                resultJSON.put("informationCheck",false);
            }else {
                resultJSON.put("informationPerfection","个人信息已完善~");
                resultJSON.put("informationCheck",true);
            }
            return resultJSON;
        }
        return new JSONObject();
    }
}
