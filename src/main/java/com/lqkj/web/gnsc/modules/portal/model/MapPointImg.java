package com.lqkj.web.gnsc.modules.portal.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/6/1 17:48
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "map_point_img", schema = "portal")
public class MapPointImg {
    private Integer imgId;
    private Integer pointCode;
    private String imgName;
    private String imgUrl;
    private Integer orderId;
    private String memo;

    public MapPointImg() {
    }

    public MapPointImg(Integer pointCode, String imgUrl) {
        this.pointCode = pointCode;
        this.imgUrl = imgUrl;
    }

    @Id
    @Column(name = "img_id", nullable = false)
    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    @Basic
    @Column(name = "point_code", nullable = true)
    public Integer getPointCode() {
        return pointCode;
    }

    public void setPointCode(Integer pointCode) {
        this.pointCode = pointCode;
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
        MapPointImg that = (MapPointImg) o;
        return imgId == that.imgId &&
                Objects.equals(pointCode, that.pointCode) &&
                Objects.equals(imgName, that.imgName) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgId, pointCode, imgName, imgUrl, orderId, memo);
    }
}
