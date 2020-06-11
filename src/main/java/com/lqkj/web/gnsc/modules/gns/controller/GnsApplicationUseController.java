package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.service.ApplicationUseService;
import com.lqkj.web.gnsc.modules.gns.service.MapUseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户位置上传
 */

@RestController
@RequestMapping("/applicationUse")
@Api(value="应用使用管理",tags={"应用使用管理"})
public class GnsApplicationUseController {
    @Autowired
    private ApplicationUseService applicationUseService;


    /**
     * 点击/导航时调用接口
     * @return
     */
    @GetMapping("/save")
    @ApiOperation("h5 点击应用 时调用")
    public MessageBean save(@ApiParam(name = "userCode",value = "用户id")@RequestParam(name = "userCode")String userCode,
                            @ApiParam(name = "appId",value = "点击应用ID")@RequestParam(name = "appId")Integer appId){
        return MessageBean.ok(applicationUseService.save(userCode,appId));

    }

}
