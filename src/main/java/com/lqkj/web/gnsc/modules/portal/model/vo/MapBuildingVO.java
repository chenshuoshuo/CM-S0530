package com.lqkj.web.gnsc.modules.portal.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingImg;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingType;

import java.util.List;

/**
 * 大楼信息实体对象
 * @version 1.0
 * @author RY
 */

public class MapBuildingVO {
    /**
     * 大楼编号
     */
    private Integer buildingCode;
    /**
     * 分类编号
     */
    private Integer typeCode;
    /**
     * 大楼名称
     */
    private String buildingName;
    /**
     * 校区编号
     */
    private Integer campusCode;
    /**
     * 大楼地图编号
     */
    private Long mapCode;
    /**
     * 大楼英文名称
     */
    private String enName;
    /**
     * 大楼别名
     */
    private String alias;
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
     * 是否已删除
     */
    private Boolean delete;
    /**
     * 大楼排序
     */
    private Integer orderId;
    /**
     * 大楼备注
     */
    private String memo;
    /**
     * 分类
     */
    private MapBuildingType mapBuildingType;
    /**
     * 图片列表
     */
    private List<MapBuildingImg> mapBuildingImgList;
    /**
     * 扩展属性列表
     */
    private List<MapBuildingExtends> mapBuildingExtendsList;
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
     * 二维经纬度
     **/
    private String lngLatString;

    /**
     * 三维经纬度
     **/
    private String rasterLngLatString;

    public MapBuildingVO() {
    }

    public MapBuildingVO(String buildingName, Long mapCode, Integer orderId) {
        this.buildingName = buildingName;
        this.mapCode = mapCode;
        this.orderId = orderId;
    }

    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public MapBuildingType getMapBuildingType() {
        return mapBuildingType;
    }

    public void setMapBuildingType(MapBuildingType mapBuildingType) {
        this.mapBuildingType = mapBuildingType;
    }

    public List<MapBuildingImg> getMapBuildingImgList() {
        return mapBuildingImgList;
    }

    public void setMapBuildingImgList(List<MapBuildingImg> mapBuildingImgList) {
        this.mapBuildingImgList = mapBuildingImgList;
    }

    public List<MapBuildingExtends> getMapBuildingExtendsList() {
        return mapBuildingExtendsList;
    }

    public void setMapBuildingExtendsList(List<MapBuildingExtends> mapBuildingExtendsList) {
        this.mapBuildingExtendsList = mapBuildingExtendsList;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLngLatString() {
        return lngLatString;
    }

    public void setLngLatString(String lngLatString) {
        this.lngLatString = lngLatString;
    }

    public String getRasterLngLatString() {
        return rasterLngLatString;
    }

    public void setRasterLngLatString(String rasterLngLatString) {
        this.rasterLngLatString = rasterLngLatString;
    }
}
