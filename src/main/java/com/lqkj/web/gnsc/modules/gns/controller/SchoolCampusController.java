package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsCampusInfo;
import com.lqkj.web.gnsc.modules.gns.service.SchoolCampusService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 校区信息
 * @version 1.0
 * @author cs
 * @since 2020-05-28 17:20
 */

@RestController
@RequestMapping("/schoolCampus")
@Api(value="校区信息controller",tags={"校区信息接口"})
public class SchoolCampusController {
    @Autowired
    private SchoolCampusService schoolCampusService;


    /**
     * 获取校区列表
     * @param schoolId
     * @return
     */
    @ApiOperation("获取校区列表")
    @GetMapping("/list/{schoolId}")
    public MessageBean loadEnrollment(@ApiParam(name = "schoolId", value = "学校ID", required = true)@PathVariable("schoolId") Integer schoolId){

            return MessageBean.ok(schoolCampusService.loadWithSchool(schoolId));
    }

    /**
     * 刷新校区列表
     * @return
     */
    @ApiOperation("刷新校区列表")
    @GetMapping("/refresh")
    public MessageListBean refreshSchoolCampus(){

        return schoolCampusService.refreshCampus();
    }


    /**
     * 校区解绑
     * @param schoolId 学校ID
     * @return
     */
    @ApiOperation("校区解绑")
    @PutMapping("/unBlindCampus")
    public MessageBean unBlindCampus(
            @ApiParam(name = "schoolId", value = "学校ID", required = true)@RequestParam(name="schoolId") Integer schoolId,
            @ApiParam(name="campusId",value="校区ID",required=true) @RequestParam(name = "campusCode") Integer campusCode){

        return schoolCampusService.unBlindCampus(campusCode,schoolId);
    }
    /**
     * 绑定校区
     * @param schoolId 学校ID
     * @param ids 选择校区ID,多个以逗号分隔
     * @return
     */
    @ApiOperation("绑定校区")
    @PutMapping("/blindCampus")
    public MessageBean blindSchool(
            @ApiParam(name = "schoolId", value = "学校ID", required = true)@RequestParam(name="schoolId") Integer schoolId,
            @ApiParam(name="ids",value="选择校区ID,多个以逗号分隔",required=true) @RequestParam(name = "ids") String ids){

        return schoolCampusService.blindCampus(schoolId,ids);

    }
    /**
     * 获取没有绑定学校的校区列表
     * @param campusName
     * @return
     */
    @ApiOperation("获取可绑定校区")
    @GetMapping("/canBlindCampus")
    public  MessageListBean canBlindCampus(@ApiParam(name="campusName",value="校区名称",required=false) @RequestParam(name = "campusName", required = false) String campusName){

        return MessageListBean.ok(schoolCampusService.canBlindCampus(campusName));
    }
    /**
     * 获取所有已经绑定学校的校区列表
     * @param campusName
     * @return
     */
    @ApiOperation("获取其他已绑定校区")
    @GetMapping("/hasBindCampus")
    public MessageListBean hasBindCampus(@ApiParam(name="campusName",value="校区名称",required=false) @RequestParam(name = "campusName", required = false) String campusName,
                                         @ApiParam(name="schoolName",value="学校名称",required=false) @RequestParam(name = "schoolName", required = false) String schoolName){

        return MessageListBean.ok(schoolCampusService.hasBlindCampus(campusName,schoolName));
    }
}
