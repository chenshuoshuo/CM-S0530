package com.lqkj.web.gnsc.modules.portal.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomImg;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomType;

import java.util.List;

/**
 * 房间信息实体对象
 * @version 1.0
 * @author RY
 */

public class MapRoomVO {
    /**
     * 房间编号
     */
    private Integer roomCode;
    /**
     * 房间类型编号
     */
    private Integer typeCode;
    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 房间英文名称
     */
    private String enName;
    /**
     * 房间别名
     */
    private String alias;
    /**
     * 房间门牌号
     */
    private String hourseNumber;
    /**
     * 房间校区编码
     */
    private Integer campusCode;
    /**
     * 房间地图编码
     */
    private Long mapCode;
    /**
     * 地图楼层编码
     */
    private Integer leaf;
    /**
     * 房间所属大楼编号
     */
    private Long buildingMapCode;
    /**
     * 房间所属大楼名称
     */
    private String buildingName;
    /**
     * 简介
     */
    private String brief;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 是否已同步
     */
    private Boolean synStatus;
    /**
     * 删除状态
     */
    private Boolean delete;
    /**
     * 房间排序
     */
    private Integer orderId;
    /**
     * 房间备注
     */
    private String memo;
    /**
     * 房间类型
     */
    private MapRoomType mapRoomType;
    /**
     * 房间图片列表
     */
    private List<MapRoomImg> mapRoomImgList;
    /**
     * 房间扩展属性列表
     */
    private List<MapRoomExtends> mapRoomExtendsList;
    /**
     * 空间信息json
     */
    private JSONObject geoJson;
    /**
     * 导入时的错误信息
     */
    private String errMsg;
    /**
     * 版本号
     */
    private Integer versionCode;
    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 字符串格式坐标
     */
    private String lngLatString;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Long getBuildingMapCode() {
        return buildingMapCode;
    }

    public void setBuildingMapCode(Long buildingMapCode) {
        this.buildingMapCode = buildingMapCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHourseNumber() {
        return hourseNumber;
    }

    public void setHourseNumber(String hourseNumber) {
        this.hourseNumber = hourseNumber;
    }

    public Boolean getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(Boolean synStatus) {
        this.synStatus = synStatus;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public MapRoomType getMapRoomType() {
        return mapRoomType;
    }

    public void setMapRoomType(MapRoomType mapRoomType) {
        this.mapRoomType = mapRoomType;
    }

    public List<MapRoomImg> getMapRoomImgList() {
        return mapRoomImgList;
    }

    public void setMapRoomImgList(List<MapRoomImg> mapRoomImgList) {
        this.mapRoomImgList = mapRoomImgList;
    }

    public List<MapRoomExtends> getMapRoomExtendsList() {
        return mapRoomExtendsList;
    }

    public void setMapRoomExtendsList(List<MapRoomExtends> mapRoomExtendsList) {
        this.mapRoomExtendsList = mapRoomExtendsList;
    }

    public JSONObject getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(JSONObject geoJson) {
        this.geoJson = geoJson;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }


    public String getLngLatString() {
        return lngLatString;
    }

    public void setLngLatString(String lngLatString) {
        this.lngLatString = lngLatString;
    }
}
