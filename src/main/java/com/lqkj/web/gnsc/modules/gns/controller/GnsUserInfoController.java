package com.lqkj.web.gnsc.modules.gns.controller;


import com.lqkj.web.gnsc.APIVersion;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.GnsUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cs
 * @Date 2020/05/28 10:35
 * @Version 1.0
 **/
@Api(tags = "微信用户信息")
@RestController
@RequestMapping("/gns/userInfo/")
public class GnsUserInfoController {

    @Autowired
    private GnsUserInfoService userInfoService;


    @GetMapping(APIVersion.V1 + "/getUserInfo")
    @ApiOperation("获取微信用户信息")
    public MessageBean<Object> getUserInfo(@RequestParam(name = "code") String code,
                                           @RequestParam(name = "schoolId") Integer schoolId,
                                           HttpServletRequest request) {
        return userInfoService.getUserInfo(request, schoolId, code);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id(必须)", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "真实姓名(非空时更新)", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号(非空时更新)", paramType = "query"),
            @ApiImplicitParam(name = "academyCode", value = "院系id(非空时更新)", paramType = "query"),
            @ApiImplicitParam(name = "dormId", value = "宿舍id(非空时更新)", paramType = "query")
    })
    @PutMapping(APIVersion.V1 + "/updateUserInfo")
    @ApiOperation("更新微信用户个人信息")
    public MessageBean<Object> updateUserInfo(@RequestParam(name = "userId") String userId,
                                              @RequestParam(name = "name", required = false) String name,
                                              @RequestParam(name = "mobile", required = false) String mobile,
                                              @RequestParam(name = "academyCode", required = false) String academyCode,
                                              @RequestParam(name = "dormId", required = false) String dormId) {
        return userInfoService.updateUserInfo(userId, name, mobile, academyCode, dormId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id(必须)", paramType = "query")
    })
    @GetMapping(APIVersion.V1 + "/getUserAchievement")
    @ApiOperation("获取微信用户成就信息")
    public MessageListBean getUserAchievement(@RequestParam(name = "userId") String userId) {
        return userInfoService.getUserAchievement(userId);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id(必须)", paramType = "query")
    })
    @GetMapping(APIVersion.V1 + "/getUserSignRanking")
    @ApiOperation("获取校友打卡排行排行榜")
    public MessageBean getUserSignRanking(@RequestParam(name = "userId") String userId) {
        return userInfoService.getUserSignRanking(userId);
    }
}
