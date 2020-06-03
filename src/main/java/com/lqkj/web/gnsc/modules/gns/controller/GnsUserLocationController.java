//package com.lqkj.web.gnsc.modules.gns.controller;
//
//import com.lqkj.web.gnsc.message.MessageBean;
//import com.lqkj.web.gnsc.modules.gns.service.GnsThumbsUpService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 用户位置上传
// */
//
//@RestController
//@RequestMapping("/location")
//@Api(value="用户位置上传",tags={"用户位置上传"})
//public class GnsUserLocationController {
//    @Autowired
//    private GnsThumbsUpService thumbsUpService;
//
//
//    /**
//     * 点赞
//     * @param userCode
//     * @param mapCode
//     * @param mapType
//     * @return
//     */
//    @PostMapping("/save")
//    @ApiOperation("点赞")
//    public MessageBean save(@ApiParam(name = "userCode",value = "用户ID")@RequestParam(name = "userCode")String userCode,
//                            @ApiParam(name = "mapCode",value = "地标ID，地标标签传入pointCode的值,其他为mapCode的值")@RequestParam(name = "mapCode")Integer mapCode,
//                            @ApiParam(name = "mapType",value = "地标类型（地标标签传point；大楼、房间、其他面传polygon")@RequestParam(name = "mapType") String mapType){
//
//        return MessageBean.ok(thumbsUpService.save(userCode,mapCode,mapType));
//
//    }
//
//}
