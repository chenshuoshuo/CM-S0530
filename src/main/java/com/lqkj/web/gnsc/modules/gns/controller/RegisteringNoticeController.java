package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import com.lqkj.web.gnsc.modules.gns.service.RegisteringNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 报到须知信息管理
 */

@RestController
@RequestMapping("/registeringNotice")
@Api(value="报到须知controller",tags={"迎新报到须知"})
public class RegisteringNoticeController {

    @Autowired
    private RegisteringNoticeService registeringNoticeService;


    /**
     * 获取报到须知列表
     * @param schoolId
     * @return
     */
    @ApiOperation("h5获取报到须知列表")
    @GetMapping("/list")
    public MessageListBean loadEnrollment(@ApiParam(name="schoolId",value="学校id",required=true) @RequestParam("schoolId") Integer schoolId){

        return MessageListBean.ok(registeringNoticeService.queryList(schoolId));
    }

    /**
     * 获取详情
     * @param noticeId
     * @return
     */
    @ApiOperation("获取报到须知详情")
    @GetMapping("/detail")
    public MessageBean loadEnrollmentDetail(@ApiParam(name="noticeId",value="报到须知信息id",required=true) @RequestParam("noticeId") Integer noticeId){

        return MessageBean.ok(registeringNoticeService.get(noticeId));
    }

    /**
     * 获取报到须知分页
     * @param schoolId 学校id
     * @param title 标题
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */

    @ApiOperation("获取报到须知信息分页")
    @GetMapping("/pageQuery")
    public MessageBean pageQuery(@ApiParam(name="schoolId",value="学校id",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId,
                            @ApiParam(name="title",value="标题",required=false) @RequestParam(name = "title", required = false, defaultValue = "") String title,
                            @ApiParam(name="page",value="页码",required=true) @RequestParam(name = "page", required = true) Integer page,
                            @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam(name = "pageSize", required = true) Integer pageSize){

       return MessageBean.ok(registeringNoticeService.page(schoolId,title,page,pageSize));
    }

    /**
     * 添加报到须知信息
     * @param registeringNotice 报到须知信息对象
     * @return
     */
    @ApiOperation("添加报到须知信息")
    @PostMapping("/add")
    public MessageBean add(@ApiParam(name="registeringNotice",value="报到须知信息对象",required=true) @RequestBody GnsRegisteringNotice registeringNotice){

        return registeringNoticeService.add(registeringNotice);
    }

    /**
     * 更新报到须知信息
     * @param registeringNotice 报到须知信息
     * @return
     */
    @ApiOperation("更新报到须知信息")
    @PostMapping("/update")
    public MessageBean update(@ApiParam(name="registeringNotice",value="报到须知信息",required=true) @RequestBody GnsRegisteringNotice registeringNotice){

        return registeringNoticeService.update(registeringNotice);
    }

    /**
     * 删除报到须知信息
     * @param noticeId 报到须知信息ID
     * @return
     */
    @ApiOperation("根据ID删除报到须知信息")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="infoId",value="报到须知信息ID",required=true) @RequestParam(name = "noticeId", required = true) Integer noticeId){

        return MessageBean.ok(registeringNoticeService.delete(noticeId));
    }

    /**
     * 批量删除报到须知信息
     * @param ids 报到须知信息ID，多个以','分隔
     * @return
     */
    @ApiOperation("批量删除报到须知信息")
    @DeleteMapping("/bulkDelete")
    public MessageBean bulkDelete(@ApiParam(name="ids",value="报到须知信息ID，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){

        return MessageBean.ok(registeringNoticeService.bulkDelete(ids));
    }
}
