package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.service.GnsApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Description 应用中心
 * @Author cs
 * @Date 2020/05/28 16:33
 * @Version 2.0.2
 **/
@Api(tags = "应用中心")
@RestController
@RequestMapping("/gns/application")
public class GnsApplicationController {
    @Autowired
    private GnsApplicationService applicationService;

    /**
     * 移动端查询应用列表
     */
    @GetMapping("/getParentAppList")
    @ApiOperation("前端查询一级应用列表")
    public MessageListBean appList(@RequestParam(name = "schoolId") Integer schoolId, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
       return MessageListBean.ok(applicationService.getParentAppList(schoolId,userName));
    }

    /**
     *
     * @param schoolId
     */
    @GetMapping("/getSubAppList")
    @ApiOperation("前端查询子级应用列表")
    public MessageListBean getSubAppList(@RequestParam(name = "enName") String enName,
                                   @RequestParam(name = "schoolId") Integer schoolId, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        return MessageListBean.ok(applicationService.getSubAppList(enName,schoolId,userName));
    }

    @GetMapping("/page")
    @ApiOperation("管理端分页")
    public MessageBean page(@RequestParam(name = "schoolId") Integer schoolId,
                            @RequestParam(name = "appName",required = false) String appName,
                            @RequestParam(name = "page") Integer page,
                            @RequestParam(name = "pageSize") Integer pageSize, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        return MessageBean.ok(applicationService.page(schoolId,appName,page,pageSize,userName));

    }

    @GetMapping("/get")
    @ApiOperation("根据主键获取")
    public MessageBean get(@RequestParam(name = "appId") Integer appId, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        return MessageBean.ok(applicationService.get(appId,userName));
    }

    @PostMapping("/add")
    @ApiOperation("添加应用")
    public MessageBean add(@RequestBody GnsApplication application, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        GnsApplication ap = applicationService.existWithEnName(application.getEnName(),application.getSchoolId());
        if(ap !=null){
            return MessageBean.error("英文名称重复");
        }
        return MessageBean.ok(applicationService.add(application,userName));
    }

    @PostMapping("/update")
    @ApiOperation("更新应用")
    public MessageBean update(@RequestBody GnsApplication application, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        GnsApplication ap = applicationService.existWithEnName(application.getEnName(),application.getSchoolId());
        if(ap != null && ap.getApplicationId() != application.getApplicationId()){
            return MessageBean.error("英文名称重复");
        }
        return MessageBean.ok(applicationService.update(application,userName));
    }

    @PostMapping("/updateOpen")
    @ApiOperation("开启/关闭应用")
    public MessageBean updateOpen(@RequestParam(name = "appId") Integer appId,
                                  @RequestParam(name = "open") Boolean open, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        return MessageBean.ok(applicationService.updateOpen(appId,open,userName));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除应用")
    public MessageBean delete(@RequestParam(name = "appId") Integer appId, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        GnsApplication application = applicationService.get(appId,userName);
        if (application.getPreset()) {
            return MessageBean.error("预设模块无法删除");
        }
        return MessageBean.ok(applicationService.delete(appId,userName));
    }

    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除应用")
    public MessageBean delete(@RequestParam(name = "ids") String ids, @ApiIgnore Authentication authentication){
        Jwt jwt = (Jwt)authentication.getPrincipal();
        String userName = (String)jwt.getClaims().get("user_name") == null? "guest" : (String)jwt.getClaims().get("user_name");
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            GnsApplication application  = applicationService.get(Integer.parseInt(id),userName);
            if (application.getPreset()) {
                return MessageBean.error("预设模块无法删除");
            }
        }
        return MessageBean.ok(applicationService.bulkDelete(ids,userName));
    }


}
