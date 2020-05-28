package com.lqkj.web.gnsc.modules.manager.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 用户角色
 */
//@Cacheable
@Entity
@Table(name = "gns_manage_role",schema = "gns")
public class GnsManageRole implements Serializable {

    @Id
    @Column(name = "rule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gns_manage_role_resource",schema = "gns",
            joinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    )
    private Set<GnsManageResource> resources;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column
    private String name;

    @Column
    private String content;

    @Column(name = "school_id")
    private Integer schoolId;

    public GnsManageRole() {
    }

    public GnsManageRole(Set<GnsManageResource> resources, String name, String content) {
        this.resources = resources;
        this.name = name;
        this.content = content;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Set<GnsManageResource> getResources() {
        return resources;
    }

    public void setResources(Set<GnsManageResource> resources) {
        this.resources = resources;
    }
}
