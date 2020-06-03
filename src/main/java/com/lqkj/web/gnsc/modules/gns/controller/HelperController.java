package com.lqkj.web.gnsc.modules.gns.controller;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.service.HelperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 迎新助手（通讯录）管理
 */

@RestController
@RequestMapping("/helper")
@Api(value="迎新通讯录controller",tags={"迎新通讯录"})
public class HelperController {

    @Autowired
    private HelperService helperService;


    /**
     * H5根据分类获取列表
     * @param typeCode
     * @return
     */
    @ApiOperation("H5根据分类获取迎新通讯录")
    @GetMapping("/list")
    public MessageListBean loadEnrollment(@ApiParam(name="typeCode",value="分类id",required=true) @RequestParam("typeCode") Integer typeCode){

        return MessageListBean.ok(helperService.queryList(typeCode));
    }

    /**
     * 获取通讯录分页
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */

    @ApiOperation("获取通讯录信息分页")
    @GetMapping("/page")
    public MessageBean pageQuery(@ApiParam(name="typeCode",value="分类编号，全部（0）",required=true,defaultValue = "0") @RequestParam(name = "typeCode", required = true) Integer typeCode,
                            @ApiParam(name="title",value="名称",required=false) @RequestParam(name = "title", required = false) String title,
                            @ApiParam(name="page",value="页码",required=true) @RequestParam(name = "page", required = true) Integer page,
                            @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam(name = "pageSize", required = true) Integer pageSize){

        return MessageBean.ok(helperService.page(typeCode,title,page,pageSize));
    }

    /**
     * 添加通讯录信息
     * @param helper 通讯录信息对象
     * @return
     */
    @ApiOperation("添加通讯录信息")
    @PostMapping("/add")
    public MessageBean add(@ApiParam(name="helper",value="通讯录信息对象",required=true) @RequestBody GnsHelper helper){

        return helperService.add(helper);
    }

    /**
     * 根据ID获取通讯录信息
     * @param helperId 通讯录信息ID
     * @return
     */
    @ApiOperation("根据ID获取通讯录信息")
    @GetMapping("/get")
    public MessageBean get(@ApiParam(name="helperId",value="通讯录信息ID",required=true) @RequestParam(name = "helperId", required = true) Integer helperId){

        return MessageBean.ok(helperService.get(helperId));
    }

    /**
     * 更新通讯录信息
     * @param helper 通讯录信息
     * @return
     */
    @ApiOperation("更新通讯录信息")
    @PostMapping("/update")
    public MessageBean update(@ApiParam(name="helper",value="通讯录信息",required=true) @RequestBody GnsHelper helper){

        return MessageBean.ok(helperService.update(helper));
    }

    /**
     * 删除通讯录信息
     * @param helperId 通讯录信息ID
     * @return
     */
    @ApiOperation("根据ID删除通讯录信息")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="helperId",value="通讯录信息ID",required=true) @RequestParam(name = "helperId", required = true) Integer helperId){

        return MessageBean.ok(helperService.delete(helperId));
    }

    /**
     * 批量删除通讯录信息
     * @param ids 通讯录信息ID，多个以','分隔
     * @return
     */
    @ApiOperation("批量删除通讯录信息")
    @DeleteMapping("/bulkDelete")
    public MessageBean bulkDelete(@ApiParam(name="ids",value="通讯录信息ID，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){
        return MessageBean.ok(helperService.bulkDelete(ids));
    }

}
