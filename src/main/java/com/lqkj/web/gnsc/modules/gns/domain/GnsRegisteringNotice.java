package com.lqkj.web.gnsc.modules.gns.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:51
 * @Version 2.2.2.0
 **/
@Entity
@Table(name = "gns_registering_notice", schema = "gns")
public class GnsRegisteringNotice extends BaseRowModel {
    private Integer noticeId;
    @ExcelProperty(value = {"学校编号"}, index = 0)
    private Integer schoolId;
    @ExcelProperty(value = {"报道须知名称"}, index = 1)
    private String title;
    @ExcelProperty(value = {"报道点内容"}, index = 2)
    private String content;
    private Timestamp updateTime;
    @ExcelProperty(value = {"排序"}, index = 3)
    private Integer orderId;
    @ExcelProperty(value = {"备注"}, index = 4)
    private String memo;

    @Id
    @Column(name = "notice_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
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
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        GnsRegisteringNotice that = (GnsRegisteringNotice) o;
        return noticeId == that.noticeId &&
                Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noticeId, schoolId, title, content, updateTime, orderId, memo);
    }
}
