package com.lqkj.web.gnsc.modules.portal.controller;


import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapRoomVO;
import com.lqkj.web.gnsc.modules.portal.service.MapRoomService;
import com.lqkj.web.gnsc.utils.UtilPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

/**
 * 房间信息controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapRoom")
@Api(tags = "portal-房间信息管理")
public class MapRoomController {
    @Autowired
    MapRoomService mapRoomService;
    @Autowired
    FileUploadService fileUploadService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private static String FILE_UPLOAD_FOLDER = "mapRoom";

    /**
     * 分页查询
     * @param campusCode 校区编码
     * @param typeCode 类型代码
     * @param roomName 房间名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean pageQuery(
            @RequestParam(name = "mapBuildingCode", required = false) Long mapBuildingCode,
            @RequestParam(name = "campusCode", required = false) Integer campusCode,
              @RequestParam(name = "typeCode", required = false) Integer typeCode,
              @RequestParam(name = "roomName", required = false) String roomName,
              @RequestParam(name = "page", defaultValue = "0") Integer page,
              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        if ("".equals(mapBuildingCode))
            mapBuildingCode = null;
        if ("".equals(campusCode))
            campusCode = null;
        if ("".equals(typeCode))
            typeCode = null;
        if ("".equals(roomName))
            roomName = null;
        return MessageBean.construct(mapRoomService.pageQuery(mapBuildingCode, campusCode, typeCode, roomName, page, pageSize),"分页查询");
    }

    /**
     * 列表查询
     * @param campusCode 校区编码
     * @param typeCode 类型代码
     * @param roomName 房间名称
     * @return
     */
    @GetMapping("/listQuery")
    @ApiOperation("列表查询")
    public MessageListBean<MapRoom> listQuery(@RequestParam(name = "mapBuildingCode", required = false) Long mapBuildingCode,
                                                @RequestParam(name = "campusCode", required = false) Integer campusCode,
                                                @RequestParam(name = "typeCode", required = false) Integer typeCode,
                                                @RequestParam(name = "roomName", required = false) String roomName){
        return MessageListBean.construct(mapRoomService.listQuery(mapBuildingCode, campusCode, typeCode, roomName),"列表查询");
    }

    /**
     * 添加并返回对象
     * @param mapRoomVO 房间信息VO
     * @return
     */
    @PutMapping("/add")
    @ApiOperation("添加并返回对象")
    public MessageBean<MapRoom> add(@RequestBody MapRoom mapRoomVO){
        return MessageBean.construct(mapRoomService.add(mapRoomVO),"添加并返回对象");
    }

    /**
     * 根据主键获取实体
     * @param roomCode 房间代码
     * @return
     */
    @GetMapping("/get/{roomCode}")
    @ApiOperation("根据主键获取实体")
    public MessageBean<MapRoomVO> get(@PathVariable(name = "roomCode") Integer roomCode){
        return MessageBean.construct(mapRoomService.get(roomCode),"根据主键获取实体");
    }

    /**
     * 更新并返回
     * @param mapRoomVO 房间信息VO
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新并返回")
    public MessageBean<MapRoomVO> update(@RequestBody MapRoom mapRoomVO){
        return mapRoomService.update(mapRoomVO);
    }

    /**
     * 根据主键删除
     * @param roomCode 房间代码
     * @return
     */
    @DeleteMapping("/delete/{roomCode}")
    @ApiOperation("根据主键删除")
    public MessageBean<Integer> delete(@PathVariable(name = "roomCode") Integer roomCode){
        return MessageBean.construct(mapRoomService.delete(roomCode),"根据主键删除");
    }

    /**
     * 批量删除
     * @param ids 批量删除的房间代码
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestParam(name = "ids") String ids){
        return MessageBean.construct(mapRoomService.bulkDelete(ids),"批量删除");
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return
     */
    @PostMapping(value = "/uploadImg")
    @ApiOperation("上传图片")
    public MessageBean<String> uploadImg(MultipartFile file){
        return fileUploadService.uploadFile(file, FILE_UPLOAD_FOLDER);
    }

    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {

        InputStream inputStream = mapRoomService.exportTemplate();
        String fileName = null;
        try {
            fileName = URLEncoder.encode("房间信息导入模板.xlsx", "UTF-8");
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
            InputStream inputStream =  mapRoomService.download(userId);
            String fileName = null;
            try {
                fileName = URLEncoder.encode("房间信息.xlsx", "UTF-8");
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
    public MessageBean upload(MultipartFile file)
            throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String path = fileUploadService.uploadFileReturnAbsolutePath(file, FILE_UPLOAD_FOLDER);
        InputStream inputStream = new FileInputStream(path);
        mapRoomService.upload(inputStream);
        return MessageBean.ok();
    }

}
