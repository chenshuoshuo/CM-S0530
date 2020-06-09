package com.lqkj.web.gnsc.modules.portal.controller;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapRoomType;
import com.lqkj.web.gnsc.modules.portal.service.MapRoomTypeService;
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
 * 房间分类controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapRoomType")
@Api(tags = "portal-房间分类管理")
public class MapRoomTypeController {
    @Autowired
    MapRoomTypeService mapRoomTypeService;
    @Autowired
    FileUploadService fileUploadService;

    private static String FILE_UPLOAD_FOLDER = "mapPointType";

    /**
     * 分页查询房间类型信息
     * @param typeName 分类名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean<UtilPage<MapRoomType>> pageQuery(
            @RequestParam(name = "typeName", required = false) String typeName,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return MessageBean.construct(mapRoomTypeService.pageQuery(typeName, page, pageSize),"分页查询房间类型信息");
    }

    /**
     * 获取分类列表
     * @param typeName 分类名称
     * @return
     */
    @GetMapping("/queryList")
    @ApiOperation("获取分类列表")
    public MessageListBean<MapRoomType> queryList(@RequestParam(name = "typeName", required = false) String typeName){
        return MessageListBean.construct(mapRoomTypeService.queryList(typeName),"获取分类列表");
    }

    /**
     * 添加并返回新对象
     * @param mapRoomType 分类对象
     * @return
     */
    @PutMapping("/add")
    @ApiOperation("添加并返回新对象")
    public MessageBean<MapRoomType> add(@RequestBody MapRoomType mapRoomType){
        return MessageBean.construct(mapRoomTypeService.add(mapRoomType),"添加并返回新对象");
    }

    /**
     * 根据主键查询对象
     * @param typeCode 分类编号
     * @return
     */
    @GetMapping("/get/{typeCode}")
    @ApiOperation("根据主键查询对象")
    public MessageBean<MapRoomType> queryById(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.construct(mapRoomTypeService.queryById(typeCode),"根据主键查询对象");
    }

    /**
     * 更新并返回新对象
     * @param mapRoomType 分类对象
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新并返回新对象")
    public MessageBean<MapRoomType> update(@RequestBody MapRoomType mapRoomType){
        return MessageBean.construct(mapRoomTypeService.update(mapRoomType),"更新并返回新对象");
    }

    /**
     * 根据分类编码删除信息
     * @param typeCode 分类编号
     * @return
     */
    @DeleteMapping("/delete/{typeCode}")
    @ApiOperation("根据分类编码删除信息")
    public MessageBean<Integer> delete(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.construct(mapRoomTypeService.delete(typeCode),"根据分类编码删除信息");
    }

    /**
     * 批量删除
     * @param ids 批量删除的编号
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestParam(name = "ids") String ids){
        return MessageBean.construct(mapRoomTypeService.bulkDelete(ids),"批量删除");
    }

    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        return mapRoomTypeService.exportTemplate();
    }

    /**
     * 导出
     * @return
     */
    @GetMapping("/download")
    @ApiOperation("导出")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "userId") String userId)
            throws IOException {
        return mapRoomTypeService.download(userId);
    }

    /**
     * 导入信息
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation("导入")
    public MessageBean upload(MultipartFile file)
            throws IOException {
        mapRoomTypeService.upload(
                fileUploadService.uploadFileReturnInputStream(file, FILE_UPLOAD_FOLDER));
        return MessageBean.ok();
    }
}
