package com.lqkj.web.gnsc.modules.gns.domain.vo;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author cs
 * @Date 2020/5/28 12:50
 * @Version 2.2.2.0
 **/

public class GnsApplicationVO {
    private String applicationName;
    private String enName;
    private String logo;
    private String qrCode;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
