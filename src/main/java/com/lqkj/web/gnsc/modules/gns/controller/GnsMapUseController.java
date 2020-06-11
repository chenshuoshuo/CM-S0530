package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.InteractionStatisticService;
import com.lqkj.web.gnsc.modules.gns.service.MapUseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户位置上传
 */

@RestController
@RequestMapping("/mapUse")
@Api(value="地图使用管理",tags={"地图使用管理"})
public class GnsMapUseController {
    @Autowired
    private MapUseService mapUseService;


    /**
     * 点击/导航时调用接口
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("h5 点击/导航地图元素 时调用")
    public MessageBean save(@ApiParam(name = "userCode",value = "用户id")@RequestParam(name = "userCode")String userCode,
                            @ApiParam(name = "campusCode",value = "校区区域组ID")@RequestParam(name = "campusCode")Integer campusCode,
                            @ApiParam(name = "recordType",value = "1点击，2搜索，3导航起点，4导航终点，5生活服务分类")@RequestParam(name = "recordType")Integer recordType,
                                 @ApiParam(name = "elementName",value = "点击/导航地图元素名称")@RequestParam(name = "elementName")String  elementName){
        return MessageBean.ok(mapUseService.save(campusCode,userCode,recordType,elementName));

    }

}
