package com.lqkj.web.gnsc.modules.manager.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.manager.domain.GnsManageUser;
import com.lqkj.web.gnsc.modules.manager.service.GnsCasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "cas处理")
@RestController
public class GnsCasController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GnsCasService casService;

    @ApiOperation("cas回调地址")
    @GetMapping("/center/cas/callback")
    public String cas(@RequestParam String ticket) {
        logger.info("收到cas ticket:{}", ticket);
        return ticket;
    }

    @ApiOperation("绑定cas ticket")
    @RequestMapping(value = "/center/cas/bind", method = {RequestMethod.GET, RequestMethod.POST})
    public MessageBean<GnsManageUser> user(@RequestParam String username,
                                           @RequestParam String ticket) throws DocumentException {
        return MessageBean.ok(casService.updateTicket(username, ticket));
    }
}
