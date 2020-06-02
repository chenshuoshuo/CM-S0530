package com.lqkj.web.gnsc.modules.gns.domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_interaction_statistic", schema = "gns")
public class GnsInteractionStatistic {
    private Integer statisticId;
    private Integer schoolId;
    private String statisticName;
    private Integer statisticData;

    @Id
    @Column(name = "statistic_id", nullable = false)
    public Integer getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(Integer statisticId) {
        this.statisticId = statisticId;
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
    @Column(name = "statistic_name", nullable = true, length = 50)
    public String getStatisticName() {
        return statisticName;
    }

    public void setStatisticName(String statisticName) {
        this.statisticName = statisticName;
    }

    @Basic
    @Column(name = "statistic_data", nullable = true)
    public Integer getStatisticData() {
        return statisticData;
    }

    public void setStatisticData(Integer statisticData) {
        this.statisticData = statisticData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GnsInteractionStatistic that = (GnsInteractionStatistic) o;
        return statisticId == that.statisticId &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(statisticName, that.statisticName) &&
                Objects.equals(statisticData, that.statisticData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statisticId, schoolId, statisticName, statisticData);
    }
}
