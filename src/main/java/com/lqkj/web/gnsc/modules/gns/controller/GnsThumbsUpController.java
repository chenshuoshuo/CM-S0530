package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsThumbsUp;
import com.lqkj.web.gnsc.modules.gns.service.GnsSignService;
import com.lqkj.web.gnsc.modules.gns.service.GnsThumbsUpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 点标注分类信息管理
 */

@RestController
@RequestMapping("/thumbsUp")
@Api(value="点赞管理",tags={"点赞管理"})
public class GnsThumbsUpController {
    @Autowired
    private GnsThumbsUpService thumbsUpService;


    /**
     * 点赞
     * @param userCode
     * @param mapCode
     * @param mapType
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("点赞")
    public MessageBean save(@ApiParam(name = "userCode",value = "用户ID")@RequestParam(name = "userCode")String userCode,
                            @ApiParam(name = "mapCode",value = "地标ID，地标标签传入pointCode的值,其他为mapCode的值")@RequestParam(name = "mapCode")Integer mapCode,
                            @ApiParam(name = "mapType",value = "地标类型（地标标签传point；大楼、房间、其他面传polygon")@RequestParam(name = "mapType") String mapType){

        return MessageBean.ok(thumbsUpService.save(userCode,mapCode,mapType));

    }

}
