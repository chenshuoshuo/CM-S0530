package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.domain.vo.Base64ImgVo;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
