package com.lqkj.web.gnsc.modules.gns.domain.vo;

import org.apache.commons.lang.StringUtils;

/**
 * @author cs
 * 路径规划点对象
 * @Date 2020/6/4 9:38
 * @Version 2.2.2.0
 **/
public class PointVO {
    private String[] lngLat;
    private String priorityType;
    private String roadType ;
    private Integer wayId;

    public String[] getLngLat() {
        return lngLat;
    }

    public void setLngLat(String[] lngLat) {
        this.lngLat = lngLat;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        if(StringUtils.isNotBlank(priorityType) || StringUtils.isEmpty(priorityType)){
            this.priorityType = "elevator";
        }
        this.priorityType = priorityType;

    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        if(StringUtils.isNotBlank(roadType) || StringUtils.isEmpty(roadType)){
            this.roadType = "foot";
        }
        this.roadType = roadType;
    }

    public Integer getWayId() {
        return wayId;
    }

    public void setWayId(Integer wayId) {
        if(wayId == null){
            this.wayId = 0;
        }
        this.wayId = wayId;
    }
}
