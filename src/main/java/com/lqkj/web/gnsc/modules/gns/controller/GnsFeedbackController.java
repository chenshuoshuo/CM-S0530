package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈
 */

@RestController
@RequestMapping("/feedback")
@Api(value = "反馈管理", tags = {"意见反馈"})
public class GnsFeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id(必须)", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "反馈内容", paramType = "query")
    })
    @PostMapping("/save")
    @ApiOperation("上传意见反馈")
    public MessageBean save(@RequestParam(name = "userId") String userId,
                            @RequestParam(name = "content", required = false, defaultValue = "") String content) {

        return feedbackService.save(userId, content);
    }
}
