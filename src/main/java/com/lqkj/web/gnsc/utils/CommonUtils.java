package com.lqkj.web.gnsc.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    public static boolean ifNotNull(String s) {
        return s != null && !s.equals("");
    }

    public static String formatTimeStamp(Timestamp timestamp) {
        //默认格式
        return formatTimeStamp(timestamp, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间戳转为指定的String格式
     *
     * @param timestamp 时间戳
     * @param format    指定的格式 如：yyyy-MM-dd HH:mm:ss
     * @return 如：2020-10-02 21:03:55
     */
    public static String formatTimeStamp(Timestamp timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timestamp.getTime()));
    }
}
