package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lqkj.web.gnsc.modules.gns.dao.*;
import com.lqkj.web.gnsc.modules.gns.domain.GnsCampusInfo;
import com.lqkj.web.gnsc.modules.gns.domain.GnsMapUse;
import com.lqkj.web.gnsc.modules.gns.domain.GnsSchool;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.modules.gns.domain.vo.CityUseStatisticVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsUseStatisticVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cs
 * @Date 2020/6/1 19:53
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class GnsUseStatisticService {
    @Autowired
    private MapUseDao mapUseDao;
    @Autowired
    private InteractionStatisticDao interactionStatisticDao;
    @Autowired
    private ApplicationUseDao applicationUseDao;
    @Autowired
    private GnsAchievementReachDao achievementReachDao;
    @Autowired
    private GnsAccessRecordDao accessRecordDao;
    @Autowired
    private GnsStoreItemDao storeItemDao;
    @Autowired
    private DbSearcher dbSearcher;
    @Autowired
    private SchoolInfoDao schoolInfoDao;
    @Autowired
    private SchoolCampusDao schoolCampusDao;

    private static Logger logger = LoggerFactory.getLogger("统计分析");

    public GnsUseStatisticVO useStatistic(Integer schoolId) {

        GnsUseStatisticVO useStatistic = new GnsUseStatisticVO();

        //获取使用次数/人数/分享次数/打卡次数/留影次数
        useStatistic.setIntergrateStatistic(itergrateStatistic(schoolId));
        //获取应用使用统计
        useStatistic.setAppUseStatisticList(applicationUse(schoolId));
        //获取功能使用排行
        useStatistic.setInteractionStatisticList(intergrateUse(schoolId));
        //成就功能数据排行
        useStatistic.setAchievementStatisticList(achievementReachStatistic(schoolId));
        //生活服务点击排行
        useStatistic.setPointStatisticList(pointCount(schoolId));
        //城市统计
        useStatistic.setCityUseList(cityUseStatistic(schoolId));

        return useStatistic;
    }

    /**
     * 获取使用次数/人数/分享次数/打卡次数/留影次数
     *
     * @param schoolId
     * @return
     */
    public JSONObject itergrateStatistic(Integer schoolId) {

        String itergrateStatistic = interactionStatisticDao.integreateStatistic(schoolId);
        if (StringUtils.isNotBlank(itergrateStatistic)) {
            return JSONObject.parseObject(itergrateStatistic);
        }
        return new JSONObject();
    }

    /**
     * 获取应用使用统计
     *
     * @param schoolId
     * @return
     */
    public JSONArray applicationUse(Integer schoolId) {

        String applicationUse = applicationUseDao.applicationUse(schoolId);
        if (StringUtils.isNotBlank(applicationUse)) {
            return JSONArray.parseArray(applicationUse);
        }
        return new JSONArray();
    }

    /**
     * 获取功能使用排行
     *
     * @param schoolId
     * @return
     */
    public JSONArray intergrateUse(Integer schoolId) {

        String intergrateUse = interactionStatisticDao.intergrateUse(schoolId);
        if (StringUtils.isNotBlank(intergrateUse)) {
            return JSONArray.parseArray(intergrateUse);
        }
        return new JSONArray();
    }

    /**
     * 获取成就数据排行
     *
     * @param schoolId
     * @return
     */
    public JSONArray achievementReachStatistic(Integer schoolId) {

        String achievementReachStatistic = achievementReachDao.achievementReachStatistic(schoolId);
        if (StringUtils.isNotBlank(achievementReachStatistic)) {
            return JSONArray.parseArray(achievementReachStatistic);
        }
        return new JSONArray();
    }

    /**
     * 获取生活服务统计排行
     *
     * @return
     */
    public JSONArray pointCount(Integer cmapusCode) {

        String pointCount = mapUseDao.pointCount(cmapusCode);
        if (StringUtils.isNotBlank(pointCount)) {
            return JSONArray.parseArray(pointCount);
        }
        return new JSONArray();
    }

    /**
     * 获取热门导航位置排行
     *
     * @param cmapusCode
     * @return
     */
    public JSONArray navigationStatistic(Integer cmapusCode) {

        String navigationStatistic = mapUseDao.navigationStatistic(cmapusCode);
        if (StringUtils.isNotBlank(navigationStatistic)) {
            return JSONArray.parseArray(navigationStatistic);
        } else {
            return new JSONArray();
        }
    }


    /**
     * 获取热门地标排行
     *
     * @param cmapusCode
     * @return
     */
    public JSONArray hotPointStatistic(Integer cmapusCode) {

        String hotPointStatistic = mapUseDao.hotPointStatistic(cmapusCode);
        if (StringUtils.isNotBlank(hotPointStatistic)) {
            return JSONArray.parseArray(hotPointStatistic);
        } else {
            return new JSONArray();
        }
    }

    /**
     * 获取迎新使用统计（按使用日期统计）
     *
     * @return
     */
    public JSONArray useStatisticByDay(Integer schoolId, String start, String end) {
        String startTime = null;
        String endTime = null;
        if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
            startTime = start;
            endTime = end;
        } else {
            GnsStoreItem storeItem = storeItemDao.findByNameAndKey("runConfiguration", "gnsStatisticStartTime", schoolId);
            String timeJsonString = storeItem.getItemValue();
            if (StringUtils.isNotBlank(timeJsonString)) {
                JSONObject timeJson = JSONObject.parseObject(timeJsonString);
                startTime = timeJson.getString("startTime");
                endTime = timeJson.getString("endTime");
            }
        }
        String useStatisticByDay = accessRecordDao.useStatisticByDay(schoolId, startTime, endTime);
        if (StringUtils.isNotBlank(useStatisticByDay)) {
            return JSONArray.parseArray(useStatisticByDay);
        } else {
            return new JSONArray();
        }
    }

    /**
     * 获取城市统计排行
     */
    public List<CityUseStatisticVO> cityUseStatistic(Integer schoolId) {
        List<Map<String, Object>> ipAddressCount = accessRecordDao.countByIp(schoolId);
        List<CityUseStatisticVO> cityUseList;
        Map<String, Object> cityCountMap = new HashMap<>();
        if (ipAddressCount != null && ipAddressCount.size() > 0) {
            for (Map<String, Object> ipAddressMap : ipAddressCount) {
                String cityName = transformIpToCityName(ipAddressMap.get("ip").toString());
                if (cityName != null) {
                    if (cityCountMap.get(cityName) != null) {
                        cityCountMap.put(cityName, Integer.parseInt(cityCountMap.get(cityName).toString()) + Integer.parseInt(ipAddressMap.get("count").toString()));
                    } else {
                        cityCountMap.put(cityName, ipAddressMap.get("count"));
                    }
                }
            }
            cityCountMap.remove("");
            cityCountMap.remove("内网IP");
        }
        cityUseList = cityCountMap.entrySet()
                .stream()
                .map(e -> new CityUseStatisticVO(e.getKey(), (Integer) e.getValue()))
                .collect(Collectors.toList());

        Collections.sort(cityUseList, (o1, o2) -> o2.getUseCount()
                .compareTo(o1.getUseCount()));
        //设置排序
        for (int i = 0; i < cityUseList.size(); i++) {
            cityUseList.get(i).setOrderId(i + 1);
        }
        return cityUseList;
    }

    /**
     * 迎新使用统计导出
     */
    public ResponseEntity<StreamingResponseBody> useStatisticDownload(Integer schoolId) {

        String finalName = downloadFileName("迎新使用统计",schoolId);

        StreamingResponseBody body = outputStream -> {
            this.gnsUseDownload(schoolId, outputStream);
        };

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename=" + finalName)
                .body(body);
    }

    /**
     * 迎新功能使用统计导出
     */
    public ResponseEntity<StreamingResponseBody> intergrateStatisticDownload(Integer schoolId) {

        String finalName = downloadFileName("迎新功能使用统计",schoolId);

        StreamingResponseBody body = outputStream -> {
            this.gnsInterateDownload(schoolId, outputStream);
        };
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment;filename="  + finalName)
                .body(body);
    }

    private String downloadFileName(String name,Integer schoolId){
        GnsSchool schoolInfo = schoolInfoDao.findBySchoolId(schoolId);
        String titleTime = null;
        String finalName = null;
        GnsStoreItem storeItem = storeItemDao.findByNameAndKey("runConfiguration","gnsStatisticStartTime",schoolId);
        String timeJsonString = storeItem.getItemValue();
        if(StringUtils.isNotBlank(timeJsonString)){
            JSONObject timeJson = JSONObject.parseObject(timeJsonString);
            String startTime = timeJson.getString("startTime");
            String endTime = timeJson.getString("endTime");
            titleTime = startTime.split("-")[0] + startTime.split("-")[1] + startTime.split("-")[2] +
                    endTime.split("-")[0] + endTime.split("-")[1] + endTime.split("-")[2];
        }
        //导出文件的标题
        String title = schoolInfo.getSchoolName() + name + titleTime;
        try {
            finalName =  URLEncoder.encode(title,"UTF-8") + ".xlsx";
        }catch (UnsupportedEncodingException e){
            logger.error(e.getMessage(),e);
        }
        return finalName;
    }


    /**
     * 迎新使用统计导出
     */
    private void gnsUseDownload(Integer schoolId, OutputStream os){
        try {

            GnsSchool schoolInfo = schoolInfoDao.findBySchoolId(schoolId);
            String startTime = null;
            String endTime = null;
            String start = null;
            String end = null;
            GnsStoreItem storeItem = storeItemDao.findByNameAndKey("runConfiguration","gnsStatisticStartTime",schoolId);
            String timeJsonString = storeItem.getItemValue();
            if(StringUtils.isNotBlank(timeJsonString)){
                JSONObject timeJson = JSONObject.parseObject(timeJsonString);
                startTime = timeJson.getString("startTime");
                start = startTime.replaceAll("-",".");
                endTime = timeJson.getString("endTime");
                end = endTime.replaceAll("-",".");
            }
            //导出文件的标题
                String title = schoolInfo.getSchoolName();

            try {
                //防止中文乱码
                // 第一步，创建一个webbook，对应一个Excel文件
                SXSSFWorkbook wb = new SXSSFWorkbook();
                // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                SXSSFSheet sheet = wb.createSheet(title);
                // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                SXSSFRow row = null;
                SXSSFCell cell = null;
                sheet.setDefaultRowHeightInPoints(26);// 设置缺省行高
                sheet.setDefaultColumnWidth(30);//设置缺省列宽
                //设置主标题样式
                CellStyle mainTitleStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(mainTitleStyle);
                Font mainFont = wb.createFont();                   // 设置字体为斜体字
                mainFont.setFontHeightInPoints((short)16);    // 将字体大小设置为14px
                this.setFont(mainFont);
                mainTitleStyle.setFont(mainFont);
                //设置一级标题样式
                CellStyle firstStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(firstStyle);
                Font firstFont = wb.createFont();
                firstFont.setFontHeightInPoints((short)14);    // 将字体大小设置为14px
                firstFont.setBold(true);    //加粗
                this.setFont(firstFont);
                firstStyle.setFont(firstFont);
                //设置二级标题样式
                CellStyle secondStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(secondStyle);
                Font secondFont = wb.createFont();
                secondFont.setFontHeightInPoints((short)12);    // 将字体大小设置为14px
                secondFont.setBold(true);    //加粗
                this.setFont(secondFont);
                secondStyle.setFont(secondFont);
                //设置三级标题样式
                CellStyle thirdStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(thirdStyle);
                Font thirdFont = wb.createFont();                   // 设置字体为斜体字
                thirdFont.setFontHeightInPoints((short)12);    // 将字体大小设置为14px
                this.setFont(thirdFont);
                thirdStyle.setFont(thirdFont);
                //设置单元格样式
                CellStyle cellStyle = wb.createCellStyle();
                this.setTitleStyle(cellStyle);
                Font cellFont = wb.createFont();
                cellFont.setFontHeightInPoints((short)12);    // 将字体大小设置为14px
                this.setFont(cellFont);
                cellStyle.setFont(cellFont);

                Integer lastRow = null;
                //创建第一行
                row = sheet.createRow(0);
                row.setHeightInPoints(26);

                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(schoolInfo.getSchoolName()+"迎新使用统计");
                    cell.setCellStyle(mainTitleStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                //创建第二行
                row = sheet.createRow(1);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue("迎新使用统计时段："+start+"-"+end);
                    cell.setCellStyle(thirdStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

                //创建第三行
                row = sheet.createRow(2);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue("迎新使用统计");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));

                //创建第四行（添加迎新统计数据表头）
                String[] headers = new String[] {"使用人数","使用次数", "分享次数", "合影次数"};
                //创建单元格并写入数据
                row = sheet.createRow(3);//创建所需的行数（从第二行开始写数据）
                row.setHeightInPoints(26);
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(secondStyle);
                }
                //创建第五行（显示迎新统计数据）
                row = sheet.createRow(4);
                row.setHeightInPoints(26);
                // 使用统计
                JSONObject useStatistic = this.itergrateStatistic(schoolId);
                if(useStatistic != null){
                    cell = row.createCell(0);
                    cell.setCellValue(useStatistic.getInteger("usePeopleCount"));
                    cell.setCellStyle(thirdStyle);
                    cell = row.createCell(1);
                    cell.setCellValue(useStatistic.getInteger("useCount"));
                    cell.setCellStyle(thirdStyle);
                    cell = row.createCell(2);
                    cell.setCellValue(useStatistic.getInteger("shareCount"));
                    cell.setCellStyle(thirdStyle);
                    cell = row.createCell(3);
                    cell.setCellValue(useStatistic.getInteger("photoCount"));
                    cell.setCellStyle(thirdStyle);
                    //创建第六行（添加迎新使用趋势统计表头）
                    row = sheet.createRow(5);
                    row.setHeightInPoints(26);
                    //创建单元格、
                    for (int i = 0; i < 4; i++) {
                        cell = row.createCell(i);
                        cell.setCellStyle(secondStyle);
                        cell.setCellValue("");
                        if(i == 0){
                            //设置主标题
                            cell.setCellValue("位置打卡次数");
                            cell.setCellStyle(secondStyle);
                        }
                    }
                    //创建第7行（显示打卡次数）
                    row = sheet.createRow(6);
                    row.setHeightInPoints(26);
                    for (int i = 0; i < 4; i++) {
                        cell = row.createCell(i);
                        cell.setCellValue("");
                        cell.setCellStyle(thirdStyle);
                        if(i == 0){
                            cell.setCellStyle(thirdStyle);
                            cell.setCellValue(useStatistic.getInteger("signCount"));
                        }
                    }

                }
                //创建第8行（添加新生城市统计top10）
                row = sheet.createRow(7);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue("迎新城市统计");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 3));
                //创建第9行（显示城市统计数据）
                //获取城市统计
                List<CityUseStatisticVO> cityUseList = this.cityUseStatistic(schoolId);
                //创建功能访问排行表格
                row = sheet.createRow(8);
                row.setHeightInPoints(26);
                //添加表头
                String[] header = new String[] {"排行","地区", "访问量"};
                for (int i = 0; i < header.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(header[i]);
                    cell.setCellStyle(secondStyle);
                    if(i == header.length - 1){
                        for (int j = 2; j < 4; j++) {
                            cell = row.createCell(j);
                            cell.setCellValue(header[i]);
                            cell.setCellStyle(secondStyle);
                        }
                        sheet.addMergedRegion(new CellRangeAddress(8, 8, 2, 3));
                    }
                }
                if(cityUseList.size() > 0){
                    lastRow = 9;
                    List<Object[]> dataList = new ArrayList<Object[]>();
                    Object[] objs = null;
                    //获取应用使用记录
                    for (int i = 0; i < cityUseList.size(); i++) {
                        objs = new Object[headers.length];
                        objs[0] = cityUseList.get(i).getOrderId();
                        objs[1] = cityUseList.get(i).getCityName();
                        objs[2] = cityUseList.get(i).getUseCount();
                        //数据添加到excel表格
                        dataList.add(objs);
                        if(i == 9){
                            break;
                        }
                    }
                    for(int i = 0; i < dataList.size(); i++){
                        Object[] obj = dataList.get(i);//遍历每个对象
                        row = sheet.createRow(i + 9);//创建所需的行数（从第二行开始写数据）
                        row.setHeightInPoints(26);
                        for(int j=0; j<obj.length; j++){
                            cell = row.createCell(j);
                            if (obj[j]!=null && !obj[j].equals("null") ) {
                                cell.setCellValue(obj[j].toString());
                            }else{
                                cell.setCellValue("");
                            }
                            cell.setCellStyle(thirdStyle);
                            if(j == obj.length - 1){
                                sheet.addMergedRegion(new CellRangeAddress(i + 9, i + 9, 2, 3));
                            }
                        }
                        lastRow = i + 10;
                    }
                }
                //创建第i+10 行
                row = sheet.createRow(lastRow);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int j = 0; j < 4; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue("热门地标TOP10");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 3));

                //创建last +1 行表头
                row = sheet.createRow(lastRow + 1);
                row.setHeightInPoints(26);
                lastRow = lastRow + 1;
                //添加表头
                String[] hotPointHeards= new String[] {"校区","排名", "地标名称","点击量"};
                for (int i = 0; i < hotPointHeards.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(hotPointHeards[i]);
                    cell.setCellStyle(secondStyle);
                }
                //根据学校获取校区
                List<GnsCampusInfo> campusInfoList = schoolCampusDao.findBySchoolId(schoolId);
                //显示热门地标数据
                if(campusInfoList.size() > 0){
                    for (GnsCampusInfo campusInfo : campusInfoList) {
                        JSONArray hotPointArray = this.hotPointStatistic(campusInfo.getCampusCode());
                        if(hotPointArray.size() > 0){
                            List<Object[]> pointList = new ArrayList<Object[]>();
                            Object[] pointObjs = null;
                            //获取应用使用记录
                            for (int i = 0; i < hotPointArray.size(); i++) {
                                pointObjs = new Object[hotPointHeards.length];
                                pointObjs[0] = "";
                                pointObjs[1] = hotPointArray.getJSONObject(i).getInteger("orderId");
                                pointObjs[2] = hotPointArray.getJSONObject(i).getString("infoName");
                                pointObjs[3] = hotPointArray.getJSONObject(i).getInteger("infoCount");
                                //数据添加到excel表格
                                pointList.add(pointObjs);
                            }

                            for(int i = 0; i < pointList.size(); i++){
                                Object[] obj = pointList.get(i);//遍历每个对象
                                row = sheet.createRow(lastRow + i + 1);//创建所需的行数（从第二行开始写数据）
                                row.setHeightInPoints(26);
                                for(int j=0; j < obj.length; j++){
                                    cell = row.createCell(j );
                                    if( row.getRowNum() == (lastRow + pointList.size())){
                                        obj[0] = campusInfo.getCampusName();
                                    }
                                    if (obj[j]!=null && !obj[j].equals("null") ) {
                                        cell.setCellValue(obj[j].toString());
                                    }else{
                                        cell.setCellValue("");
                                    }
                                    cell.setCellStyle(thirdStyle);
                                }
                                if( row.getRowNum() == (lastRow + pointList.size())){
                                    SXSSFRow row2 = null;
                                    SXSSFCell cell2 = null;
                                    row2 = sheet.getRow(lastRow + 1);
                                    cell2 = row2.getCell(0);
                                    sheet.addMergedRegion(new CellRangeAddress(lastRow+1 , (lastRow + pointList.size()), 0, 0));
                                    cell2.setCellValue(obj[0].toString());
                                }
                            }
                            lastRow = lastRow + pointList.size() + 1;
                        }
                    }
//                    System.out.println(lastRow);
                    //获取热门导航位置TOP10
                    row = sheet.createRow(lastRow);
                    row.setHeightInPoints(26);
                    //创建单元格、
                    for (int j = 0; j < 4; j++) {
                        cell = row.createCell(j);
                        cell.setCellValue("热门导航位置TOP10");
                        cell.setCellStyle(firstStyle);
                    }
                    // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                    sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 3));

                    //创建last +1 行表头
                    row = sheet.createRow(lastRow + 1);
                    row.setHeightInPoints(26);
                    lastRow = lastRow + 1;
                    //添加表头
                    String[] navigationHeards= new String[] {"校区","排名", "地标名称","点击量"};
                    for (int i = 0; i < navigationHeards.length; i++) {
                        cell = row.createCell(i);
                        cell.setCellValue(navigationHeards[i]);
                        cell.setCellStyle(secondStyle);
                    }
                    for (GnsCampusInfo campusInfo : campusInfoList) {
                        JSONArray navigationList = this.navigationStatistic(campusInfo.getCampusCode());
                        if(navigationList.size() > 0){
                            List<Object[]> pointList = new ArrayList<Object[]>();
                            Object[] pointObjs = null;
                            //获取应用使用记录
                            for (int i = 0; i < navigationList.size(); i++) {
                                pointObjs = new Object[hotPointHeards.length];
                                pointObjs[0] = "";
                                pointObjs[1] = navigationList.getJSONObject(i).getInteger("orderId");
                                pointObjs[2] = navigationList.getJSONObject(i).getString("infoName");
                                pointObjs[3] = navigationList.getJSONObject(i).getInteger("infoCount");
                                //数据添加到excel表格
                                pointList.add(pointObjs);
                            }
                            for(int i = 0; i < pointList.size(); i++){
                                Object[] obj = pointList.get(i);//遍历每个对象
                                row = sheet.createRow(lastRow + i + 1);//创建所需的行数（从第二行开始写数据）
                                row.setHeightInPoints(26);
                                for(int j=0; j < obj.length; j++){
                                    cell = row.createCell(j );
                                    if( row.getRowNum() == (lastRow + pointList.size())){
                                        obj[0] = campusInfo.getCampusName();
                                    }
                                    if (obj[j]!=null && !obj[j].equals("null") ) {
                                        cell.setCellValue(obj[j].toString());
                                    }else{
                                        cell.setCellValue("");
                                    }
                                    cell.setCellStyle(thirdStyle);
                                }
                                if( row.getRowNum() == (lastRow + pointList.size())){
                                    SXSSFRow row2 = null;
                                    SXSSFCell cell2 = null;
                                    row2 = sheet.getRow(lastRow + 1);
                                    cell2 = row2.getCell(0);
                                    sheet.addMergedRegion(new CellRangeAddress(lastRow+1 , (lastRow + pointList.size()), 0, 0));
                                    cell2.setCellValue(obj[0].toString());
                                }
                            }
                            lastRow = lastRow + pointList.size() + 1;
                        }
                    }
                }
                //创建第n行（添加迎新使用趋势统计表头）
                row = sheet.createRow(lastRow);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int j = 0; j < 4; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue("迎新使用趋势统计（访问次数）");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 3));
                //创建第n行（显示迎新使用趋势统计数据）

                //获取迎新使用统计
                JSONArray useDayList = this.useStatisticByDay(schoolId,startTime,endTime);
                SXSSFRow row1 = null;
                SXSSFCell cell1 = null;
                if(useDayList != null && useDayList.size() > 4){
                    //获取当前数据一共多少行
                    int rows = (int)Math.ceil(useDayList.size() / 4);
                    int index = 0;
                    for(int j = lastRow + 1; j < rows*2+6; j+=2){
                        int k = 0;
                        //表头动态获取，为了表头和值对应，每次创建两行分别设置表头和对应的值
                        row = sheet.createRow(j);
                        row.setHeightInPoints(26);
                        row1 = sheet.createRow(j+1);
                        row1.setHeightInPoints(26);
                        for(int m = 0; m < useDayList.size();m++) {
                            if(m < 4){
                                String head = useDayList.getJSONObject(index).getString("infoName");
                                String value = useDayList.getJSONObject(index).getString("infoCount");
                                //添加表头
                                cell = row.createCell(k);
                                cell.setCellValue(head);
                                cell.setCellStyle(secondStyle);
                                //添加数据
                                cell1 = row1.createCell(k);
                                cell1.setCellValue(value);
                                cell1.setCellStyle(thirdStyle);
                                k++;
                                index++;
                            }else{
                                break;
                            }
                        }
                    }
                }else if(useDayList != null && useDayList.size() <= 4){
                    row = sheet.createRow(lastRow + 1);
                    row.setHeightInPoints(26);
                    row1 = sheet.createRow(lastRow + 2);
                    row1.setHeightInPoints(26);
                    for(int i= 0; i < useDayList.size(); i ++)
                    {
                        //添加表头
                        cell = row.createCell(i);
                        cell.setCellValue(useDayList.getJSONObject(i).getString("infoName"));
                        cell.setCellStyle(secondStyle);
                        //添加数据
                        cell1 = row1.createCell(i);
                        cell1.setCellValue(useDayList.getJSONObject(i).getString("infoCount"));
                        cell1.setCellStyle(thirdStyle);
                    }
                }
                wb.write(os);
                wb.dispose();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 迎新功能统计导出
     */
    private void gnsInterateDownload(Integer schoolId, OutputStream os){
        try {

            GnsSchool schoolInfo = schoolInfoDao.findBySchoolId(schoolId);
            String startTime = null;
            String endTime = null;
            String start = null;
            String end = null;
            GnsStoreItem storeItem = storeItemDao.findByNameAndKey("runConfiguration","gnsStatisticStartTime",schoolId);
            String timeJsonString = storeItem.getItemValue();
            if(StringUtils.isNotBlank(timeJsonString)){
                JSONObject timeJson = JSONObject.parseObject(timeJsonString);
                startTime = timeJson.getString("startTime");
                start = startTime.replaceAll("-",".");
                endTime = timeJson.getString("endTime");
                end = endTime.replaceAll("-",".");
            }
            //导出文件的标题
            String title = schoolInfo.getSchoolName();

            try {
                //防止中文乱码
                // 第一步，创建一个webbook，对应一个Excel文件
                SXSSFWorkbook wb = new SXSSFWorkbook();
                // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
                SXSSFSheet sheet = wb.createSheet(title);
                // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                SXSSFRow row = null;
                SXSSFCell cell = null;
                sheet.setDefaultRowHeightInPoints(26);// 设置缺省行高
                sheet.setDefaultColumnWidth(30);//设置缺省列宽
                //设置主标题样式
                CellStyle mainTitleStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(mainTitleStyle);
                Font mainFont = wb.createFont();                   // 设置字体为斜体字
                mainFont.setFontHeightInPoints((short)16);    // 将字体大小设置为14px
                this.setFont(mainFont);
                mainTitleStyle.setFont(mainFont);
                //设置一级标题样式
                CellStyle firstStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(firstStyle);
                Font firstFont = wb.createFont();
                firstFont.setFontHeightInPoints((short)14);    // 将字体大小设置为14px
                firstFont.setBold(true);    //加粗
                this.setFont(firstFont);
                firstStyle.setFont(firstFont);
                //设置二级标题样式
                CellStyle secondStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(secondStyle);
                Font secondFont = wb.createFont();
                secondFont.setFontHeightInPoints((short)12);    // 将字体大小设置为14px
                secondFont.setBold(true);    //加粗
                this.setFont(secondFont);
                secondStyle.setFont(secondFont);
                //设置三级标题样式
                CellStyle thirdStyle = wb.createCellStyle();        //标题样式
                this.setTitleStyle(thirdStyle);
                Font thirdFont = wb.createFont();                   // 设置字体为斜体字
                thirdFont.setFontHeightInPoints((short)12);    // 将字体大小设置为14px
                this.setFont(thirdFont);
                thirdStyle.setFont(thirdFont);
                //设置单元格样式
                CellStyle cellStyle = wb.createCellStyle();
                this.setTitleStyle(cellStyle);
                Font cellFont = wb.createFont();
                cellFont.setFontHeightInPoints((short)12);    // 将字体大小设置为14px
                this.setFont(cellFont);
                cellStyle.setFont(cellFont);

                Integer lastRow = null;
                //创建第一行
                row = sheet.createRow(0);
                row.setHeightInPoints(26);

                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(schoolInfo.getSchoolName()+"迎新功能使用统计");
                    cell.setCellStyle(mainTitleStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                //创建第二行
                row = sheet.createRow(1);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue("迎新统计时段："+start+"-"+end);
                    cell.setCellStyle(thirdStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));

                //创建第三行
                row = sheet.createRow(2);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue("功能访问量排行");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 3));

                //创建第四行（添加迎新统计数据表头）
                String[] headers = new String[] {"排行","功能名称", "使用次数", "使用人数"};
                //创建单元格并写入数据
                row = sheet.createRow(3);//创建所需的行数（从第二行开始写数据）
                row.setHeightInPoints(26);
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(headers[i]);
                    cell.setCellStyle(secondStyle);
                }
                //创建第五行（显示迎新统计数据）
                // 应用使用统计
                lastRow = 4;
                JSONArray appUseList = this.applicationUse(schoolId);
                if(appUseList.size() >0){
                    List<Object[]> dataList = new ArrayList<Object[]>();
                    Object[] objs = null;
                    //获取应用使用记录
                    for (int i = 0; i < appUseList.size(); i++) {
                        objs = new Object[headers.length];
                        objs[0] = appUseList.getJSONObject(i).getInteger("orderId");
                        objs[1] = appUseList.getJSONObject(i).getString("applicationName");
                        objs[2] = appUseList.getJSONObject(i).getInteger("useCount");
                        objs[3] = appUseList.getJSONObject(i).getInteger("usePeopleCount");
                        //数据添加到excel表格
                        dataList.add(objs);
                    }
                    for(int i = 0; i < dataList.size(); i++){
                        Object[] obj = dataList.get(i);//遍历每个对象
                        row = sheet.createRow(i + 4);//创建所需的行数（从第二行开始写数据）
                        row.setHeightInPoints(26);
                        for(int j=0; j<obj.length; j++){
                            cell = row.createCell(j);
                            if (obj[j]!=null && !obj[j].equals("null") ) {
                                cell.setCellValue(obj[j].toString());
                            }else{
                                cell.setCellValue("");
                            }
                            cell.setCellStyle(thirdStyle);
                        }
                    }
                    lastRow = dataList.size() + lastRow;
                }
                //创建第8行（添加新生城市统计top10）
                row = sheet.createRow(lastRow);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int i = 0; i < 4; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue("校园生活服务点位点击浏览次数统计");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 3));
                //创建第9行（显示城市统计数据）
                row = sheet.createRow(lastRow + 1);
                row.setHeightInPoints(26);
                lastRow = lastRow + 1;
                //添加表头
                String[] hotPointHeards= new String[] {"校区","排名", "服务名称","浏览量"};
                for (int i = 0; i < hotPointHeards.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(hotPointHeards[i]);
                    cell.setCellStyle(secondStyle);
                }
                //根据学校获取校区
                List<GnsCampusInfo> campusInfoList = schoolCampusDao.findBySchoolId(schoolId);
                //显示热门地标数据
                if(campusInfoList.size() > 0) {
                    for (GnsCampusInfo campusInfo : campusInfoList) {
                        JSONArray pointArray = this.pointCount(campusInfo.getCampusCode());
                        if (pointArray.size() > 0) {
                            List<Object[]> pointList = new ArrayList<Object[]>();
                            Object[] pointObjs = null;
                            //获取应用使用记录
                            for (int i = 0; i < pointArray.size(); i++) {
                                pointObjs = new Object[hotPointHeards.length];
                                pointObjs[0] = "";
                                pointObjs[1] = pointArray.getJSONObject(i).getInteger("orderId");
                                pointObjs[2] = pointArray.getJSONObject(i).getString("infoName");
                                pointObjs[3] = pointArray.getJSONObject(i).getInteger("infoCount");
                                //数据添加到excel表格
                                pointList.add(pointObjs);
                            }

                            for (int i = 0; i < pointList.size(); i++) {
                                Object[] obj = pointList.get(i);//遍历每个对象
                                row = sheet.createRow(lastRow + i + 1);//创建所需的行数（从第二行开始写数据）
                                row.setHeightInPoints(26);
                                for (int j = 0; j < obj.length; j++) {
                                    cell = row.createCell(j);
                                    if (row.getRowNum() == (lastRow + pointList.size())) {
                                        obj[0] = campusInfo.getCampusName();
                                    }
                                    if (obj[j] != null && !obj[j].equals("null")) {
                                        cell.setCellValue(obj[j].toString());
                                    } else {
                                        cell.setCellValue("");
                                    }
                                    cell.setCellStyle(thirdStyle);
                                }
                                if (row.getRowNum() == (lastRow + pointList.size())) {
                                    SXSSFRow row2 = null;
                                    SXSSFCell cell2 = null;
                                    row2 = sheet.getRow(lastRow + 1);
                                    cell2 = row2.getCell(0);
                                    sheet.addMergedRegion(new CellRangeAddress(lastRow + 1, (lastRow + pointList.size()), 0, 0));
                                    cell2.setCellValue(obj[0].toString());
                                }
                            }
                            lastRow = lastRow + pointList.size() + 1;
                        }
                    }
                }
                //创建第n行（添加互动功能使用统计）
                row = sheet.createRow(lastRow);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int j = 0; j < 4; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue("互动功能使用统计");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 3));
                //创建第n行（显示表头）
                row = sheet.createRow(lastRow + 1);
                row.setHeightInPoints(26);
                lastRow = lastRow + 1;
                //添加表头
                String[] intergrateHeads= new String[] {"排名", "功能名称","使用次数"};
                for (int i = 0; i < intergrateHeads.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(intergrateHeads[i]);
                    cell.setCellStyle(secondStyle);
                    if(i == intergrateHeads.length - 1){
                        for (int j = 2; j < 4; j++) {
                            cell = row.createCell(j);
                            cell.setCellValue(intergrateHeads[i]);
                            cell.setCellStyle(secondStyle);
                        }
                        sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 2, 3));
                    }
                }
                //获取迎新使用统计
                JSONArray  intergrateUse= this.intergrateUse(schoolId);
                if (intergrateUse.size() > 0) {
                    List<Object[]> pointList = new ArrayList<Object[]>();
                    Object[] pointObjs = null;
                    //获取应用使用记录
                    for (int i = 0; i < intergrateUse.size(); i++) {
                        pointObjs = new Object[hotPointHeards.length];
                        pointObjs[0] = intergrateUse.getJSONObject(i).getInteger("orderId");
                        pointObjs[1] = intergrateUse.getJSONObject(i).getString("infoName");
                        pointObjs[2] = intergrateUse.getJSONObject(i).getInteger("infoCount");
                        //数据添加到excel表格
                        pointList.add(pointObjs);
                    }

                    for (int i = 0; i < pointList.size(); i++) {
                        Object[] obj = pointList.get(i);//遍历每个对象
                        row = sheet.createRow(lastRow + i + 1);//创建所需的行数（从第二行开始写数据）
                        row.setHeightInPoints(26);
                        for (int j = 0; j < obj.length; j++) {
                            cell = row.createCell(j);
                            if (obj[j] != null && !obj[j].equals("null")) {
                                cell.setCellValue(obj[j].toString());
                            } else {
                                cell.setCellValue("");
                            }
                            if(j == obj.length - 1){
                                sheet.addMergedRegion(new CellRangeAddress(lastRow + i + 1 , lastRow + i + 1, 2, 3));
                            }
                            cell.setCellStyle(thirdStyle);
                        }
                    }
                    lastRow = lastRow + pointList.size() + 1;
                }

                //创建第n行（添加成就功能使用统计）
                row = sheet.createRow(lastRow);
                row.setHeightInPoints(26);
                //创建单元格、
                for (int j = 0; j < 4; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue("成就功能数据统计");
                    cell.setCellStyle(firstStyle);
                }
                // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
                sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 0, 3));
                //创建第n行（显示表头）
                row = sheet.createRow(lastRow + 1);
                row.setHeightInPoints(26);
                lastRow = lastRow + 1;
                //添加表头
                String[] achievementHeads= new String[] {"排名", "成就名称","获得量"};
                for (int i = 0; i < achievementHeads.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(achievementHeads[i]);
                    cell.setCellStyle(secondStyle);
                    if(i == achievementHeads.length - 1){
                        for (int j = 2; j < 4; j++) {
                            cell = row.createCell(j);
                            cell.setCellValue(achievementHeads[i]);
                            cell.setCellStyle(secondStyle);
                        }
                        sheet.addMergedRegion(new CellRangeAddress(lastRow, lastRow, 2, 3));
                    }
                }
                //获取迎新使用统计
                JSONArray  achievementList = this.achievementReachStatistic(schoolId);
                if (achievementList.size() > 0) {
                    List<Object[]> pointList = new ArrayList<Object[]>();
                    Object[] pointObjs = null;
                    //获取应用使用记录
                    for (int i = 0; i < achievementList.size(); i++) {
                        pointObjs = new Object[hotPointHeards.length];
                        pointObjs[0] = achievementList.getJSONObject(i).getInteger("orderId");
                        pointObjs[1] = achievementList.getJSONObject(i).getString("infoName");
                        pointObjs[2] = achievementList.getJSONObject(i).getInteger("infoCount");
                        //数据添加到excel表格
                        pointList.add(pointObjs);
                    }

                    for (int i = 0; i < pointList.size(); i++) {
                        Object[] obj = pointList.get(i);//遍历每个对象
                        row = sheet.createRow(lastRow + i + 1);//创建所需的行数（从第二行开始写数据）
                        row.setHeightInPoints(26);
                        for (int j = 0; j < obj.length; j++) {
                            cell = row.createCell(j);
                            if (obj[j] != null && !obj[j].equals("null")) {
                                cell.setCellValue(obj[j].toString());
                            } else {
                                cell.setCellValue("");
                            }
                            if(j == obj.length - 1){
                                sheet.addMergedRegion(new CellRangeAddress(lastRow + i + 1, lastRow + i + 1, 2, 3));
                            }
                            cell.setCellStyle(thirdStyle);
                        }
                    }
                    lastRow = lastRow + pointList.size() + 1;
                }
                wb.write(os);
                wb.dispose();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据IP获取城市名称
     */
    private String transformIpToCityName(String ipAddress){
        String cityName = "";
        if (StringUtils.isNotBlank(ipAddress)) {
            try {
                //解决双ip问题
                String[] splitIp = ipAddress.split(",");
                //取第一个ip进行查询
                DataBlock block = dbSearcher.memorySearch(splitIp[0]);
                String region = block.getRegion();
                region = region.replaceAll("\\|", ",");
                String[] address = region.split(",");
                System.out.println(cityName);
                cityName = address[3];
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
            return cityName;
        }
        return null;
    }

    /**
     * 设置标题样式
     */
    private void setTitleStyle(CellStyle titleStyle){
        titleStyle.setBorderBottom(BorderStyle.THIN); //下边框
        titleStyle.setBorderLeft(BorderStyle.THIN);//左边框
        titleStyle.setBorderTop(BorderStyle.THIN);//上边框
        titleStyle.setBorderRight(BorderStyle.THIN);//右边框
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    }

    /**
     * 设置标题字体
     */
    private void setFont( Font font){
        font.setItalic(false);                     // 设置字体为斜体字
        font.setFontName("宋体");
    }
}
