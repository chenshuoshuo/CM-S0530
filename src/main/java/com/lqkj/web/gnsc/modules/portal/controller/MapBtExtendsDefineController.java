package com.lqkj.web.gnsc.modules.portal.controller;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapBtExtendsDefinePK;
import com.lqkj.web.gnsc.modules.portal.service.MapBtExtendsDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 大楼类型-扩展属性定义controller
 * @author RY
 * @version 1.0
 * @since 2018-12-10 11:33:52
 */

@RestController
@RequestMapping("/mapBtExtendsDefine")
@Api(tags = "portal-大楼扩展属性定义")
public class MapBtExtendsDefineController {
    @Autowired
    MapBtExtendsDefineService mapBtExtendsDefineService;

    /**
     * 根据分类编号获取扩展属性列表
     * @param typeCode
     * @return
     */
    @ApiOperation("根据分类编号获取扩展属性")
    @GetMapping("/list/{typeCode}")
    public MessageListBean list(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageListBean.ok(mapBtExtendsDefineService.queryList(typeCode));
    }

    /**
     * 批量保存扩展属性
     * @param mapBtExtendsDefineList
     * @return
     */
    @PutMapping("/save")
    @ApiOperation("批量保存扩展属性")
    public MessageBean save(@RequestBody List<MapBtExtendsDefine> mapBtExtendsDefineList){
        return MessageBean.ok(mapBtExtendsDefineService.bulkSave(mapBtExtendsDefineList));
    }

    /**
     * 删除
     * @param mapBtExtendsDefinePK
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public MessageBean delete(@RequestBody MapBtExtendsDefinePK mapBtExtendsDefinePK){
        return MessageBean.ok(mapBtExtendsDefineService.delete(mapBtExtendsDefinePK));
    }

    /**
     * 批量删除
     * @param mapBtExtendsDefinePKList
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean  bulkDelete(@RequestBody List<MapBtExtendsDefinePK> mapBtExtendsDefinePKList){
        return MessageBean.ok(mapBtExtendsDefineService.bulkDelete(mapBtExtendsDefinePKList));
    }

}
