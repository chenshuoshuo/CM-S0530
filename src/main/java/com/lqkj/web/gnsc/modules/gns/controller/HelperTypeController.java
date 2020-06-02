package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelperType;
import com.lqkj.web.gnsc.modules.gns.service.HelperTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 迎新助手（通讯录）分类管理
 */

@RestController
@RequestMapping("/helperType")
@Api(value="迎新通讯录分类controller",tags={"迎新通讯录分类"})
public class HelperTypeController {

    @Autowired
    private HelperTypeService helperTypeService;


    /**
     * H5获取分类列表
     * @param schoolId
     * @return
     */
    @ApiOperation("H5/后台获取迎新通讯录分类列表")
    @GetMapping("/list")
    public MessageListBean list(@ApiParam(name="schoolId",value="学校id",required=true) @RequestParam("schoolId") Integer schoolId){

        return MessageListBean.ok(helperTypeService.queryList(schoolId));
    }

    /**
     * 保存迎新助手分类
     * @param helperTypeList 迎新助手分类对象
     * @return
     */
    @ApiOperation("保存迎新通讯录分类分类")
    @PostMapping("/save")
    public MessageListBean add(@ApiParam(name="helperTypeList",value="迎新通讯录分类列表",required=true) @RequestBody List<GnsHelperType> helperTypeList){

        return MessageListBean.ok(helperTypeService.saveAll(helperTypeList));
    }

    /**
     * 删除迎新助手分类
     * @param typeCode 迎新助手分类ID
     * @return
     */
    @ApiOperation("根据ID删除迎新通讯录分类")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="typeCode",value="迎新通讯录分类",required=true) @RequestParam(name = "typeCode", required = true) Integer typeCode){

        return MessageBean.ok(helperTypeService.delete(typeCode));
    }

}
