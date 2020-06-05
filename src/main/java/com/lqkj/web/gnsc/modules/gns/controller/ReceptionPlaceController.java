package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionPlace;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.gns.service.ReceptionPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 迎新接待点管理
 */

@RestController
@CrossOrigin
@Api(value = "迎新接待点controller", tags = "迎新接待点")
@RequestMapping("/receptionPlace")
public class ReceptionPlaceController {
    @Autowired
    private ReceptionPlaceService receptionPlaceService;
    @Autowired
    private FileUploadService fileUploadService;

    private static String FILE_UPLOAD_FOLDER = "receptionPlace";

    /**
     * H5获取迎新接待点列表
     * @param typeCode
     * @return
     */
    @ApiOperation("H5获取迎新接待点列表")
    @GetMapping("/list")
    public MessageListBean queryList(@ApiParam(name = "typeCode",value = "接待点分类ID")@RequestParam("typeCode") Integer typeCode){

        return MessageListBean.ok(receptionPlaceService.queryList(typeCode));
    }

    /**
     * 获取迎新接待点列表分页
     * @param typeCode
     * @param campusCode
     * @return
     */
    @ApiOperation("获取迎新接待点列表分页")
    @GetMapping("/pageQuery")
    public MessageBean pageQuery(@ApiParam(name="schoolId",value="学校ID",required=false) @RequestParam(name = "schoolId") Integer schoolId,
                                 @ApiParam(name="typeCode",value="接待点分类ID，全部（0）",required=false) @RequestParam(name = "typeCode", required = false, defaultValue = "0") Integer typeCode,
                                 @ApiParam(name="title",value="接待点名称",required=false) @RequestParam(name = "title", required = false, defaultValue = "") String title,
                                 @ApiParam(name="campusCode",value="校区区域组ID，全部（0）",required=false) @RequestParam(name = "campusCode", required = false, defaultValue = "0") Integer campusCode,
                                 @ApiParam(name="page",value="页码",required=true) @RequestParam("page") Integer page,
                                 @ApiParam(name="pageSize",value="每页数据条数",required=true) @RequestParam("pageSize") Integer pageSize){

        return MessageBean.ok(receptionPlaceService.page(schoolId,campusCode,typeCode,title,page,pageSize));
    }

    /**
     * 添加迎新接待点
     * @param receptionPlace 迎新接待点对象
     * @return
     */
    @ApiOperation("添加迎新接待点")
    @PostMapping("/add")
    public MessageBean add(@ApiParam(name="receptionPlace",value="迎新接待点对象",required=true) @RequestBody GnsReceptionPlace receptionPlace){

        return receptionPlaceService.add(receptionPlace);
    }

    /**
     * 获取迎新接待点
     * @param replaceId 迎新接待点ID
     * @return
     */
    @ApiOperation("根据ID获取迎新接待点")
    @GetMapping("/detail")
    public MessageBean get(@ApiParam(name="replaceId",value="迎新接待点ID",required=true) @RequestParam(name = "replaceId", required = true) Integer replaceId){

        return MessageBean.ok(receptionPlaceService.get(replaceId));
    }

    /**
     * 更新迎新接待点
     * @param receptionPlace 迎新接待点对象
     * @return
     */
    @ApiOperation("更新迎新接待点")
    @PostMapping("/update")
    public MessageBean update(@ApiParam(name="receptionPlace",value="迎新接待点对象",required=true) @RequestBody GnsReceptionPlace receptionPlace){

        return MessageBean.ok(receptionPlaceService.update(receptionPlace));
    }

    /**
     * 删除迎新接待点
     * @param replaceId 迎新接待点ID
     * @return
     */
    @ApiOperation("根据ID删除迎新接待点")
    @DeleteMapping("/delete")
    public MessageBean delete(@ApiParam(name="replaceId",value="迎新接待点ID",required=true) @RequestParam(name = "replaceId", required = true) Integer replaceId){

        return MessageBean.ok(receptionPlaceService.delete(replaceId));
    }

    /**
     * 批量删除迎新接待点
     * @param ids 迎新接待点ID，多个以','分隔
     * @return
     */
    @ApiOperation("批量删除迎新接待点")
    @DeleteMapping("/bulkDelete")
    public MessageBean bulkDelete(@ApiParam(name="ids",value="迎新接待点ID，多个以','分隔",required=true) @RequestParam(name = "ids", required = true) String ids){

        return MessageBean.ok(receptionPlaceService.bulkDelete(ids));
    }

    /**
     * 导出
     * @throws IOException IO异常
     */
    @ApiOperation("导出")
    @GetMapping("/info/download")
    public ResponseEntity<InputStreamResource> download(@ApiParam(name="schoolId",value="学校ID",required=true) @RequestParam(name = "schoolId", required = true) Integer schoolId) throws IOException{
        return receptionPlaceService.download(schoolId);
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

        return MessageBean.ok(receptionPlaceService.upload(
                fileUploadService.uploadFileReturnInputStream(file, FILE_UPLOAD_FOLDER),schoolId));
    }

}
