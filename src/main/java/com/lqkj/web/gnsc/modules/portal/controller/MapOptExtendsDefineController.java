package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapOptExtendsDefinePK;
import com.lqkj.web.gnsc.modules.portal.service.MapOptExtendsDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 其他面图源类型-扩展属性定义controller
 * @author RY
 * @version 1.0
 * @since 2018-12-10 11:33:52
 */

@RestController
@RequestMapping("/mapOptExtendsDefine")
@Api(tags = "portal-其他面图源类型-扩展属性定义")
public class MapOptExtendsDefineController {
    @Autowired
    MapOptExtendsDefineService mapOptExtendsDefineService;

    /**
     * 根据分类编号获取扩展属性列表
     * @param typeCode 其他面图源分类代码
     * @return
     */
    @GetMapping("/list/{typeCode}")
    @ApiOperation("根据分类编号获取扩展属性列表")
    public MessageListBean<MapOptExtendsDefine> list(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageListBean.construct(mapOptExtendsDefineService.queryList(typeCode),"根据分类编号获取扩展属性列表");
    }

    /**
     * 批量保存扩展属性
     * @param mapOptExtendsDefineList 扩展属性列表
     * @return
     */
    @PutMapping("/save")
    @ApiOperation("批量保存扩展属性")
    public MessageBean<Integer> save(@RequestBody List<MapOptExtendsDefine> mapOptExtendsDefineList){
        return MessageBean.construct(mapOptExtendsDefineService.bulkSave(mapOptExtendsDefineList),"批量保存扩展属性");
    }

    /**
     * 删除
     * @param mapOptExtendsDefinePK 扩展属性主键
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public MessageBean<Integer> delete(@RequestBody MapOptExtendsDefinePK mapOptExtendsDefinePK){
        return MessageBean.construct(mapOptExtendsDefineService.delete(mapOptExtendsDefinePK),"删除");
    }

    /**
     * 批量删除
     * @param mapOptExtendsDefinePKList 扩展属性主键列表
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestBody List<MapOptExtendsDefinePK> mapOptExtendsDefinePKList){
        return MessageBean.construct(mapOptExtendsDefineService.bulkDelete(mapOptExtendsDefinePKList),"批量删除");
    }
}
