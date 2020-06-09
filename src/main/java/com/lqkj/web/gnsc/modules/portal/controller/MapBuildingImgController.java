package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.modules.portal.model.MapBuildingImg;
import com.lqkj.web.gnsc.modules.portal.service.MapBuildingImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 大楼图片controller
 * @version 1.0
 * @since 2018-12-11 13:15:15
 * @author RY
 */

@RestController
@RequestMapping("/mapBuildingImg")
public class MapBuildingImgController {
    @Autowired
    MapBuildingImgService mapBuildingImgService;

    /**
     * 根据大楼编号获取图片
     * @param buildingCode
     * @return
     */
    @GetMapping("/queryListWithBuildingCode")
    public List<MapBuildingImg> queryListWithBuildingCode(@RequestParam(name = "buildingCode") Integer buildingCode){
        return mapBuildingImgService.queryListWithBuildingCode(buildingCode);
    }

    /**
     * 保存图片
     * @param mapBuildingImgList
     * @return
     */
    @PostMapping("/saveImgList")
    public Integer saveImgList(@RequestBody List<MapBuildingImg> mapBuildingImgList){
        return mapBuildingImgService.saveImgList(mapBuildingImgList);
    }

    /**
     * 根据大楼代码删除所有图片
     * @param buildingCode 大楼代码
     * @return
     */
    @DeleteMapping("/deleteAllByBuildingCode")
    public Integer deleteAllByBuildingCode(@RequestParam(name = "buildingCode") Integer buildingCode){
        return mapBuildingImgService.deleteAllByBuildingCode(buildingCode);
    }
}
