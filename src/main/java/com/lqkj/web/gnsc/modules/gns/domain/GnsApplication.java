package com.lqkj.web.gnsc.modules.gns.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_application", schema = "gns")
public class GnsApplication {
    private Integer applicationId;
    private Integer parentId;
    private Integer schoolId;
    private String applicationName;
    private String enName;
    private Boolean applicationOpen;
    private String logo;
    private String qrCode;
    private String openUrl;
    private Boolean preset;
    private Timestamp updateTime;
    private Integer orderId;
    private String memo;

    public GnsApplication() {
    }

    public GnsApplication(Integer applicationId,Integer parentId, Integer schoolId, String applicationName, String enName, Boolean applicationOpen,
                          String logo, String qrCode, String openUrl, Boolean preset, Integer orderId, String memo) {
        this.applicationId = applicationId;
        this.parentId = parentId;
        this.schoolId = schoolId;
        this.applicationName = applicationName;
        this.enName = enName;
        this.applicationOpen = applicationOpen;
        this.logo = logo;
        this.qrCode = qrCode;
        this.openUrl = openUrl;
        this.preset = preset;
        this.orderId = orderId;
        this.memo = memo;
    }

    @Id
    @Column(name = "application_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Basic
    @Column(name = "parent_id", nullable = true)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "school_id", nullable = true)
    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "application_name", nullable = true, length = 50)
    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Basic
    @Column(name = "en_name", nullable = true, length = 50)
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Basic
    @Column(name = "application_open", nullable = true)
    public Boolean getApplicationOpen() {
        return applicationOpen;
    }

    public void setApplicationOpen(Boolean applicationOpen) {
        this.applicationOpen = applicationOpen;
    }

    @Basic
    @Column(name = "logo", nullable = true, length = 1024)
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "qr_code", nullable = true, length = 1024)
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    @Basic
    @Column(name = "open_url", nullable = true, length = 1024)
    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    @Basic
    @Column(name = "preset", nullable = true)
    public Boolean getPreset() {
        return preset;
    }

    public void setPreset(Boolean preset) {
        this.preset = preset;
    }

    @Basic
    @Column(name = "update_time", nullable = true, length = -1)
    @UpdateTimestamp
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
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
        GnsApplication that = (GnsApplication) o;
        return applicationId == that.applicationId &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(applicationName, that.applicationName) &&
                Objects.equals(enName, that.enName) &&
                Objects.equals(applicationOpen, that.applicationOpen) &&
                Objects.equals(logo, that.logo) &&
                Objects.equals(qrCode, that.qrCode) &&
                Objects.equals(openUrl, that.openUrl) &&
                Objects.equals(preset, that.preset) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationId, schoolId, applicationName, enName, applicationOpen, logo, qrCode, openUrl, preset, updateTime, orderId, memo);
    }
}
