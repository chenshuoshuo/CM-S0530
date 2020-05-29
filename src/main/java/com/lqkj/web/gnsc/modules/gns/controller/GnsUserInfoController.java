package com.lqkj.web.gnsc.modules.gns.controller;


import com.lqkj.web.gnsc.APIVersion;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.service.GnsUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public MessageBean<Object> getUserInfo (@RequestParam(name = "code") String code,
                                                  @RequestParam(name = "schoolId") Integer schoolId,
                                                  HttpServletRequest request) {
        return userInfoService.getUserInfo(request,schoolId,code);
    }
}
