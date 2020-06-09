package com.lqkj.web.gnsc.modules.portal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapPoint;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapPointVO;
import com.lqkj.web.gnsc.modules.portal.service.MapPointService;
import com.lqkj.web.gnsc.utils.UtilPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 点标注信息controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapPoint")
@Api(tags = "portal-点标注信息管理")
public class MapPointController {
    @Autowired
    MapPointService mapPointService;
    @Autowired
    FileUploadService fileUploadService;

    protected static ObjectMapper objectMapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String FILE_UPLOAD_FOLDER = "mapPoint";

    /**
     * 分页查询
     * @param campusCode 校区编号
     * @param typeCode 类型编号
     * @param parentTypeCode 父类型编号
     * @param pointName 标注名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean pageQuery(
               @RequestParam(name = "campusCode", required = false) Integer campusCode,
               @RequestParam(name = "typeCode", required = false) Integer typeCode,
               @RequestParam(name = "parentTypeCode", required = false) Integer parentTypeCode,
               @RequestParam(name = "pointName", required = false) String pointName,
               @RequestParam(name = "page", defaultValue = "0") Integer page,
               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        return MessageBean.construct(
                mapPointService.pageQuery(campusCode, typeCode, parentTypeCode, pointName, page, pageSize),"分页查询");
    }

    /**
     * 获取列表
     * @param campusCode 校区编号
     * @param typeCode 类型编号
     * @param parentTypeCode 父类型编号
     * @param pointName 标注名称
     * @return
     */
    @GetMapping(value = "/queryList")
    @ApiOperation("获取列表")
    public MessageListBean queryList(
                                     @RequestParam(name = "campusCode", required = false) Integer campusCode,
                                     @RequestParam(name = "typeCode", required = false) Integer typeCode,
                                     @RequestParam(name = "parentTypeCode", required = false) Integer parentTypeCode,
                                     @RequestParam(name = "pointName", required = false) String pointName){
        return MessageListBean.construct(mapPointService.queryList(campusCode, typeCode, parentTypeCode, pointName),"获取列表");
    }

    /**
     * 添加
     * @param mapPointVO 点标注VO
     * @return
     */
    @PutMapping(value = "/add")
    @ApiOperation("添加")
    public MessageBean add(@RequestBody MapPoint mapPointVO){
        return MessageBean.construct(mapPointService.add(mapPointVO),"添加");
    }

    /**
     * 根据代码获取
     * @param pointCode 点标注编号
     * @return
     */
    @GetMapping(value = "/get/{pointCode}")
    @ApiOperation("根据代码获取")
    public MessageBean<MapPointVO> get(@PathVariable(name = "pointCode") Integer pointCode){
        return MessageBean.construct(mapPointService.get(pointCode),"根据代码获取");
    }

    /**
     * 保存
     * @param mapPointVO 点标注VO
     * @return
     */
    @PutMapping(value = "/update")
    @ApiOperation("更新")
    public MessageBean<MapPointVO> update(@RequestBody MapPointVO mapPointVO)
            throws IOException {
        return MessageBean.construct(mapPointService.update(mapPointVO),"保存");
    }

    /**
     * 删除
     * @param pointCode 点标注VO
     * @return
     */
    @DeleteMapping(value = "/delete/{pointCode}")
    @ApiOperation("删除")
    public MessageBean<Integer> delete(@PathVariable(name = "pointCode") Integer pointCode){
        return MessageBean.construct(mapPointService.delete(pointCode),"删除");
    }

    /**
     * 批量删除
     * @param ids 批量删除的点标注编号
     * @return
     */
    @DeleteMapping(value = "/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestParam(name = "ids") String ids) {
        return MessageBean.construct(mapPointService.bulkDelete(ids),"批量删除");
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

    @GetMapping(value = "/refreshRasterGeom")
    @ApiOperation("刷新空间数据")
    public MessageBean<Integer> refreshRasterGeom(){
        return MessageBean.construct(mapPointService.refreshRasterGeom(),"刷新空间数据");
    }

    /**
     * 下载导入模板
     * @return
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation("下载导入模板")
    public ResponseEntity<InputStreamResource> downloadTemplate() throws IOException {
        InputStream inputStream = mapPointService.exportTemplate();
        String fileName = null;
        try {
            fileName = URLEncoder.encode("点标注信息导入模板.xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
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
            InputStream inputStream =  mapPointService.download(userId);
            String fileName = null;
            try {
                fileName = URLEncoder.encode("点标注信息.xlsx", "UTF-8");
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
        mapPointService.upload(inputStream);
        return MessageBean.ok();
    }
}
