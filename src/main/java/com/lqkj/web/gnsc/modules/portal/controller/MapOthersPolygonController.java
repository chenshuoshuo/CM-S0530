package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygon;
import com.lqkj.web.gnsc.modules.portal.model.vo.MapOthersPolygonVO;
import com.lqkj.web.gnsc.modules.portal.service.MapOthersPolygonService;
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
 * 其他面图源信息controller
 * @version 1.0
 * @author RY
 */

@RestController
@RequestMapping("/mapOthersPolygon")
@Api(tags = "portal-其他面图源信息")
public class MapOthersPolygonController {
    @Autowired
    MapOthersPolygonService mapOthersPolygonService;
    @Autowired
    FileUploadService fileUploadService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private static String FILE_UPLOAD_FOLDER = "mapOthersPolygon";

    /**
     * 分页查询
     * @param campusCode 校区编码
     * @param typeCode 分类代码
     * @param polygonName 面图源名称
     * @param page 页码
     * @param pageSize 每页数据条数
     * @return
     */
    @GetMapping("/pageQuery")
    @ApiOperation("分页查询")
    public MessageBean<UtilPage<MapOthersPolygonVO>> pageQuery(
              @RequestParam(name = "campusCode", required = false) Integer campusCode,
              @RequestParam(name = "typeCode", required = false) Integer typeCode,
              @RequestParam(name = "polygonName", required = false) String polygonName,
              @RequestParam(name = "page", defaultValue = "0") Integer page,
              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){
        if ("".equals(polygonName)) {
            polygonName = null;
        }
        if ("".equals(campusCode)) {
            campusCode = null;
        }
        if ("".equals(typeCode)) {
            typeCode = null;
        }
        return MessageBean.construct(mapOthersPolygonService.pageQuery(campusCode, typeCode, polygonName, page, pageSize),"分页查询");
    }

    /**
     * 列表查询
     * @param campusCode 校区编码
     * @param typeCode 分类代码
     * @param polygonName 面图源名称
     * @return
     */
    @GetMapping("/listQuery")
    @ApiOperation("列表查询")
    public MessageListBean listQuery(
               @RequestParam(name = "campusCode", required = false) Integer campusCode,
               @RequestParam(name = "typeCode", required = false) Integer typeCode,
               @RequestParam(name = "polygonName", required = false) String polygonName){
        return MessageListBean.construct(mapOthersPolygonService.listQuery(campusCode, typeCode, polygonName),"列表查询");
    }

    /**
     * 添加并返回对象
     * @return
     */
    @PutMapping("/add")
    @ApiOperation("添加并返回对象")
    public MessageBean<MapOthersPolygon> add(@RequestBody MapOthersPolygon mapOthersPolygon){
        return MessageBean.construct(mapOthersPolygonService.add(mapOthersPolygon),"添加并返回对象");
    }

    /**
     * 根据主键获取实体
     * @param polygonCode 面图源编号
     * @return
     */
    @GetMapping("/get/{polygonCode}")
    @ApiOperation("根据主键获取实体")
    public MessageBean<MapOthersPolygonVO> get(@PathVariable(name = "polygonCode") Integer polygonCode){
        return MessageBean.construct(mapOthersPolygonService.get(polygonCode),"根据主键获取实体");
    }

    /**
     * 更新并返回
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新并返回")
    public MessageBean<MapOthersPolygon> update(@RequestBody MapOthersPolygon mapOthersPolygon){
        return mapOthersPolygonService.update(mapOthersPolygon);
    }

    /**
     * 根据主键删除
     * @param polygonCode 面图源编号
     * @return
     */
    @DeleteMapping("/delete/{polygonCode}")
    @ApiOperation("根据主键删除")
    public MessageBean<Integer> delete(@PathVariable(name = "polygonCode") Integer polygonCode){
        return MessageBean.construct(mapOthersPolygonService.delete(polygonCode),"根据主键删除");
    }

    /**
     * 批量删除
     * @param ids 批量删除的面图源编号
     * @return
     */
    @DeleteMapping("/bulkDelete")
    @ApiOperation("批量删除")
    public MessageBean<Integer> bulkDelete(@RequestParam(name = "ids") String ids){
        return MessageBean.construct(mapOthersPolygonService.bulkDelete(ids),"批量删除");
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
        InputStream inputStream = mapOthersPolygonService.exportTemplate();
        String fileName = null;
        try {
            fileName = URLEncoder.encode("其他面图元信息导入模板.xlsx", "UTF-8");
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
            InputStream inputStream =  mapOthersPolygonService.download(userId);
            String fileName = null;
            try {
                fileName = URLEncoder.encode("其他面图元信息.xlsx", "UTF-8");
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
    @ApiOperation("导入信息")
    public MessageBean upload(MultipartFile file)
            throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String path = fileUploadService.uploadFileReturnAbsolutePath(file, FILE_UPLOAD_FOLDER);
        InputStream inputStream = new FileInputStream(path);
        mapOthersPolygonService.upload(inputStream);
        return MessageBean.ok();
    }

}
