package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsAcademy;
import com.lqkj.web.gnsc.modules.gns.service.GnsAcademyService;
import com.lqkj.web.gnsc.modules.gns.service.GnsPushMessageService;
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
@RequestMapping("/academy")
@Api(value="院系信息管理",tags={"院系信息管理"})
public class GnsAcademyController {
    @Autowired
    private GnsAcademyService academyService;
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;


    /**
     * h5获取院系列表
     * @return
     */
    @GetMapping("/queryList")
    @ApiOperation("h5获取院系列表")
    public MessageListBean queryList(@ApiParam(name = "schoolId",value = "学校id")@RequestParam(name = "schoolId")Integer schoolId){
        return MessageListBean.ok(academyService.findBySchoolId(schoolId));

    }

}
