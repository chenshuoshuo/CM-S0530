package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:47
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_building_img", schema = "portal")
public class MapBuildingImg {
    private Integer imgId;
    private Integer buildingCode;
    private String imgName;
    private String imgUrl;
    private Integer orderId;
    private String memo;

    public MapBuildingImg() {}

    public MapBuildingImg(Integer buildingCode, String imgUrl) {
        this.buildingCode = buildingCode;
        this.imgUrl = imgUrl;
    }

    @Id
    @Column(name = "img_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    @Basic
    @Column(name = "building_code", nullable = true)
    public Integer getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(Integer buildingCode) {
        this.buildingCode = buildingCode;
    }

    @Basic
    @Column(name = "img_name", nullable = true, length = 64)
    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Basic
    @Column(name = "img_url", nullable = true, length = 1024)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "order_id", nullable = true)
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "memo", nullable = true, length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapBuildingImg that = (MapBuildingImg) o;
        return imgId == that.imgId &&
                Objects.equals(buildingCode, that.buildingCode) &&
                Objects.equals(imgName, that.imgName) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgId, buildingCode, imgName, imgUrl, orderId, memo);
    }
}
