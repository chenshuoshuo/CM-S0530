package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.domain.vo.Base64ImgVo;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @author cs
 * @Date 2020/6/4 15:33
 * @Version 2.2.2.0
 **/
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传管理")
public class FileUploadController {

    @Autowired
    private FileUploadService uploadService;

    private static final String FILE_FOLDER = "gns";

    /**
     * 上传文件
     *
     * @return
     */
    @ApiOperation("上传离线文件")
    @PostMapping("/uploadZipFile")
    public MessageBean uploadZipFile(MultipartFile file) throws IOException {

        return uploadService.uploadZip(file,FILE_FOLDER);
    }

    /**
     * 上传文件
     *
     * @return
     */
    @ApiOperation("上传图片音频视频")
    @PostMapping( "/uploadFile")
    public MessageBean<String> uploadFile(MultipartFile file) throws Exception {

        return uploadService.uploadFile(file,FILE_FOLDER);
    }

    /**
     * 上传图片
     * @param base64ImgVo 上传Base64对象
     * @return
     */
    @ApiOperation("上传Base64对象")
    @PostMapping(value = "/base64/uploadImg")
    public MessageBean<String> uploadImg(@RequestBody Base64ImgVo base64ImgVo){
        return uploadService.uploadBase64ImgFile(base64ImgVo.getBase64Img().split(",")[1],base64ImgVo.getFileName(),base64ImgVo.getFolder());
    }

    /**
     * 文件同步
     * 同步cmips  upload  下文件
     * @return
     */
    @ApiOperation("文件同步")
    @GetMapping(value = "/synFile")
    public MessageBean synFile(@RequestParam(name = "path")String path){
        return uploadService.synFile(path);
    }

    /**
     * 压缩upload 下所有文件
     * 同步cmips  upload  下文件
     * @return
     */
    @ApiOperation("压缩文件")
    @GetMapping(value = "/fileToZip")
    public MessageBean fileToZip(){
        return MessageBean.ok(uploadService.fileToZip("upload"));
    }
}
