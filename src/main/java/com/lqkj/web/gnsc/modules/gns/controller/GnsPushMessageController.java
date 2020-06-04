package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsPushMessage;
import com.lqkj.web.gnsc.modules.gns.service.GnsPushMessageService;
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
@RequestMapping("/pushMessage")
@Api(value="推送消息管理",tags={"推送消息管理"})
public class GnsPushMessageController {
    @Autowired
    private GnsPushMessageService pushMessageService;
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;


    /**
     * 上传位置并推送
     * @param pushId
     * @return
     */
    @PostMapping("/changeValid")
    @ApiOperation("设置消息是否已读")
    public MessageBean change(@ApiParam(name = "pushId",value = "消息ID")@RequestParam(name = "pushId")String pushId){
        pushMessageService.changeValid(pushId);
        return MessageBean.ok();

    }

}
