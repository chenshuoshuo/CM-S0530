package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.DormNavigationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cs
 * @Date 2020/6/1 13:36
 * @Version 2.2.2.0
 **/
@RestController
@RequestMapping("/dorm")
@Api(tags = "迎新宿舍")
public class DormNavigationController {

    @Autowired
    private DormNavigationService dormNavigationService;

    /**
     * 根据校区获取列表
     * @param vectorZoomCode
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("H5根据校区获取宿舍楼列表")
    public MessageListBean queryList(@ApiParam(name = "vectorZoomCode",value = "校区二维ID") @RequestParam(name = "vectorZoomCode") Integer vectorZoomCode){

        return MessageListBean.ok(dormNavigationService.queryDormList(vectorZoomCode));
    }

    /**
     * h5根据学校获取列表
     * @param schoolId
     * @return
     */
    @GetMapping("/listWithSchool")
    @ApiOperation("H5获取学校所有宿舍楼")
    public MessageListBean listWithSchool(@ApiParam(name = "schoolId",value = "学校ID") @RequestParam(name = "schoolId") Integer schoolId){

        return MessageListBean.ok(dormNavigationService.queryDormListWithSchoolId(schoolId));
    }
}
