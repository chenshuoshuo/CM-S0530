package com.lqkj.web.gnsc.modules.gns.domain.vo;

import java.util.List;

/**
 * 地图使用统计实体
 * @version 1.0
 * @author RY
 * @since 2018-6-21 18:15:37
 */
public class MapUseStatisticVO {
    /**
     * 搜索统计
     */
    private List<KeyValueVO> searchStatisticList;
    /**
     * 点击统计
     */
    private List<KeyValueVO> clickStatisticList;
    /**
     * 导航起点
     */
    private List<KeyValueVO> navicationStartStatisticList;
    /**
     * 导航终点
     */
    private List<KeyValueVO> navigationEndStatisticList;
    /**
     * 生活服务分类
     */
    private List<KeyValueVO> pointCategoryStatisticList;

    public List<KeyValueVO> getSearchStatisticList() {
        return searchStatisticList;
    }

    public void setSearchStatisticList(List<KeyValueVO> searchStatisticList) {
        this.searchStatisticList = searchStatisticList;
    }

    public List<KeyValueVO> getClickStatisticList() {
        return clickStatisticList;
    }

    public void setClickStatisticList(List<KeyValueVO> clickStatisticList) {
        this.clickStatisticList = clickStatisticList;
    }

    public List<KeyValueVO> getNavicationStartStatisticList() {
        return navicationStartStatisticList;
    }

    public void setNavicationStartStatisticList(List<KeyValueVO> navicationStartStatisticList) {
        this.navicationStartStatisticList = navicationStartStatisticList;
    }

    public List<KeyValueVO> getNavigationEndStatisticList() {
        return navigationEndStatisticList;
    }

    public void setNavigationEndStatisticList(List<KeyValueVO> navigationEndStatisticList) {
        this.navigationEndStatisticList = navigationEndStatisticList;
    }

    public List<KeyValueVO> getPointCategoryStatisticList() {
        return pointCategoryStatisticList;
    }

    public void setPointCategoryStatisticList(List<KeyValueVO> pointCategoryStatisticList) {
        this.pointCategoryStatisticList = pointCategoryStatisticList;
    }
}
