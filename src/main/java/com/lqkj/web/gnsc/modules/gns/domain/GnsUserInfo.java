package com.lqkj.web.gnsc.modules.gns.domain;

import com.vividsolutions.jts.geom.Geometry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@ApiModel(value = "迎新用户信息")
@Entity
@Table(name = "gns_user_info", schema = "gns")
public class GnsUserInfo {
    @Id
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID userId;

    @Column(name = "school_id")
    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;

    @Column(name = "open_id")
    @ApiModelProperty(value = "微信openId")
    private String openid;

    @Column(name = "nickname")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Column(name = "province")
    @ApiModelProperty(value = "省份")
    private String province;

    @Column(name = "city")
    @ApiModelProperty(value = "城市")
    private String city;

    @Column(name = "country")
    @ApiModelProperty(value = "学校ID")
    private String country;

    @Column(name = "head_url")
    @ApiModelProperty(value = "头像地址")
    private String headUrl;

    @Column(name = "union_id")
    @ApiModelProperty(value = "微信unionid")
    private String unionId;

    @Column(name = "dorm_id")
    @ApiModelProperty(value = "宿舍ID")
    private String dormId;

    @Column(name = "mobile")
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @Column(name = "academy_code")
    @ApiModelProperty(value = "院系ID")
    private String academyCode;

    @Column(name = "real_name")
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @Column(name = "sign_count")
    @ApiModelProperty(value = "打卡次数")
    private Integer signCount;

    @Column(name = "last_use_time")
    @ApiModelProperty(value = "最后使用时间")
    private Timestamp lastUseTime;

    @Column(name = "last_use_location")
    @ApiModelProperty(value = "最后使用位置")
    private Geometry lastUseLocation;

    @Column(name = "share_times")
    @ApiModelProperty(value = "分享次数")
    private Integer shareTimes;


    public GnsUserInfo() {
        this.userId = UUID.randomUUID();
    }

    public GnsUserInfo(Integer schoolId, String openid, String nickname, String sex, String province, String city, String country, String headUrl, String unionId) {
        this.userId = UUID.randomUUID();
        this.schoolId = schoolId;
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headUrl = headUrl;
        this.unionId = unionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getDormId() {
        return dormId;
    }

    public void setDormId(String dormId) {
        this.dormId = dormId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAcademyCode() {
        return academyCode;
    }

    public void setAcademyCode(String academyCode) {
        this.academyCode = academyCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Timestamp getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Timestamp lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    public Geometry getLastUseLocation() {
        return lastUseLocation;
    }

    public void setLastUseLocation(Geometry lastUseLocation) {
        this.lastUseLocation = lastUseLocation;
    }

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }
}
