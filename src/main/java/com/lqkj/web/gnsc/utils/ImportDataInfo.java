package com.lqkj.web.gnsc.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传数据时接收的对象
 */
public class ImportDataInfo {
    private MultipartFile file;

    private String userId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
