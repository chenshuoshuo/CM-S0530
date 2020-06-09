package com.lqkj.web.gnsc.modules.portal.controller;


import com.lqkj.web.gnsc.modules.portal.model.MapBuildingExtends;
import com.lqkj.web.gnsc.modules.portal.service.MapBuildingExtendsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 大楼扩展属性-值controller
 * @version 1.0
 * @author RY
 * @since 2018-12-10 19:02:43
 */

@RestController
@RequestMapping("/mapBuildingExtends")
@Api(tags = "portal-大楼扩展属性-值")
public class MapBuildingExtendsController {
    @Autowired
    MapBuildingExtendsService mapBuildingExtendsService;


    /**
     * 根据大楼编号
     * 获取扩展属性列表
     * @param buildingCode
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据大楼编号获取扩展属性列表")
    public List<MapBuildingExtends> queryList(@RequestParam(name = "buildingCode") Integer buildingCode){
        return mapBuildingExtendsService.queryList(buildingCode);
    }

    /**
     * 批量更新
     * @param mapBuildingExtendsList
     * @return
     */
    @PutMapping("/bulkUpdate")
    @ApiOperation("批量更新")
    public Integer bulkUpdate(@RequestBody List<MapBuildingExtends> mapBuildingExtendsList){
        return mapBuildingExtendsService.save(mapBuildingExtendsList);
    }
}
