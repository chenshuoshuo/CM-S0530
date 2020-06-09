package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapBuilding;
import com.lqkj.web.gnsc.modules.portal.service.MapBuildingService;
import com.lqkj.web.gnsc.utils.ImportDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

/**
 * 大楼信息controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapBuilding")
@Api(tags = "portal-大楼信息管理")
public class MapBuildingController {
    @Autowired
    MapBuildingService mapBuildingService;
    @Autowired
    FileUploadService fileUploadService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String FILE_UPLOAD_FOLDER = "mapBuilding";

    /**
     * 分页查询
     * @param campusCode 校区编码
     * @param typeCode 类型代码
     * @param buildingName 大楼名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean pageQuery(
              @RequestParam(name = "campusCode", required = false) Integer campusCode,
              @RequestParam(name = "typeCode", required = false) Integer typeCode,
              @RequestParam(name = "buildingName", required = false) String buildingName,
              @RequestParam(name = "page", defaultValue = "0") Integer page,
              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return MessageBean.construct(mapBuildingService.pageQuery(campusCode, typeCode, buildingName, page, pageSize),"分页查询");
    }

    /**
     * 列表查询
     * @param campusCode 校区编码
     * @param typeCode 类型代码
     * @param buildingName 大楼名称
     * @return
     */
    @GetMapping(value = "/listQuery")
    @ApiOperation("列表查询")
    public MessageListBean listQuery(
             @RequestParam(name = "campusCode", required = false) Integer campusCode,
             @RequestParam(name = "typeCode", required = false) Integer typeCode,
             @RequestParam(name = "buildingName", required = false) String buildingName){
        return MessageListBean.construct(mapBuildingService.listQuery(campusCode, typeCode, buildingName),"列表查询");
    }

    /**
     * 根据校区获取列表
     * @param campusCode 校区编码
     * @param typeCode 类型代码
     * @param buildingName 大楼名称
     * @return
     */
    @GetMapping(value = "/listQueryByCampusCode")
    @ApiOperation("根据校区获取列表")
    public MessageListBean listQueryByCampusCode(
            @RequestParam(name = "campusCode", required = false) Integer campusCode,
            @RequestParam(name = "typeCode", required = false) Integer typeCode,
            @RequestParam(name = "buildingName", required = false) String buildingName){
        return MessageListBean.construct(mapBuildingService.listQuery(campusCode, typeCode, buildingName),"根据校区获取列表");
    }

    /**
     * 添加并返回对象
     * @param mapBuildingVO 大楼VO对象
     * @return
     */
    @PutMapping(value = "/add")
    @ApiOperation("添加并返回对象")
    public MessageBean add(@RequestBody MapBuilding mapBuildingVO){
        return MessageBean.construct(mapBuildingService.add(mapBuildingVO),"添加并返回对象");
    }

    /**
     * 根据主键获取实体
     * @param buildingCode 大楼编码
     * @return
     */
    @GetMapping(value = "/get/{buildingCode}")
    @ApiOperation("根据主键获取实体")
    public MessageBean get(@PathVariable(name = "buildingCode") Integer buildingCode){
        return MessageBean.construct(mapBuildingService.get(buildingCode),"根据主键获取实体");
    }

    /**
     * 更新并返回
     * @param mapBuildingVO 大楼VO对象
     * @return
     */
    @PutMapping(value = "/update")
    @ApiOperation("更新并返回")
    public MessageBean update(@RequestBody MapBuilding mapBuildingVO){
        return mapBuildingService.update(mapBuildingVO);
    }

    /**
     * 根据主键删除
     * @param buildingCode 大楼编码
     * @return
     */
    @DeleteMapping(value = "/delete/{buildingCode}")
    @ApiOperation("根据主键删除")
    public MessageBean<Integer> delete(@PathVariable(name = "buildingCode") Integer buildingCode){
        return MessageBean.construct(mapBuildingService.delete(buildingCode),"根据主键删除");
    }

    /**
     * 批量删除
     * @param ids 批量删除的大楼编码
     * @return
     */
    @DeleteMapping(value = "/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestParam(name = "ids") String ids){
        return MessageBean.construct(mapBuildingService.bulkDelete(ids),"批量删除");
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return
     */
    @PostMapping(value = "/uploadImg")
    @ApiOperation("上传图片")
    public MessageBean<String> uploadImg(@RequestPart(name = "file") MultipartFile file){
        return fileUploadService.uploadFile(file, FILE_UPLOAD_FOLDER);
    }


    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        InputStream inputStream = mapBuildingService.exportTemplate();
        String fileName = null;
        try {
            fileName = URLEncoder.encode("大楼信息导入模板.xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            //e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .body(new InputStreamResource(inputStream));
    }

    /**
     * 导出
     * @return
     */
    @GetMapping("/download")
    @ApiOperation("导出")
    public ResponseEntity<InputStreamResource> download(@RequestParam(name = "userId") String userId){
        try {
            InputStream inputStream =  mapBuildingService.download(userId);
            String fileName = null;
            try {
                fileName = URLEncoder.encode("大楼信息.xlsx", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .body(new InputStreamResource(inputStream));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 导入信息
     * @return
     */
    @PostMapping(value = "/upload")
    @ApiOperation("导入")
    public MessageBean upload(MultipartFile file)
            throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String path = fileUploadService.uploadFileReturnAbsolutePath(file, FILE_UPLOAD_FOLDER);
        InputStream inputStream = new FileInputStream(path);
        mapBuildingService.upload(inputStream);
        return MessageBean.ok();
    }


}
