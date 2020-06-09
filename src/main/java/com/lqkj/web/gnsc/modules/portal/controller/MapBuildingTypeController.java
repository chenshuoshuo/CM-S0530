package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapBuildingType;
import com.lqkj.web.gnsc.modules.portal.service.MapBuildingTypeService;
import com.lqkj.web.gnsc.utils.ImportDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 大楼分类controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapBuildingType")
@Api(tags = "portal-大楼类别管理")
public class MapBuildingTypeController {
    @Autowired
    MapBuildingTypeService mapBuildingTypeService;
    @Autowired
    FileUploadService fileUploadService;

    private static String FILE_UPLOAD_FOLDER = "mapBuildingType";

    /**
     * 分页查询大楼类型信息
     * @param typeName 分类名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @ApiOperation("分页")
    @GetMapping("/pageQuery")
    public MessageBean pageQuery(@RequestParam(name = "typeName", required = false) String typeName,
                                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return MessageBean.ok(mapBuildingTypeService.pageQuery(typeName, page, pageSize));
    }

    /**
     * 获取分类列表
     * @param typeName
     * @return
     */
    @GetMapping("/queryList")
    @ApiOperation("列表查询")
    public MessageListBean queryList(@RequestParam(name = "typeName", required = false) String typeName){
        return MessageListBean.ok(mapBuildingTypeService.queryList(typeName));
    }

    /**
     * 添加并返回新对象
     * @param mapBuildingType
     * @return
     */
    @PutMapping("/add")
    @ApiOperation("添加并返回新对象")
    public MessageBean add(@RequestBody MapBuildingType mapBuildingType){
        return MessageBean.ok(mapBuildingTypeService.add(mapBuildingType));
    }

    /**
     * 根据主键查询对象
     * @param typeCode
     * @return
     */
    @GetMapping("/get/{typeCode}")
    @ApiOperation("根据主键查询对象")
    public MessageBean queryById(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.ok(mapBuildingTypeService.queryById(typeCode));
    }

    /**
     * 更新并返回新对象
     * @param mapBuildingType
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新并返回新对象")
    public MessageBean update(@RequestBody MapBuildingType mapBuildingType){
        return MessageBean.ok(mapBuildingTypeService.update(mapBuildingType));
    }

    /**
     * 根据分类编码删除信息
     * @param typeCode
     * @return
     */
    @DeleteMapping("/delete/{typeCode}")
    @ApiOperation("根据分类编码删除信息")
    public MessageBean delete(@PathVariable(name = "typeCode") Integer typeCode){
        return MessageBean.ok(mapBuildingTypeService.delete(typeCode));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean bulkDelete(@RequestParam(name = "ids") String ids){
        return MessageBean.ok(mapBuildingTypeService.bulkDelete(ids));
    }

    /**
     * 批量保存
     * @return
     */
    @PutMapping("/saveAll")
    @ApiOperation("批量保存")
    public MessageListBean saveAll(@RequestBody List<MapBuildingType> buildingTypeList){
        return MessageListBean.ok(mapBuildingTypeService.saveAll(buildingTypeList));
    }


    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        return mapBuildingTypeService.exportTemplate();
    }

    /**
     * 导出
     * @return
     */
    @GetMapping("/download")
    @ApiOperation("导出")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "userId") String userId)
            throws IOException {
        return mapBuildingTypeService.download(userId);
    }

    /**
     * 导入信息
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation("导入")
    public MessageBean upload(MultipartFile file)
            throws IOException {
        mapBuildingTypeService.upload(
                fileUploadService.uploadFileReturnInputStream(file, FILE_UPLOAD_FOLDER));
        return MessageBean.ok();
    }
}
