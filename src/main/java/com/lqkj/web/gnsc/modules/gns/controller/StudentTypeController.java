package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import com.lqkj.web.gnsc.modules.gns.service.StudentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生类型管理
 */

@RestController
@Api(value = "学生类型controller", tags = "学生类型接口")
@RequestMapping("/studentType")
public class StudentTypeController {
    @Autowired
    private StudentTypeService studentTypeService;

    /**
     * 获取迎新接待点列表
     * @param schoolId
     * @return
     */
    @ApiOperation("h5获取学生类型列表")
    @GetMapping("/list/{schoolId}")
    public MessageBean loadStudentTypeWithGuide(@ApiParam(name="schoolId",value="学校id",required=true) @PathVariable("schoolId") Integer schoolId){

        return MessageBean.ok(studentTypeService.loadStudentTypeWithGuide(schoolId));
    }

    /**
     * 获取迎新接待点列表
     * @param schoolId
     * @return
     */
    @ApiOperation("后台获取学生类型列表")
    @GetMapping("/getAll/{schoolId}")
    public MessageBean loadEnrollment(@ApiParam(name="schoolId",value="学校id",required=true) @PathVariable("schoolId") Integer schoolId){

        return MessageBean.ok(studentTypeService.getAll(schoolId));
    }


    /**
     * 保存学生类型
     * @param studentTypeList 学生类型列表对象
     * @return
     */
    @ApiOperation("保存学生类型")
    @PostMapping("/save")
    public MessageListBean add(@ApiParam(name="studentTypeList",value="学生类型列表",required=true) @RequestBody List<GnsStudentType> studentTypeList){
        return MessageListBean.ok(studentTypeService.saveAll(studentTypeList));
    }

    /**
     * 删除学生类型
     * @return
     */
    @ApiOperation("根据ID删除学生类型")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="typeCode",value="学生类型ID",required=true) @RequestParam(name = "typeCode", required = true) Integer typeCode){
        return MessageBean.ok(studentTypeService.delete(typeCode));
    }
}
