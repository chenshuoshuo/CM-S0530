package com.lqkj.web.gnsc.modules.gns.domain.vo;

/**
 * 键值对对象，用于统计
 * @author RY
 * @version 1.0
 * @since 2018-7-3 10:21:37
 */

public class KeyValueVO {
    /**
     * 键
     */
    private String keyString;
    /**
     * 值
     */
    private String valueString;

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
