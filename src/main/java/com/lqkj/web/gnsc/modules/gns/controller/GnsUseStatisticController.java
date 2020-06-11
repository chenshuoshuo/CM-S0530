package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.GnsUseStatisticService;
import com.lqkj.web.gnsc.modules.gns.service.MapUseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户位置上传
 */

@RestController
@RequestMapping("/gnsUse")
@Api(value="迎新使用统计分析",tags={"迎新使用统计分析"})
public class GnsUseStatisticController {
    @Autowired
    private GnsUseStatisticService statisticService;
    @Autowired
    private MapUseService mapUseService;

    /**
     * 统计分析
     * @return
     */
    @GetMapping("/statistic")
    @ApiOperation("统计分析")
    public MessageBean save(@ApiParam(name = "schoolId",value = "学校Id")@RequestParam(name = "schoolId")Integer schoolId){
        return MessageBean.ok(statisticService.useStatistic(schoolId));

    }


    /**
     * 获取生活服务点位点击排行
     * @return
     */
    @GetMapping("/lifePointStatistic")
    @ApiOperation("获取生活服务点位点击排行")
    public MessageListBean lifePointStatistic(@ApiParam(name = "campusCode",value = "校区区域组ID")@RequestParam(name = "campusCode")Integer campusCode) {
        return MessageListBean.ok(statisticService.pointCount(campusCode));
    }

    /**
     * 获取热门导航位置排行
     * @return
     */
    @GetMapping("/navigationStatistic")
    @ApiOperation("获取热门导航位置排行")
    public MessageListBean navigation(@ApiParam(name = "campusCode",value = "校区区域组ID")@RequestParam(name = "campusCode")Integer campusCode){
        return MessageListBean.ok(statisticService.navigationStatistic(campusCode));
    }


    /**
     * 获取热门地标排行
     * @return
     */
    @GetMapping("/hotPointStatistic")
    @ApiOperation("获取热门地标排行")
    public MessageListBean hotPointStatistic(@ApiParam(name = "campusCode",value = "校区区域组ID")@RequestParam(name = "campusCode")Integer campusCode){
        return MessageListBean.ok(statisticService.hotPointStatistic(campusCode));
    }


    /**
     * 获取迎新使用统计（按日期统计）
     * @return
     */
    @GetMapping("/useStatisticByDay")
    @ApiOperation("获取迎新使用统计（按日期统计）")
    public MessageListBean hotPointStatistic(@ApiParam(name = "schoolId",value = "学校ID")@RequestParam(name = "schoolId")Integer schoolId,
                                             @ApiParam(name = "start",value = "开始时间,格式（2020-06-11）")@RequestParam(name = "start",required = false)String start,
                                             @ApiParam(name = "end",value = "结束时间，格式（2020-06-11）")@RequestParam(name = "end",required = false)String end){
        return MessageListBean.ok(statisticService.useStatisticByDay(schoolId,start,end));
    }

    /**
     * 迎新应用统计
     * @param schoolId
     * @return
     */
    @ApiOperation("迎新使用统计导出")
    @GetMapping("/gnsUseStatisticExcel")
    public ResponseEntity<StreamingResponseBody> gnsUseStatisticExcel(HttpServletResponse response, HttpServletRequest request,
                                                       @ApiParam(name="schoolId",value="学校id",required=true) @RequestParam("schoolId") Integer schoolId){

        return statisticService.useStatisticDownload(schoolId);
    }

    /**
     * 迎新应用统计
     * @param schoolId
     * @return
     */
    @ApiOperation("迎新功能使用统计导出")
    @GetMapping("/gnsUseIntergrateStatisticExcel")
    public ResponseEntity<StreamingResponseBody> gnsUseIntergrateStatisticExcel(HttpServletResponse response, HttpServletRequest request,
                                                                      @ApiParam(name="schoolId",value="学校id",required=true) @RequestParam("schoolId") Integer schoolId){

        return statisticService.intergrateStatisticDownload(schoolId);
    }
}
