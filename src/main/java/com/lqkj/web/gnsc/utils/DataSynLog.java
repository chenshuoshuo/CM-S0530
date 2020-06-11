package com.lqkj.web.gnsc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 数据同步对象
 */
public class DataSynLog {
    private Integer zoneId;
    private Boolean synStatus;
    private String errMsg;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public DataSynLog(Integer zoneId, Boolean synStatus, String errMsg) {
        this.zoneId = zoneId;
        this.synStatus = synStatus;
        this.errMsg = errMsg;
    }

    public DataSynLog(Integer zoneId, Boolean synStatus, Exception e) {
        this.zoneId = zoneId;
        this.synStatus = synStatus;

        if(e != null){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            //e.printStackTrace(pw);
            logger.error(e.getMessage(),e);
            this.errMsg = sw.toString();
        }


    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Boolean getSynStatus() {
        return synStatus;
    }

    public void setSynStatus(Boolean synStatus) {
        this.synStatus = synStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
