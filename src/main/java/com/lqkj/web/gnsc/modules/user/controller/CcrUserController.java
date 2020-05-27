package com.lqkj.web.gnsc.modules.user.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.handler.WebSocketPushHandler;
import com.lqkj.web.gnsc.modules.user.domain.User;
import com.lqkj.web.gnsc.modules.user.service.CcrUserService;
import com.lqkj.web.gnsc.utils.PwdCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "用户管理")
@RestController
@Validated
public class CcrUserController {

    @Autowired
    CcrUserService ccrUserService;
    @Autowired
    WebSocketPushHandler webSocketPushHandler;


    @ApiOperation("注册用户")
    @PutMapping("/user/register")
    public MessageBean<User> register(@RequestBody User user, @RequestParam Integer adminCode,
                                      @ApiIgnore Authentication authentication) throws Exception {
        //检查密码是否为大于6-20位的长度
        boolean checkPasLength = PwdCheckUtil.checkPasswordLength(user.getPassword(), "6", "20");
        if(!checkPasLength){
            return MessageBean.error("请输入6位以上并且小于20位密码");
        }
        //检查是否包含数字
        boolean checkContainDigit = PwdCheckUtil.checkContainDigit(user.getPassword());
        //检查是否包含大写字母
        boolean checkContainUpperCase = PwdCheckUtil.checkContainUpperCase(user.getPassword());
        if(!checkContainDigit || !checkContainUpperCase){
            return MessageBean.error("密码必须包含大写字母和数字");
        }
        String userName = "guest";
        if(authentication!=null){
            Jwt jwt = (Jwt)authentication.getPrincipal();
            userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        }
        if (ccrUserService.registerAdmin(adminCode, user, userName) != null) {
            return MessageBean.ok(ccrUserService.registerAdmin(adminCode, user, userName));
        }
        return MessageBean.error("用户已存在");
    }

    @ApiOperation("根据用户名查询用户信息")
    @GetMapping("/user/name/{username}")
    public MessageBean<User> info(@PathVariable String username, @ApiIgnore Authentication authentication) throws Exception {

        //String name = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = "";
        if (authentication != null) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            name = (String) jwt.getClaims().get("user_name");
        }
        UserDetails userDetails = ccrUserService.loadUserByUsername(name);
        if(userDetails==null){
            return MessageBean.error("沒有该用戶");
        }
        return MessageBean.ok((User) userDetails);
    }

    @ApiOperation("根据用户名查询用户信息")
    @PostMapping("/user/push/msg")
    public MessageBean<User> msg(@RequestParam(name = "msg",required = true) String msg,@RequestParam(name = "uid", required = true) String uid) throws Exception {
        Boolean pushStatus = webSocketPushHandler.pushMsg(uid, msg);
        return pushStatus?MessageBean.ok():MessageBean.error("推送失败");
    }
}
