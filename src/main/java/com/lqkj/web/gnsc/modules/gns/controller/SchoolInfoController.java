package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsSchool;
import com.lqkj.web.gnsc.modules.gns.service.SchoolCampusService;
import com.lqkj.web.gnsc.modules.gns.service.SchoolInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 学校信息
 */

@RestController
@RequestMapping("/schoolInfo")
@Api(value = "学校信息controller", tags = "学校信息接口")
public class SchoolInfoController {

    @Autowired
    private SchoolInfoService schoolInfoService;

    /**
     * 获取学校信息列表
     * @return
     */

    @ApiOperation("获取学校信息列表")
    @GetMapping("/list")
    public MessageListBean list(){

        return MessageListBean.ok(schoolInfoService.listAll());
    }

    /**
     * 获取学校信息分页
     * @param schoolName 学校名称
     * @param schoolId 学校ID
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @ApiOperation("获取学校信息分页")
    @GetMapping("/pageQuery")
    @ResponseBody
    public MessageBean pageQuery(
                            @ApiParam(name="schoolId",value="学校ID",required=false) @RequestParam(name = "schoolId", required = false) Integer schoolId,
                            @ApiParam(name="schoolName",value="学校名称",required=false) @RequestParam(name = "schoolName", required = false) String schoolName,
                            @ApiParam(name="page",value="页码",required=true) @RequestParam(name = "page", required = true) Integer page,
                            @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam(name = "pageSize", required = true) Integer pageSize){

        return MessageBean.ok(schoolInfoService.page(schoolId,schoolName,page,pageSize));
    }
    /**
     * 添加学校信息
     * @param schoolInfo 学校信息对象
     * @return
     */
    @ApiOperation("添加学校信息")
    @PutMapping("/add")
    public MessageBean add(
            @ApiParam(name="schoolInfo",value="学校信息对象",required=true) @RequestBody GnsSchool schoolInfo,
            @ApiParam(name="ids",value="校区，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){

        return schoolInfoService.add(schoolInfo,ids);

    }

    /**
     * 根据ID获取学校信息
     * @param schoolId
     * @return
     */
    @ApiOperation("根据ID获取学校信息")
    @GetMapping("/{schoolId}")
    public MessageBean loadSchoolInfo(@ApiParam(name = "schoolId", value = "学校ID", required = true) @PathVariable("schoolId") Integer schoolId){

        return MessageBean.ok(schoolInfoService.get(schoolId));
    }

    /**
     * 获取默认学校信息
     * @return
     */
    @ApiOperation("获取默认学校信息")
    @GetMapping("/default")
    @ResponseBody
    public MessageBean loadDefaultSchoolInfo(){
        return MessageBean.ok(schoolInfoService.getDefaultSchool());
    }

    /**
     * 更新学校信息
     * @param schoolInfo
     * @return
     */
    @ApiOperation("更新学校信息")
    @PutMapping("/update")
    public MessageBean update(@ApiParam(name = "schoolInfo", value = "学校信息对象", required = true) @RequestBody GnsSchool schoolInfo){

        return MessageBean.ok(schoolInfoService.update(schoolInfo));
    }
    /**
     * 删除学校信息
     * @param schoolId 学校信息ID
     * @return
     */
    @ApiOperation("根据ID删除学校信息")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="schoolId",value="学校信息ID",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId){

        return MessageBean.ok(schoolInfoService.delete(schoolId));
    }

    /**
     * 批量删除学校信息
     * @param ids 学校信息ID，多个以','分隔
     * @return
     */
    @ApiOperation("批量删除学校信息")
    @DeleteMapping("/bulkDelete")
    public MessageBean bulkDelete(@ApiParam(name="ids",value="学校信息ID，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){

        return MessageBean.ok(schoolInfoService.bulkDelete(ids));
    }

}
