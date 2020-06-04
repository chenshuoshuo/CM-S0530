package com.lqkj.web.gnsc.modules.gns.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lqkj.web.gnsc.message.MessageBaseBean;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsDisplayPointType;
import com.lqkj.web.gnsc.modules.gns.service.PointTypeService;
import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.message.Message;

import java.util.List;

/**
 * 点标注分类信息管理
 */

@RestController
@RequestMapping("/pointCategory")
@Api(value="地标分类信息controller",tags={"地标管理"})
public class PointTypeController {
    @Autowired
    private PointTypeService pointTypeService;


    /**
     * 后台管理分页
     */
    @ApiOperation("后台分页获取地标")
    @GetMapping("/page")
    public MessageBean pageQuery(@ApiParam(name="schoolId",value="学校ID",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId,
            @ApiParam(name="campusCode",value="校区区域组ID，全部（0）",required=true,defaultValue = "0") @RequestParam(name = "campusCode", required = true) Integer campusCode,
                                 @ApiParam(name="pointName",value="名称",required=false) @RequestParam(name = "pointName", required = false) String pointName,
                                 @ApiParam(name="page",value="页码",required=true) @RequestParam(name = "page", required = true) Integer page,
                                 @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam(name = "pageSize", required = true) Integer pageSize){

        return MessageBean.ok(pointTypeService.page(schoolId,campusCode,pointName,page,pageSize));
    }

    /**
     * 后台设置是否热门
     * @return
     */
    @ApiOperation("后台设置是否热门地标(开启/禁用)")
    @PostMapping("/updateOpen")
    public MessageBean save(@ApiParam(name = "pointCode", value = "地标编号", required = true)@RequestParam(name = "pointCode") Integer pointCode,
                            @ApiParam(name = "open", value = "开启/禁用", required = true)@RequestParam(name = "open") Boolean open){

        return MessageBean.ok(pointTypeService.open(pointCode,open));
    }

    /**
     * 后台编辑点击量
     * @return
     */
    @ApiOperation("后台编辑点击量")
    @PostMapping("/updateThumpsUp")
    public MessageBean updateThumpsUp(@ApiParam(name = "pointCode", value = "地标编号", required = true)@RequestParam(name = "pointCode") Integer pointCode,
                            @ApiParam(name = "thumpsUpCount", value = "点赞数", required = true)@RequestParam(name = "thumpsUpCount") Integer thumpsUpCount){

        return MessageBean.ok(pointTypeService.updateThumpsUp(pointCode,thumpsUpCount));
    }

    /**
     * 批量保存默认地标分类
     * @return
     */
    @ApiOperation("批量保存默认地标分类")
    @PostMapping("/save")
    public MessageListBean save(@ApiParam(name = "pointCategoryList", value = "分类信息列表", required = true)@RequestBody List<GnsDisplayPointType> pointTypeList){

        return pointTypeService.add(pointTypeList);
    }

    /**
     * 根据学校获取地标分类
     * @param schoolId
     * @return
     */
    @ApiOperation("h5获取地标分类")
    @GetMapping("/typeList")
    public MessageListBean queryList(
            @ApiParam(name="schoolId",value="学校id",required=true)@RequestParam(name = "schoolId", required = true) Integer schoolId){

        return MessageListBean.ok(pointTypeService.queryList(schoolId));
    }

    /**
     * 获取指定地标分类下列表
     */
    @ApiOperation("h5获取地标列表")
    @GetMapping("/pointList")
    public MessageBean pointList(@ApiParam(name="vectorZoomCode",value="校区二维ID",required=true)@RequestParam(name = "vectorZoomCode", required = true) Integer vectorZoomCode,
                                 @ApiParam(name="typeCode",value="地标分类编号",required=true)@RequestParam(name = "typeCode", required = true) Integer typeCode){

        return MessageBean.ok(pointTypeService.queryByType(vectorZoomCode,typeCode));
    }

    /**
     * h5获取地标详情
     */
    @ApiOperation("h5获取地标详情")
    @GetMapping("/detail")
    public MessageBean pointList(@ApiParam(name="mapCode",value="地标ID，点标注传入pointCode的值,其他为mapCode的值",required=true)@RequestParam(name = "mapCode", required = true) Integer mapCode,
                                 @ApiParam(name="mapType",value="地标类型（地标标签传point；大楼、房间、其他面传polygon）",required=true)@RequestParam(name = "mapType", required = true) String mapType){

        return MessageBean.ok(pointTypeService.queryByMapCode(mapCode,mapType));
    }


}
