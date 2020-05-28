package com.lqkj.web.gnsc.modules.log.service;

import com.lqkj.web.gnsc.modules.log.dao.GnsManageLogRepository;
import com.lqkj.web.gnsc.modules.log.domain.GnsManageLog;
import com.lqkj.web.gnsc.modules.manager.dao.GnsManageUserRepository;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageResource;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageRole;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageUser;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GnsManageLogService {

    @Autowired
    GnsManageLogRepository gnsManageLogRepository;
    @Autowired
    GnsManageUserRepository userRepository;

    /**
     * 增加一条日志记录
     */
    public void addLog(String source, String method, String description, String userName) {
        GnsManageLog systemLog = new GnsManageLog(source, method, description);
        GnsManageUser ccrUser = userRepository.findByUserName(userName);
        List<String> list = new ArrayList<>();
        if(ccrUser != null){
            Set<GnsManageRole> rules = ccrUser.getRules();
            if(rules.size() > 0){
                for(GnsManageRole rule :rules){
                   list.add(rule.getName());
                }
                String rule = StringUtils.join(list,",");
                systemLog.setRule(rule);
            }
        }
        systemLog.setUserName(userName);
        gnsManageLogRepository.save(systemLog);
    }

    /**
     * 分页
     */
    public Page<GnsManageLog> page(Timestamp startTime, Timestamp endTime,
                                   Integer page, Integer pageSize) {
        if (startTime!=null && endTime!=null) {
            return gnsManageLogRepository.pageByTime(startTime, endTime, PageRequest.of(page, pageSize));
        }
        //根据日志的时间进行排序
        return gnsManageLogRepository.findAllByTimeDesc(PageRequest.of(page, pageSize));
    }

    public void export(Timestamp startTime, Timestamp endTime, OutputStream os) throws IOException {
        List<GnsManageLog> logs = gnsManageLogRepository.findAllByTime(startTime, endTime);

        SXSSFWorkbook workbook = new SXSSFWorkbook(11);

        Sheet sheet = workbook.createSheet();

        //设置头
        Row rootRow = sheet.createRow(0);
        rootRow.createCell(0).setCellValue("日志id");
        rootRow.createCell(1).setCellValue("创建时间");
        rootRow.createCell(2).setCellValue("介绍");
        rootRow.createCell(3).setCellValue("执行方法");
        rootRow.createCell(4).setCellValue("来源");
        rootRow.createCell(5).setCellValue("执行者用户名");
        rootRow.createCell(6).setCellValue("执行者角色");

        for (int i = 0; i < logs.size(); i++) {
            GnsManageLog log = logs.get(i);

            Row dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(log.getLogId().toString());
            dataRow.createCell(1).setCellValue(log.getCreateTime().toString());
            dataRow.createCell(2).setCellValue(log.getDescription());
            dataRow.createCell(3).setCellValue(log.getMethod());
            dataRow.createCell(4).setCellValue(log.getSource());
            dataRow.createCell(5).setCellValue(log.getUserName());
            dataRow.createCell(6).setCellValue(log.getRule());
        }

        workbook.write(os);

        workbook.dispose();
    }
}
