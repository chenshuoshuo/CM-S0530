package com.lqkj.web.gnsc.modules.portal.model.vo;



import com.lqkj.web.gnsc.modules.portal.model.MapPointExtends;
import com.lqkj.web.gnsc.modules.portal.model.MapPointImg;
import com.lqkj.web.gnsc.modules.portal.model.MapPointType;

import java.util.List;

/**
 * 点标注实体对象
 * @author RY
 * @version 1.0
 * @since 2018-12-18 14:16:21
 */

public class MapPointVO {
    /**
     * 点标注编号
     */
    private Integer pointCode;
    /**
     * 点标注分类编号
     */
    private Integer typeCode;
    /**
     * 点标注名称
     */
    private String pointName;
    /**
     * 校区编码
     */
    private Integer campusCode;
    /**
     * 楼层，null为室外
     */
    private Integer leaf;
    /**
     * 绑定位置
     */
    private String location;
    /**
     * 字符串格式坐标
     */
    private String lngLatString;
    /**
     * 字符串格式三维坐标
     */
    private String rasterLngLatString;
    /**
     * 简介
     */
    private String brief;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 排序
     */
    private Integer orderId;
    /**
     * 备注
     */
    private String memo;
    /**
     * 点标注类别
     */
    private MapPointType mapPointType;
    /**
     * 点标注父类别
     */
    private MapPointType parentMapPointType;
    /**
     * 点标注图片列表
     */
    private List<MapPointImg> mapPointImgList;
    /**
     * 点标注扩展属性列表
     */
    private List<MapPointExtends> mapPointExtendsList;
    /**
     * 导入时的错误信息
     */
    private String errMsg;
    /**
     * 地图编码
     * 这里指在CMGIS中的编号
     */
    private Long mapCode;
    /**
     * 版本号
     */
    private Integer versionCode;
    /**
     * 是否已同步
     */
    private Boolean synStatus;
    /**
     * 删除状态
     */
    private Boolean delete;
    /**
     * 图片地址
     */
    private String picUrl;
    /**
     * 大楼名称
     */
    private String buildingName;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(Integer campusCode) {
        this.campusCode = campusCode;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public MapPointType getMapPointType() {
        return mapPointType;
    }

    public void setMapPointType(MapPointType mapPointType) {
        this.mapPointType = mapPointType;
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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public MapPointType getParentMapPointType() {
        return parentMapPointType;
    }

    public void setParentMapPointType(MapPointType parentMapPointType) {
        this.parentMapPointType = parentMapPointType;
    }

    public List<MapPointImg> getMapPointImgList() {
        return mapPointImgList;
    }

    public void setMapPointImgList(List<MapPointImg> mapPointImgList) {
        this.mapPointImgList = mapPointImgList;
    }

    public List<MapPointExtends> getMapPointExtendsList() {
        return mapPointExtendsList;
    }

    public void setMapPointExtendsList(List<MapPointExtends> mapPointExtendsList) {
        this.mapPointExtendsList = mapPointExtendsList;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Long getMapCode() {
        return mapCode;
    }

    public void setMapCode(Long mapCode) {
        this.mapCode = mapCode;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
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
}
