package com.lqkj.web.gnsc.modules.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * 用户
 */
//@Cacheable
@ApiModel(value = "用户")
@Entity
@Table(name = "ccr_user", indexes = {
        @Index(name = "user_code_index", unique = true, columnList = "user_code"),
        @Index(name = "open_id_index", unique = true, columnList = "open_id"),
        @Index(name = "cas_ticket_index", unique = true, columnList = "cas_ticket")
})
public class User implements Serializable, UserDetails {

    @ApiModelProperty(value = "账号id")
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "用户Code不能为空")
    @ApiModelProperty(value = "用户Code")
    @Column(name = "user_code")
    private String userCode;

    @ApiModelProperty(value = "账号名")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty(value = "密码")
    @Column(name = "pass_word")
    private String passWord;

    @ApiModelProperty(value = "oauth2.0登录id")
    @Column(name = "open_id")
    private String openId;

    @ApiModelProperty(value = "cas登录凭证")
    @Column(name = "cas_ticket")
    private String casTicket;

    @ApiModelProperty(value = "用户权限")
    @Column(name = "user_auth")
    @Enumerated(EnumType.STRING)
    private UserAuthority userAuthority;

    @ApiModelProperty(value = "用户群体")
    @Column(name = "user_group")
    @Enumerated(EnumType.STRING)
    private CcrUserGroupType userGroup;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @ApiModelProperty("是否允许登录后台")
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @ApiModelProperty("头像保存路径")
    @Column(name = "head_path")
    private String headPath;

    @ApiModelProperty("头像保存路径")
    private String headUrl;

    public CcrUserGroupType getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(CcrUserGroupType userGroup) {
        this.userGroup = userGroup;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCasTicket() {
        return casTicket;
    }

    public void setCasTicket(String casTicket) {
        this.casTicket = casTicket;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserAuthority getUserAuthority() {
        return userAuthority;
    }

    public void setUserAuthority(UserAuthority userAuthority) {
        this.userAuthority = userAuthority;
    }

    @Override
    public Collection<UserAuthority> getAuthorities() {
        Set<UserAuthority> authorities = new HashSet<>();
        authorities.add(this.getUserAuthority());
        return authorities;
    }

    @Override
    public String getPassword() {
        if (passWord!=null) {
            return passWord;
        } else {
            return casTicket;
        }
    }

    @Override
    public String getUsername() {
        return userCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum CcrUserGroupType {
        student, teacher, staff, guest, teacher_staff
    }

    public enum UserAuthority implements Serializable, GrantedAuthority{
        admin, superAdmin;
        @Override
        public String getAuthority() {
            return this.name();
        }
    }
}
