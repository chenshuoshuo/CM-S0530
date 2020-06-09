package com.lqkj.web.gnsc.modules.portal.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonImg;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonType;

import java.util.List;

/**
 * 其他面图源实体对象
 * @version 1.0
 * @author RY
 */

public class MapOthersPolygonVO {
    /**
     * 面图源编号
     */
    private Integer polygonCode;
    /**
     * 面图源分类编号
     */
    private Integer typeCode;
    /**
     * 面图源名称
     */
    private String polygonName;
    /**
     * 面图源校区编号
     */
    private Integer campusCode;
    /**
     * 面图源地图编号
     */
    private Long mapCode;
    /**
     * 地图楼层编码
     */
    private Integer leaf;
    /**
     * 面图源英文名称
     */
    private String enName;
    /**
     * 面图源别名
     */
    private String alias;
    /**
     * 简介
     */
    private String brief;
    /**
     * 是否已同步
     */
    private Boolean synStatus;
    /**
     * 删除状态
     */
    private Boolean delete;
    /**
     * 面图源排序
     */
    private Integer orderId;
    /**
     * 面图源备注
     */
    private String memo;
    /**
     * 面图源分类
     */
    private MapOthersPolygonType mapOthersPolygonType;
    /**
     * 图片列表
     */
    private List<MapOthersPolygonImg> mapOthersPolygonImgList;
    /**
     * 扩展属性列表
     */
    private List<MapOthersPolygonExtends> mapOthersPolygonExtendsList;
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

    public Integer getPolygonCode() {
        return polygonCode;
    }

    public void setPolygonCode(Integer polygonCode) {
        this.polygonCode = polygonCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getPolygonName() {
        return polygonName;
    }

    public void setPolygonName(String polygonName) {
        this.polygonName = polygonName;
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

    public MapOthersPolygonType getMapOthersPolygonType() {
        return mapOthersPolygonType;
    }

    public void setMapOthersPolygonType(MapOthersPolygonType mapOthersPolygonType) {
        this.mapOthersPolygonType = mapOthersPolygonType;
    }

    public List<MapOthersPolygonImg> getMapOthersPolygonImgList() {
        return mapOthersPolygonImgList;
    }

    public void setMapOthersPolygonImgList(List<MapOthersPolygonImg> mapOthersPolygonImgList) {
        this.mapOthersPolygonImgList = mapOthersPolygonImgList;
    }

    public List<MapOthersPolygonExtends> getMapOthersPolygonExtendsList() {
        return mapOthersPolygonExtendsList;
    }

    public void setMapOthersPolygonExtendsList(List<MapOthersPolygonExtends> mapOthersPolygonExtendsList) {
        this.mapOthersPolygonExtendsList = mapOthersPolygonExtendsList;
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
}
