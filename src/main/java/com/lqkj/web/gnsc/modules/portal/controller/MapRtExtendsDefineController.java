package com.lqkj.web.gnsc.modules.portal.controller;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.portal.model.MapRtExtendsDefine;
import com.lqkj.web.gnsc.modules.portal.model.MapRtExtendsDefinePK;
import com.lqkj.web.gnsc.modules.portal.service.MapRtExtendsDefineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间类型-扩展属性定义controller
 * @author RY
 * @version 1.0
 * @since 2018-12-10 11:33:52
 */

@RestController
@RequestMapping("/mapRtExtendsDefine")
@Api(tags = "portal-房间类型-扩展属性定义")
public class MapRtExtendsDefineController {
    @Autowired
    MapRtExtendsDefineService mapRtExtendsDefineService;

    /**
     * 根据分类编号获取扩展属性列表
     * @param typeCode 分类编号
     * @return
     */
    @GetMapping("/list/{typeCode}")
    @ApiOperation("根据分类编号获取扩展属性列表")
    public MessageListBean<MapRtExtendsDefine> list(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageListBean.construct(mapRtExtendsDefineService.queryList(typeCode),"根据分类编号获取扩展属性列表");
    }

    /**
     * 批量保存扩展属性
     * @param mapRtExtendsDefineList 扩展属性列表
     * @return
     */
    @PutMapping("/save")
    @ApiOperation("批量保存扩展属性")
    public MessageBean<Integer> save(@RequestBody List<MapRtExtendsDefine> mapRtExtendsDefineList){
        return MessageBean.construct(mapRtExtendsDefineService.bulkSave(mapRtExtendsDefineList),"批量保存扩展属性");
    }

    /**
     * 删除
     * @param mapRtExtendsDefinePK 扩展属性主键
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public MessageBean<Integer> delete(@RequestBody MapRtExtendsDefinePK mapRtExtendsDefinePK){
        return MessageBean.construct(mapRtExtendsDefineService.delete(mapRtExtendsDefinePK),"删除");
    }

    /**
     * 批量删除
     * @param mapRtExtendsDefinePKList 扩展属性主键列表
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestBody List<MapRtExtendsDefinePK> mapRtExtendsDefinePKList){
        return MessageBean.construct(mapRtExtendsDefineService.bulkDelete(mapRtExtendsDefinePKList),"批量删除");
    }
}
