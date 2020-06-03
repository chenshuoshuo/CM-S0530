package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import com.lqkj.web.gnsc.modules.gns.service.ReceptionTypeService;
import com.lqkj.web.gnsc.modules.gns.service.StudentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接待点分类
 */

@RestController
@Api(value = "接待点分类controller", tags = "迎新接待点分类")
@RequestMapping("/receptionType")
public class ReceptionTypeController {
    @Autowired
    private ReceptionTypeService receptionTypeService;

    /**
     * 获取迎新接待点列表
     * @param schoolId
     * @return
     */
    @ApiOperation("h5/后台获取迎新接待点分类列表")
    @GetMapping("/list")
    public MessageBean loadStudentTypeWithGuide(@ApiParam(name="schoolId",value="学校id",required=true) @RequestParam("schoolId") Integer schoolId){

        return MessageBean.ok(receptionTypeService.getAll(schoolId));
    }

    /**
     * 保存学迎新接接待点分类
     * @return
     */
    @ApiOperation("保存迎新接待点分类")
    @PostMapping("/save")
    public MessageListBean add(@ApiParam(name="receptionTypeList",value="接待点分类",required=true) @RequestBody List<GnsReceptionType> receptionTypeList){
        return MessageListBean.ok(receptionTypeService.saveAll(receptionTypeList));
    }

    /**
     * 删除迎新接接待点分类
     * @return
     */
    @ApiOperation("根据ID删除迎新接待点分类")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="typeCode",value="接待点分类ID",required=true) @RequestParam(name = "typeCode", required = true) Integer typeCode){
        return MessageBean.ok(receptionTypeService.delete(typeCode));
    }
}
