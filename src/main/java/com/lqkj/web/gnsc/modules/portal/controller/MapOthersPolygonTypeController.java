package com.lqkj.web.gnsc.modules.portal.controller;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygonType;
import com.lqkj.web.gnsc.modules.portal.service.MapOthersPolygonTypeService;
import com.lqkj.web.gnsc.utils.UtilPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 其他面图源分类controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapOthersPolygonType")
@Api(tags = "portal-其他面图源分类")
public class MapOthersPolygonTypeController {
    @Autowired
    MapOthersPolygonTypeService mapOthersPolygonTypeService;
    @Autowired
    FileUploadService fileUploadService;

    private static String FILE_UPLOAD_FOLDER = "mapOthersPolygonType";

    /**
     * 分页查询房间类型信息
     * @param typeName 分类名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean<UtilPage<MapOthersPolygonType>> pageQuery(
            @RequestParam(name = "typeName", required = false) String typeName,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return MessageBean.construct(mapOthersPolygonTypeService.pageQuery(typeName, page, pageSize),"分页查询");
    }

    /**
     * 获取分类列表
     * @param typeName 分类名称
     * @return
     */
    @GetMapping("/listQuery")
    @ApiOperation("获取分类列表")
    public MessageListBean<MapOthersPolygonType> queryList(
            @RequestParam(name = "typeName", required = false) String typeName){
        return MessageListBean.construct(mapOthersPolygonTypeService.queryList(typeName),"获取分类列表");
    }

    /**
     * 添加并返回新对象
     * @param mapOthersPolygonType 分类对象
     * @return
     */
    @PutMapping("/add")
    @ApiOperation("添加并返回新对象")
    public MessageBean<MapOthersPolygonType> add(@RequestBody MapOthersPolygonType mapOthersPolygonType){
        return MessageBean.construct(mapOthersPolygonTypeService.add(mapOthersPolygonType),"添加并返回新对象");
    }

    /**
     * 根据主键查询对象
     * @param typeCode 分类编号
     * @return
     */
    @GetMapping("/get/{typeCode}")
    @ApiOperation("根据主键查询对象")
    public MessageBean<MapOthersPolygonType> queryById(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.construct(mapOthersPolygonTypeService.queryById(typeCode),"根据主键查询对象");
    }

    /**
     * 更新并返回新对象
     * @param mapOthersPolygonType 分类对象
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新并返回新对象")
    public MessageBean<MapOthersPolygonType> update(@RequestBody MapOthersPolygonType mapOthersPolygonType){
        return MessageBean.construct(mapOthersPolygonTypeService.update(mapOthersPolygonType),"更新并返回新对象");
    }

    /**
     * 根据分类编码删除信息
     * @param typeCode 分类编号
     * @return
     */
    @DeleteMapping("/delete/{typeCode}")
    @ApiOperation("根据分类编码删除信息")
    public MessageBean<Integer> delete(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.construct(mapOthersPolygonTypeService.delete(typeCode),"根据分类编码删除信息");
    }

    /**
     * 批量删除
     * @param ids 批量删除的分类编号
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestParam(name = "ids") String ids){
        return MessageBean.construct(mapOthersPolygonTypeService.bulkDelete(ids),"批量删除");
    }

    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        return mapOthersPolygonTypeService.exportTemplate();
    }

    /**
     * 导出
     * @return
     */
    @GetMapping("/download")
    @ApiOperation("导出")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "userId") String userId)
            throws IOException {
        return mapOthersPolygonTypeService.download(userId);
    }

    /**
     * 导入信息
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation("导入信息")
    public MessageBean upload(MultipartFile file)
            throws IOException {
        mapOthersPolygonTypeService.upload(
                fileUploadService.uploadFileReturnInputStream(file, FILE_UPLOAD_FOLDER));
        return MessageBean.ok();
    }
}
