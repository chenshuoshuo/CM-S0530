package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.APIVersion;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.GnsSignService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 点标注分类信息管理
 */

@RestController
@RequestMapping("/sign")
@Api(value = "打卡管理", tags = {"打卡管理"})
public class GnsSignController {
    @Autowired
    private GnsSignService signService;


    /**
     * 批量保存默认地标分类
     *
     * @return
     */
    @ApiOperation("h5获取弹幕列表")
    @GetMapping("/queryList")
    public MessageListBean queryList(@ApiParam(name = "schoolId", value = "学校ID", required = true) @RequestParam(name = "schoolId") Integer schoolId) {

        return MessageListBean.ok(signService.queryList(schoolId));
    }


    @PostMapping("/save")
    @ApiOperation("上传打卡信息")
    public MessageBean save(@ApiParam(name = "userCode", value = "用户ID") @RequestParam(name = "userCode") String userCode,
                            @ApiParam(name = "mapCode", value = "地标ID，地标标签传入pointCode的值,其他为mapCode的值") @RequestParam(name = "mapCode") Integer mapCode,
                            @ApiParam(name = "mapType", value = "地标类型（地标标签传point；大楼、房间、其他面传polygon") @RequestParam(name = "mapType") String mapType) {

        return signService.save(userCode, mapCode, mapType);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "campusCode", value = "学校id(必须)", paramType = "query")
    })
    @GetMapping(APIVersion.V1 + "/getSignRanking")
    @ApiOperation("获取打卡地标排行榜")
    public MessageListBean getSignRanking(@RequestParam(name = "campusCode") Integer campusCode) {
        return signService.getSignRanking(campusCode);
    }
}
