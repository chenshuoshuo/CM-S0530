package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsTourRoute;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsTourPointVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsTourRouteForm;
import com.lqkj.web.gnsc.modules.gns.service.GnsPushMessageService;
import com.lqkj.web.gnsc.modules.gns.service.GnsTourRouteService;
import com.lqkj.web.gnsc.modules.handler.WebSocketPushHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户位置上传
 */

@RestController
@RequestMapping("/tourRoute")
@Api(value="推荐游览路线管理",tags={"推荐游览路线管理"})
public class GnsTourRouteController {
    @Autowired
    private GnsTourRouteService tourRouteService;
    @Autowired
    private WebSocketPushHandler webSocketPushHandler;


    /**
     * h5根据校区获取游览路线列表
     * @param campusCode
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("h5根据校区获取游览路线列表")
    public MessageListBean list(@ApiParam(name = "campusCode",value = "校区区域组ID")@RequestParam(name = "campusCode")Integer campusCode){

        return MessageListBean.ok(tourRouteService.findByCampusCode(campusCode));
    }

    /**
     * h5根据校区获取游览点位列表
     * @return
     */
    @GetMapping("/listTourPoint")
    @ApiOperation("h5获取游览点位列表")
    public MessageBean listTourPoint(@ApiParam(name = "routeId",value = "路线编号")@RequestParam(name = "routeId")Integer routeId){

        return MessageBean.ok(tourRouteService.getRouteDetail(routeId));
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @ApiOperation("游览路线分页")
    public MessageBean page(@ApiParam(name = "campusCode",value = "校区区域组ID",required = true,defaultValue = "0")@RequestParam(name = "campusCode",defaultValue = "0")Integer campusCode,
                            @ApiParam(name = "routeName",value = "路线名称")@RequestParam(name = "routeName",required = false)String routeName,
                            @ApiParam(name = "page",value = "页码")@RequestParam(name = "page")Integer page,
                            @ApiParam(name = "pageSize",value = "条数")@RequestParam(name = "pageSize")Integer pageSize){

        return MessageBean.ok(tourRouteService.page(campusCode,routeName,page,pageSize));

    }

    /**
     * 根据主键获取
     */
    @GetMapping("/get")
    @ApiOperation("根据主键获取")
    public MessageBean get(@ApiParam(name = "routeId",value = "id")@RequestParam(name = "routeId") Integer routeId){

        return MessageBean.ok(tourRouteService.get(routeId));

    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ApiOperation("新增游览路线")
    public MessageBean add(@ApiParam(name = "tourRouteForm",value = "游览路线对象")@RequestBody GnsTourRouteForm tourRouteForm){

        return MessageBean.ok(tourRouteService.add(tourRouteForm));

    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @ApiOperation("更新游览路线")
    public MessageBean update(@ApiParam(name = "tourRouteForm",value = "游览路线对象")@RequestBody GnsTourRouteForm tourRouteForm){

        return MessageBean.ok(tourRouteService.update(tourRouteForm));

    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public MessageBean delete(@ApiParam(name = "routeId",value = "id")@RequestParam(name = "routeId") Integer routeId){

        return MessageBean.ok(tourRouteService.delete(routeId));

    }

    /**
     * 删除
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("删除")
    public MessageBean bulkDelete(@ApiParam(name = "ids",value = "id,多个以逗号隔开")@RequestParam(name = "ids") String ids){

        return MessageBean.ok(tourRouteService.bulkDelete(ids));

    }

}
