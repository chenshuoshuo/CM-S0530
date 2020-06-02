package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsSignDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsDisplayPointType;
import com.lqkj.web.gnsc.modules.gns.service.GnsSignService;
import com.lqkj.web.gnsc.modules.gns.service.PointTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 点标注分类信息管理
 */

@RestController
@RequestMapping("/sign")
@Api(value="打卡管理",tags={"打卡接口"})
public class GnsSignController {
    @Autowired
    private GnsSignService signService;


    /**
     * 批量保存默认地标分类
     * @return
     */
    @ApiOperation("h5获取弹幕列表")
    @GetMapping("/queryList")
    public MessageListBean queryList(@ApiParam(name = "schoolId", value = "学校ID", required = true)@RequestParam(name = "schoolId")Integer schoolId){

        return MessageListBean.ok(signService.queryList(schoolId));
    }

}
