package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.dao.InteractionStatisticDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsInteractionStatistic;
import com.lqkj.web.gnsc.modules.gns.service.GnsAcademyService;
import com.lqkj.web.gnsc.modules.gns.service.InteractionStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户位置上传
 */

@RestController
@RequestMapping("/interaction")
@Api(value="互动功能管理",tags={"互动功能管理"})
public class GnsInteractionStatisticController {
    @Autowired
    private InteractionStatisticService interactionStatisticService;


    /**
     * 统计 解说/分享/打卡/点赞/留影/VR/视频
     * @return
     */
    @GetMapping("/save")
    @ApiOperation("h5 点击 解说/分享/打卡/点赞/留影/VR/视频 时调用")
    public MessageBean save(@ApiParam(name = "schoolId",value = "学校id")@RequestParam(name = "schoolId")Integer schoolId,
                                 @ApiParam(name = "name",value = "点击功能名称(解说/分享/打卡/点赞/留影/VR/视频)")@RequestParam(name = "name")String  name){
        return MessageBean.ok(interactionStatisticService.save(schoolId,name));

    }

}
