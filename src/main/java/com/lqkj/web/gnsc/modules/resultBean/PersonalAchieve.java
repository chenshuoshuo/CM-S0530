package com.lqkj.web.gnsc.modules.resultBean;

import java.io.Serializable;

public class PersonalAchieve implements Serializable {
    private String achieveName;
    private String icon;
    private String brief;
    private String condition;
    private boolean gained;
    private String gainTime;
    private Integer personAchieveNumber;
    private Integer schoolAchieveNumber;

    public PersonalAchieve(String achieveName, String icon, String brief,
                           String condition, boolean gained, String gainTime) {
        this.achieveName = achieveName;
        this.icon = icon;
        this.brief = brief;
        this.condition = condition;
        this.gained = gained;
        this.gainTime = gainTime;
    }

    public PersonalAchieve(String achieveName, String icon, String brief, String condition, boolean gained, String gainTime, Integer personAchieveNumber, Integer schoolAchieveNumber) {
        this.achieveName = achieveName;
        this.icon = icon;
        this.brief = brief;
        this.condition = condition;
        this.gained = gained;
        this.gainTime = gainTime;
        this.personAchieveNumber = personAchieveNumber;
        this.schoolAchieveNumber = schoolAchieveNumber;
    }

    public String getAchieveName() {
        return achieveName;
    }

    public void setAchieveName(String achieveName) {
        this.achieveName = achieveName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public boolean isGained() {
        return gained;
    }

    public void setGained(boolean gained) {
        this.gained = gained;
    }

    public String getGainTime() {
        return gainTime;
    }

    public void setGainTime(String gainTime) {
        this.gainTime = gainTime;
    }

    public Integer getPersonAchieveNumber() {
        return personAchieveNumber;
    }

    public void setPersonAchieveNumber(Integer personAchieveNumber) {
        this.personAchieveNumber = personAchieveNumber;
    }

    public Integer getSchoolAchieveNumber() {
        return schoolAchieveNumber;
    }

    public void setSchoolAchieveNumber(Integer schoolAchieveNumber) {
        this.schoolAchieveNumber = schoolAchieveNumber;
    }
}
