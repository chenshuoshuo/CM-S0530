package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.portal.model.MapPtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapPtExtendsDefinePK;
import com.lqkj.web.gnsc.modules.portal.service.MapPtExtendsDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 点标注扩展属性定义controller
 * @version 1.0
 * @author RY
 * @since 2018-12-12 16:09:31
 */

@RestController
@RequestMapping("/mapPtExtendsDefine")
@Api(tags = "portal-点标注扩展属性定义")
public class MapPtExtendsDefineController {
    @Autowired
    MapPtExtendsDefineService mapPtExtendsDefineService;

    /**
     * 根据分类编号获取扩展属性列表
     * @param typeCode 点标注类型代码
     * @return
     */
    @RequestMapping("/list/{typeCode}")
    @ApiOperation("根据分类编号获取扩展属性列表")
    public MessageListBean<MapPtExtendsDefine> list(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageListBean.construct(mapPtExtendsDefineService.queryList(typeCode),"根据分类编号获取扩展属性列表");
    }

    /**
     * 批量保存扩展属性
     * @param mapPtExtendsDefineList 扩展属性列表
     * @return
     */
    @PutMapping("/save")
    @ApiOperation("批量保存扩展属性")
    public MessageBean<Integer> save(@RequestBody List<MapPtExtendsDefine> mapPtExtendsDefineList){
        return MessageBean.construct(mapPtExtendsDefineService.bulkSave(mapPtExtendsDefineList),"批量保存扩展属性");
    }

    /**
     * 删除
     * @param mapPtExtendsDefinePK 扩展属性主键
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public MessageBean<Integer> delete(@RequestBody MapPtExtendsDefinePK mapPtExtendsDefinePK){
        return MessageBean.construct(mapPtExtendsDefineService.delete(mapPtExtendsDefinePK),"删除");
    }

    /**
     * 批量删除
     * @param mapPtExtendsDefinePKList 扩展属性主键列表
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestBody List<MapPtExtendsDefinePK> mapPtExtendsDefinePKList){
        return MessageBean.construct(mapPtExtendsDefineService.bulkDelete(mapPtExtendsDefinePKList),"批量删除");
    }
}
