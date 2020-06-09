package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.APIVersion;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.service.FileUploadService;
import com.lqkj.web.gnsc.modules.gns.service.GnsMergeImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gns/saMergeImg")
@Api(tags = "图片融合管理")
public class GnsMergeImgController {
    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private GnsMergeImgService saMergeImgService;

    private static String FILE_UPLOAD_FOLDER = "gns/temp";

    @GetMapping("/mergeFace")
    @ApiOperation("根据背景进行图片融合")
    public MessageBean<Object> mergeface(@RequestParam(name = "schoolId",required = true) Integer schoolId,
                                         @RequestParam(name = "backGroundPhotoUrl",required = true) String backGroundPhotoUrl,
                                         @RequestParam(name = "mergeImgPath",required = true) String mergeImgPath){
        return saMergeImgService.mergeface(schoolId,backGroundPhotoUrl,mergeImgPath);
    }

    @GetMapping("/mergeBody")
    @ApiOperation("图片融合")
    public MessageBean<Object> humanBodySegment(@RequestParam(name = "schoolId",required = true) Integer schoolId,
                                                @RequestParam(name = "mergeImgPath",required = true) String mergeImgPath){
        return saMergeImgService.humanBodySegment(schoolId,mergeImgPath);
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return
     */
    @PostMapping(value = "/uploadImg")
    public MessageBean<String> uploadImg(MultipartFile file){
        return fileUploadService.uploadFile(file, FILE_UPLOAD_FOLDER);
    }

}
