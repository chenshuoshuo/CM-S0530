package com.lqkj.web.gnsc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据导入日志对象
 * @version 1.0
 * @author RY
 */

public class DataImportLog {
    /**
     * 正在导入的数据分类
     */
    private String category;
    /**
     * 数据总条数
     */
    private Integer totalCount = 0;
    /**
     * 已导入条数
     */
    private Integer importedCount = 0;
    /**
     * 错误数据条数
     */
    private Integer errorCount = 0;
    /**
     * 正在导入的数据-二级分类
     */
    private String subCategory;
    /**
     * 二级分类数据总条数
     */
    private Integer subTotalCount = 0;
    /**
     * 二级分类已导入条数
     */
    private Integer subImportedCount = 0;
    /**
     * 二级分类错误数据条数
     */
    private Integer subErrorCount = 0;
    /**
     * 错误数据列表
     */
    private List<Object> errorDataList;
    /**
     * 错误码
     */
    private Integer errorCode = 0;
    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误标识码
     */
    private String errorLogCode;

    public DataImportLog(){}

    public DataImportLog(String category, Integer totalCount) {
        this.category = category;
        this.totalCount = totalCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getImportedCount() {
        return importedCount;
    }

    public void setImportedCount(Integer importedCount) {
        this.importedCount = importedCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getSubTotalCount() {
        return subTotalCount;
    }

    public void setSubTotalCount(Integer subTotalCount) {
        this.subTotalCount = subTotalCount;
    }

    public Integer getSubImportedCount() {
        return subImportedCount;
    }

    public void setSubImportedCount(Integer subImportedCount) {
        this.subImportedCount = subImportedCount;
    }

    public Integer getSubErrorCount() {
        return subErrorCount;
    }

    public void setSubErrorCount(Integer subErrorCount) {
        this.subErrorCount = subErrorCount;
    }

    public List<Object> getErrorDataList() {
        return errorDataList;
    }

    public void setErrorDataList(List<Object> errorDataList) {
        this.errorDataList = errorDataList;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorLogCode() {
        return errorLogCode;
    }

    public void setErrorLogCode(String errorLogCode) {
        this.errorLogCode = errorLogCode;
    }

    /**
     * 成功导入一条数据
     * 变更计数
     * @param isSubImported 导入是否包含多级
     */
    public void addImportedCount(Boolean isSubImported){
        this.importedCount += 1;
        if(isSubImported){
            this.subImportedCount += 1;
        }
    }

    /**
     * 添加一条错误记录
     * @param isSubImported 导入是否包含多级
     * @param object 导入对象
     */
    public void addError(Boolean isSubImported, Object object){
        this.errorCount += 1;

        if(this.errorDataList == null){
            this.errorDataList = new ArrayList<>();
        }
        this.errorDataList.add(object);

        if(isSubImported){
            this.subErrorCount += 1;
        }
    }
}
