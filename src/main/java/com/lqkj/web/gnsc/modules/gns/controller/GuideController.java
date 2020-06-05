package com.lqkj.web.gnsc.modules.gns.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsGuide;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.gns.service.GuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 迎新引导管理
 * @version 1.0
 * @author RY
 * @since 2018-6-2 11:38:40
 */

@RestController
@CrossOrigin
@RequestMapping("/guide")
@Api(value = "迎新引导controller", tags = "迎新引导")
public class GuideController {
    @Autowired
    private GuideService guideService;
    @Autowired
    private FileUploadService fileUploadService;

    private static String FILE_UPLOAD_FOLDER = "guide";

    @ApiOperation("H5获取迎新引导列表")
    @GetMapping("/list")
    public MessageListBean loadListWithCampusStudentType( @ApiParam(name = "campusCode",value = "校区区域组ID")@RequestParam(name = "campusCode") Integer campusCode,
                                                          @ApiParam(name = "typeCode",value = "学生类型ID")@RequestParam(name = "typeCode") Integer typeCode){

        return MessageListBean.ok(guideService.queryAll(campusCode,typeCode));

    }

    /**
     * 获取详情
     * @return
     */
    @ApiOperation("根据主键获取")
    @GetMapping("/detail")
    public MessageBean loadGuideDetail(@ApiParam(name="guideId",value="迎新引导信息ID",required=true) @RequestParam("guideId") Integer guideId){
        return MessageBean.ok(guideService.get(guideId));
    }

    /**
     * 获取迎新引导分页
     * @param campusCode 校区ID，全部（0）
     * @param typeCode 学生类型，全部（0）
     * @param title 标题
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @ApiOperation("迎新引导信息分页")
    @GetMapping("/page")
    public MessageBean pageQuery(   @ApiParam(name = "schoolId", value = "学校ID", required = true) @RequestParam(name = "schoolId") Integer schoolId,
                             @ApiParam(name = "campusCode", value = "校区区域组ID，全部（0）", required = true) @RequestParam(name = "campusCode", required = false, defaultValue = "0") Integer campusCode,
                             @ApiParam(name="typeCode",value="学生类型，全部（0）",required=true) @RequestParam(name = "typeCode", required = false, defaultValue = "0") Integer typeCode,
                             @ApiParam(name="title",value="标题",required=false) @RequestParam(name = "title", required = false) String title,
                             @ApiParam(name="page",value="页码",required=true) @RequestParam(name = "page", required = true) Integer page,
                             @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam(name = "pageSize", required = true) Integer pageSize){

        return MessageBean.ok(guideService.page(schoolId,campusCode,typeCode,title,page,pageSize));
    }

    /**
     * 添加迎新引导信息
     * @param guide 迎新引导信息对象
     * @return
     */
    @ApiOperation("添加迎新引导信息")
    @PostMapping("/add")
    public MessageBean add(@ApiParam(name="guide",value="迎新引导信息对象",required=true) @RequestBody GnsGuide guide){

        return MessageBean.ok(guideService.add(guide));
    }

    /**
     * 更新迎新引导信息
     * @param guide 迎新引导信息
     * @return
     */
    @ApiOperation("更新迎新引导信息")
    @PostMapping("/update")
    public MessageBean update(@ApiParam(name="guide",value="迎新引导信息",required=true) @RequestBody GnsGuide guide){
       return MessageBean.ok(guideService.update(guide));
    }

    /**
     * 删除迎新引导信息
     * @param guideId 迎新引导信息ID
     * @return
     */
    @ApiOperation("根据ID删除迎新引导信息")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="guideId",value="迎新引导信息ID",required=true) @RequestParam(name = "guideId", required = true) Integer guideId){
       return MessageBean.ok(guideService.delete(guideId));
    }

    /**
     * 批量删除迎新引导信息
     * @param ids 迎新引导信息ID，多个以','分隔
     * @return
     */
    @ApiOperation("批量删除迎新引导信息")
    @DeleteMapping("/bulkDelete")
    public MessageBean bulkDelete(@ApiParam(name="ids",value="迎新引导信息ID，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){
       return MessageBean.ok(guideService.bulkDelete(ids));
    }

    /**
     * 下载导入模板
     * @throws IOException IO异常
     */
    @ApiOperation("下载导入模板")
    @GetMapping("/info/downloadTemplate")
    public ResponseEntity<InputStreamResource> exportTemplate(@ApiParam(name="schoolId",value="学校ID",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId)
            throws IOException{
        return guideService.exportTemplate(schoolId);
    }

    /**
     * 导出
     * @throws IOException IO异常
     */
    @ApiOperation("导出")
    @GetMapping("/info/download")
    public ResponseEntity<InputStreamResource> download(@ApiParam(name="schoolId",value="学校ID",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId) throws IOException{
        return guideService.download(schoolId);
    }

    /**
     * 导入
     */
    @PostMapping("/info/upload")
    @ApiOperation("导入")
    public MessageBean upload(MultipartFile file, @ApiParam(name="schoolId",value="学校ID",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException,
            IOException{

        return MessageBean.ok(guideService.upload(
                fileUploadService.uploadFileReturnInputStream(file, FILE_UPLOAD_FOLDER),schoolId));
    }


}
