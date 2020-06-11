package com.lqkj.web.gnsc.modules.gns.domain.vo;

/**
 * 应用使用统计对象
 * @version 1.0
 * @author RY
 * @since 2018-7-3 10:22:45
 */

public class ModuleUseStatisticVO {
    /**
     * 排行
     */
    private Integer orderId;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 访问次数
     */
    private Integer useCount;
    /**
     * 访问人数
     */
    private Integer userCount;
    /**
     * 访问人数
     */
    private Integer countAll;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }
}
