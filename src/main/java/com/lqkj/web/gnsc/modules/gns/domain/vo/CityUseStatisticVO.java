package com.lqkj.web.gnsc.modules.gns.domain.vo;

/**
 * 应用使用统计对象
 * @version 1.0
 * @author RY
 * @since 2018-7-3 10:22:45
 */

public class CityUseStatisticVO {
    /**
     * 排序
     */
    private Integer orderId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 访问量
     */
    private Integer useCount;

    public CityUseStatisticVO() {
    }

    public CityUseStatisticVO(String cityName, Integer useCount) {
        this.cityName = cityName;
        this.useCount = useCount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }
}
