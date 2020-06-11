package com.lqkj.web.gnsc.modules.gns.domain.vo;

import java.util.List;


/**
 * 迎新使用统计对象
 * @version 1.0
 * @author RY
 * @since 2018-7-3 10:32:49
 */

public class GnsUseStatisticVO {
    /**
     * 综合统计（使用次数、使用人数、打卡次数、留影次数、分享次数）
     */
    private Object intergrateStatistic;
    /**
     * 使用城市统计
     */
    private List<CityUseStatisticVO> cityUseList;
    /**
     * 热门地标统计
     */
    private List<KeyValueVO> hotPointStatisticList;
    /**
     * 日期使用对比(PV统计)
     */
    private List<KeyValueVO> dayUseCountByPvList;
    /**
     * 日期使用对比(IP统计)
     */
    private List<KeyValueVO> dayUseCountByIpList;
    /**
     * 功能访问量排行
     */
    private List<Object> appUseStatisticList;
    /**
     * 活服务点击排行
     */
    private List<Object> pointStatisticList;

    /**
     * 互动功能排行
     */
    private List<Object> interactionStatisticList;

    /**
     * 成就功能数据排序
     */
    private List<Object> achievementStatisticList;

    public Object getIntergrateStatistic() {
        return intergrateStatistic;
    }

    public void setIntergrateStatistic(Object intergrateStatistic) {
        this.intergrateStatistic = intergrateStatistic;
    }

    public List<CityUseStatisticVO> getCityUseList() {
        return cityUseList;
    }

    public void setCityUseList(List<CityUseStatisticVO> cityUseList) {
        this.cityUseList = cityUseList;
    }

    public List<KeyValueVO> getHotPointStatisticList() {
        return hotPointStatisticList;
    }

    public void setHotPointStatisticList(List<KeyValueVO> hotPointStatisticList) {
        this.hotPointStatisticList = hotPointStatisticList;
    }

    public List<KeyValueVO> getDayUseCountByPvList() {
        return dayUseCountByPvList;
    }

    public void setDayUseCountByPvList(List<KeyValueVO> dayUseCountByPvList) {
        this.dayUseCountByPvList = dayUseCountByPvList;
    }

    public List<KeyValueVO> getDayUseCountByIpList() {
        return dayUseCountByIpList;
    }

    public void setDayUseCountByIpList(List<KeyValueVO> dayUseCountByIpList) {
        this.dayUseCountByIpList = dayUseCountByIpList;
    }

    public List<Object> getAppUseStatisticList() {
        return appUseStatisticList;
    }

    public void setAppUseStatisticList(List<Object> appUseStatisticList) {
        this.appUseStatisticList = appUseStatisticList;
    }

    public List<Object> getPointStatisticList() {
        return pointStatisticList;
    }

    public void setPointStatisticList(List<Object> pointStatisticList) {
        this.pointStatisticList = pointStatisticList;
    }

    public List<Object> getInteractionStatisticList() {
        return interactionStatisticList;
    }

    public void setInteractionStatisticList(List<Object> interactionStatisticList) {
        this.interactionStatisticList = interactionStatisticList;
    }

    public List<Object> getAchievementStatisticList() {
        return achievementStatisticList;
    }

    public void setAchievementStatisticList(List<Object> achievementStatisticList) {
        this.achievementStatisticList = achievementStatisticList;
    }
}
