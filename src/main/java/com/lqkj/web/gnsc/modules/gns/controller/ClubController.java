package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import com.lqkj.web.gnsc.modules.gns.service.ClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 校园社团管理
 * @author cs
 * @since 2019/5/9
 */
@RestController
@RequestMapping("/club")
@Api(value="校园社团管理",tags={"迎新校园社团"})
public class ClubController {
    @Autowired
    private ClubService clubService;

    /**
     * 根据校区获取校园社团列表
     * @return
     */
    @ApiOperation("根据校区获取校园社团列表")
    @GetMapping("/list")
    public MessageListBean loadClub(@ApiParam(name="campusCode",value = "校区区域组ID",required = true) @RequestParam("campusCode") Integer campusCode){

        return MessageListBean.ok(clubService.queryList(campusCode));
    }
    /**
     * 根据ID获取校园社团详情
     * @param clubId
     * @return
     */
    @ApiOperation("H5获取校园社团详情")
    @GetMapping("/get")
    public MessageBean get(@ApiParam(name="clubId",value="H5获取校园社团详情",required=true) @RequestParam(name = "clubId", required = true) Integer clubId){
        return MessageBean.ok(clubService.get(clubId));
    }

    /**
     * 获取校园社团信息分页
     * @param clubName 社团名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */

    @ApiOperation("获取校园社团信息分页")
    @GetMapping("/page")
    public MessageBean pageQuery(@ApiParam(name = "campusCode", value = "校区区域组ID，全部（0）", required = true) @RequestParam(name = "campusCode", required = true, defaultValue = "0") Integer campusCode,
            @ApiParam(name="clubName",value="校团名称",required=false) @RequestParam(name = "clubName", required = false, defaultValue = "") String clubName,
            @ApiParam(name="page",value="页码",required=true) @RequestParam(name = "page", required = true) Integer page,
            @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam(name = "pageSize", required = true) Integer pageSize){

        return MessageBean.ok(clubService.page(campusCode,clubName,page,pageSize));
    }
    /**
     * 添加校园社团信息
     * @param club 社团对象信息
     * @return
     */
    @ApiOperation("添加校园社团信息")
    @PostMapping("/add")
    public MessageBean add(@ApiParam(name="club",value="社团对象信息",required=true) @RequestBody GnsClub club){

        return clubService.add(club);
    }

    /**
     * 更新校园社团信息
     * @param club 校园社团信息
     * @return
     */
    @ApiOperation("更新校园社团信息")
    @PostMapping("/update")
    public MessageBean update(@ApiParam(name="club",value="校园社团信息",required=true) @RequestBody GnsClub club){

        return MessageBean.ok(clubService.update(club));
    }

    /**
     * 删除校园社团信息
     * @param clubId 社团ID
     * @return
     */
    @ApiOperation("删除校园社团信息")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="clubId",value="社团ID",required=true) @RequestParam(name = "clubId", required = true) Integer clubId){

        return MessageBean.ok(clubService.delete(clubId));
    }

    /**
     * 批量删除社团信息
     * @param ids 社团信息ID，多个以','分隔
     * @return
     */
    @ApiOperation("批量删除社团信息")
    @DeleteMapping("/bulkDelete")
    public MessageBean bulkDelete(@ApiParam(name="ids",value="社团信息ID，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){

        return MessageBean.ok(clubService.bulkDelete(ids));
    }
}
