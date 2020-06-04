package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.service.GnsThumbsUpService;
import com.lqkj.web.gnsc.modules.gns.service.GnsUserInfoService;
import com.lqkj.web.gnsc.modules.handler.WebSocketPushHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户位置上传
 */

@RestController
@RequestMapping("/location")
@Api(value="用户位置上传",tags={"用户位置上传"})
public class GnsUserLocationController {
    @Autowired
    private GnsUserInfoService userInfoService;
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;


    /**
     * 上传位置并推送
     * @param userCode
     * @return
     */
    @GetMapping("/loadUserLocation")
    @ApiOperation("上传位置并推送")
    public MessageBean save(@ApiParam(name = "userCode",value = "用户ID")@RequestParam(name = "userCode")String userCode,
                            @ApiParam(name = "lng",value = "经度")@RequestParam(name = "lng")Double lng,
                            @ApiParam(name = "lat",value = "纬度")@RequestParam(name = "lat") Double lat){

        return MessageBean.ok(userInfoService.loadUserLocation(userCode,lng,lat));

    }

}
