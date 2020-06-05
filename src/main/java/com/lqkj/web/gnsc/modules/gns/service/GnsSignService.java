package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.fastjson.JSON;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsAchievementDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsAchievementReachDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsSignDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAchievement;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAchievementReach;
import com.lqkj.web.gnsc.modules.gns.domain.GnsSign;
import com.lqkj.web.gnsc.modules.handler.WebSocketPushHandler;
import com.lqkj.web.gnsc.modules.resultBean.PersonalAchieve;
import com.lqkj.web.gnsc.modules.resultBean.RankingBean;
import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapPointDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapRoomDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBuilding;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygon;
import com.lqkj.web.gnsc.modules.portal.model.MapPoint;
import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import com.lqkj.web.gnsc.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    private MapPointDao pointDao;
    @Autowired
    private MapBuildingDao buildingDao;
    @Autowired
    private MapRoomDao roomDao;
    @Autowired
    private MapOthersPolygonDao othersPolygonDao;
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;
    @Autowired
    private GnsAchievementReachDao achievementReachDao;

    @Autowired
    private GnsAchievementDao achievementDao;

    /**
     * 获取弹幕
     *
     * @param schoolId
     * @return
     */
    public List<Map<String,Object>> queryList(Integer schoolId){

        return signDao.queryList(schoolId);
    }

    /**
     * 打卡
     */
    public MessageBean save(String userCode, Integer mapCode, String mapType,Integer schoolId){
        String landMarkName = null;
        Integer signCount = null;
        Integer signInterval = 1;
        //判断是否已经打卡
        Boolean checkSignList = signDao.existsByUserCodeAndMapCode(userCode,mapCode);
        if(checkSignList){
            if("point".equals(mapType) && pointDao.existsByPointCode(mapCode)){
                MapPoint point = pointDao.queryByPointCode(mapCode);
                landMarkName = point.getPointName();
                point.setGnsSignCount(point.getGnsSignCount() + 1);
                signCount = point.getGnsSignCount();
                signInterval = point.getGnsSignInterval() * 60;
                pointDao.save(point);
            }else {
                //获取房间 大楼 其他面信息
                if(buildingDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    MapBuilding building = buildingDao.queryByMapCode(Long.parseLong(mapCode.toString()));
                    landMarkName = building.getBuildingName();
                    building.setGnsSignCount(building.getGnsSignCount() + 1);
                    signCount = building.getGnsSignCount();
                    signInterval = building.getGnsSignInterval() * 60;
                    buildingDao.save(building);
                }else if(roomDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    MapRoom room = roomDao.queryByMapCode(Long.parseLong(mapCode.toString()));
                    landMarkName = room.getRoomName();
                    room.setGnsSignCount(room.getGnsSignCount() + 1);
                    signCount = room.getGnsSignCount();
                    signInterval = room.getGnsSignInterval();
                    roomDao.save(room);
                }else if(othersPolygonDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                    MapOthersPolygon othersPolygon = othersPolygonDao.queryByMapCode(Long.parseLong(mapCode.toString()));
                    landMarkName = othersPolygon.getPolygonName();
                    othersPolygon.setGnsSignCount(othersPolygon.getGnsSignCount() + 1);
                    signCount = othersPolygon.getGnsSignCount();
                    signInterval = othersPolygon.getGnsSignInterval() * 60;
                    othersPolygonDao.save(othersPolygon);
                }
            }
            //保存打卡记录
            GnsSign sign = new GnsSign(userCode,Long.parseLong(mapCode.toString()),landMarkName,mapType);
            signDao.save(sign);
            //当前用户打卡地标超过5则获得成就
            Integer userSignCount = signDao.countAllByUserId(userCode);
            if(userSignCount == 5){
                GnsAchievementReach achievementReach = new GnsAchievementReach(userCode,3,schoolId);
                GnsAchievementReach reach = achievementReachDao.save(achievementReach);
                Integer personReachNumber = achievementReachDao.countAllByUserId(userCode);
                Integer schoolReachNumber = achievementReachDao.countAllByAchievementIdAndSchoolId(3,schoolId);
                GnsAchievement achievement = achievementDao.findByAchievementId(3);
                PersonalAchieve personalAchieve = new PersonalAchieve(achievement.getAchievementName(),achievement.getAchievedIcon(),achievement.getBrief(),achievement.getCondition(),
                        true, CommonUtils.formatTimeStamp(reach.getReachTime()),
                        personReachNumber == null ? 0 : personReachNumber,
                        schoolReachNumber == null ? 0 : schoolReachNumber);
                //推送
                webSocketPushHandler.pushMsg(userCode, JSON.toJSONString(personalAchieve));
            }
            webSocketPushHandler.pushAllMsg(JSON.toJSONString(this.queryList(schoolId)));
            return MessageBean.ok(signCount);
        }else {
            return MessageBean.error("提示：您已打卡，过" + signInterval + "分钟可以再次打卡哦！");
        }
    }

    public MessageListBean getSignRanking(Integer campusCode) {
        List<Object[]> list = signDao.getSignRanking(campusCode);
        ArrayList<RankingBean> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            RankingBean bean = new RankingBean(i, list.get(i)[0].toString(), (Integer) list.get(i)[1]);
            result.add(bean);
        }
        return MessageListBean.ok(result);
    }

    public Page<GnsSign> getUserSigns(String userId, int page, int pageSize) {
        return signDao.getUserSigns(userId, PageRequest.of(page, pageSize));
    }
}
