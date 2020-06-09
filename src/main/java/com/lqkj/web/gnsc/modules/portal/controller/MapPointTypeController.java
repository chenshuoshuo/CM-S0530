package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapPointType;
import com.lqkj.web.gnsc.modules.portal.service.MapPointTypeService;
import com.lqkj.web.gnsc.utils.UtilPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 点标注分类controller
 * @version 1.0
 */

@RestController
@RequestMapping("/mapPointType")
@Api(tags = "portal-点标注分类管理")
public class MapPointTypeController {
    @Autowired
    MapPointTypeService mapPointTypeService;
    @Autowired
    FileUploadService fileUploadService;

    private static String FILE_UPLOAD_FOLDER = "mapPointType";

    /**
     * 分页查询点标注类型信息
     * @param typeName 分类名称
     * @param parentTypeName 父类别代码
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean<UtilPage<MapPointType>> pageQuery(
               @RequestParam(name = "typeName", required = false) String typeName,
               @RequestParam(name = "parentTypeName", required = false) String parentTypeName,
               @RequestParam(name = "page", defaultValue = "0") Integer page,
               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return MessageBean.construct(mapPointTypeService.pageQuery(typeName, parentTypeName, page, pageSize),"分页");
    }

    /**
     * 列表查询
     * @param typeName 类型名称
     * @param parentTypeName 父类型名称
     * @return
     */
    @GetMapping("/listQuery")
    @ApiOperation("列表查询")
    public MessageListBean<MapPointType> listQuery(
               @RequestParam(name = "typeName", required = false) String typeName,
               @RequestParam(name = "parentTypeName", required = false) String parentTypeName,
               @RequestParam(name = "campusCode", required = false) Integer campusCode){
        return MessageListBean.construct(mapPointTypeService.listQuery(typeName, parentTypeName, campusCode),"列表查询");
    }

    /**
     * 获取父类别列表
     * @return
     */
    @GetMapping("/queryParentList")
    @ApiOperation("获取父类别列表")
    public MessageListBean<MapPointType> queryParentList(){
        return MessageListBean.construct(mapPointTypeService.queryParentList(),"获取父类别列表");
    }

    /**
     * 添加并返回新对象
     * @param mapPointType 点标注类型对象
     * @return
     */
    @PutMapping(value = "/add")
    @ApiOperation("添加并返回新对象")
    public MessageBean<MapPointType> add(@RequestBody MapPointType mapPointType){
        return MessageBean.construct(mapPointTypeService.add(mapPointType),"添加并返回新对象");
    }

    /**
     * 根据主键查询对象
     * @param typeCode 类型代码
     * @return
     */
    @GetMapping("/get/{typeCode}")
    @ApiOperation("根据主键查询对象")
    public MessageBean<MapPointType> queryById(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.construct(mapPointTypeService.queryById(typeCode),"根据主键查询对象");
    }

    /**
     * 更新并返回新对象
     * @param mapPointType 点标注类型对象
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新并返回新对象")
    public MessageBean<MapPointType> update(@RequestBody MapPointType mapPointType){
        return MessageBean.construct(mapPointTypeService.update(mapPointType),"更新并返回新对象");
    }

    /**
     * 根据分类编码删除信息
     * @param typeCode 类型代码
     * @return
     */
    @DeleteMapping("/delete/{typeCode}")
    @ApiOperation("根据分类编码删除信息")
    public MessageBean<Integer> delete(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.construct(mapPointTypeService.delete(typeCode),"根据分类编码删除信息");
    }

    /**
     * 批量删除
     * @param ids 批量删除的代码
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(String ids){
        return MessageBean.construct(mapPointTypeService.bulkDelete(ids),"批量删除");
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return
     */
    @PostMapping(value = "/uploadImg")
    @ApiOperation("上传图片")
    public MessageBean<String> uploadImg( MultipartFile file){
        return fileUploadService.uploadFile(file, FILE_UPLOAD_FOLDER);
    }

    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        return mapPointTypeService.exportTemplate();
    }

    /**
     * 导出
     * @return
     */
    @GetMapping("/download")
    @ApiOperation("导出")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "userId") String userId)
            throws IOException {
        return mapPointTypeService.download(userId);
    }

    /**
     * 导入信息
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation("导入")
    public MessageBean upload(MultipartFile file)
            throws IOException {
        mapPointTypeService.upload(
                fileUploadService.uploadFileReturnInputStream(file, FILE_UPLOAD_FOLDER));
        return MessageBean.ok();
    }
}
