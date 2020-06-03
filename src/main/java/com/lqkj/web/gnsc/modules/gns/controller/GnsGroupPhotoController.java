package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsGroupPhoto;
import com.lqkj.web.gnsc.modules.gns.service.GnsGroupPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cs
 * @Date 2020/6/2 9:52
 * @Version 2.2.2.0
 **/
@RestController
@RequestMapping("/photo")
@Api(tags = "留影管理")
public class GnsGroupPhotoController {

    @Autowired
    private GnsGroupPhotoService photoService;

    @PostMapping("/save")
    @ApiOperation("上传留影")
    public MessageBean save(@ApiParam(name = "userCode",value = "用户ID")@RequestParam(name = "userCode")String userCode,
                            @ApiParam(name = "mapCode",value = "地标ID，地标标签传入pointCode的值,其他为mapCode的值")@RequestParam(name = "mapCode")Integer mapCode,
                            @ApiParam(name = "mapType",value = "地标类型（地标标签传point；大楼、房间、其他面传polygon")@RequestParam(name = "mapType") String mapType,
                            @ApiParam(name = "photoUrl",value = "图片地址")@RequestParam(name = "photoUrl")String photoUrl){

        return MessageBean.ok(photoService.save(userCode,mapCode,mapType,photoUrl));

    }
}
