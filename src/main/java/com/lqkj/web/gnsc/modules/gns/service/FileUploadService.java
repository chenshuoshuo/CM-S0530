package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.utils.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author cs
 * @Date 2020/6/4 15:40
 * @Version 2.2.2.0
 **/
@Service
public class FileUploadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 上传压缩文件
     * @param file
     * @param folder
     * @return
     */
    public MessageBean<String> uploadZip(MultipartFile file, String folder) {
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        if(fileExtension.contains("zip")){
            String property = System.getProperty("user.dir");
            String resultUrl = unPackFileZip(file,folder);
            long time = new Date().getTime();
            return MessageBean.ok(this.changePath(property +"/"+ resultUrl, time));
        }else {
            return MessageBean.error("格式错误");
        }
    }

    /**
     * 上传图片/音频/视频
     * @param file
     * @param folder
     * @return
     */
    public MessageBean<String> uploadFile(MultipartFile file, String folder) {

        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if(fileExtension.toLowerCase().contains("png") ||fileExtension.toLowerCase().contains("jpg")
                ||fileExtension.toLowerCase().contains("jpeg") || fileExtension.toLowerCase().contains("gif")
                || fileExtension.toLowerCase().contains("mp3") || fileExtension.toLowerCase().contains("svg")
                || fileExtension.toLowerCase().contains("mp4")){
            File uploadFile = uploadFileReturnFile(file, folder);
            return MessageBean.ok("/upload/" + folder + "/" + uploadFile.getName());
        }
        return MessageBean.error("文件格式错误");
    }
    /**
     * 上传base64图片
     * @param folder
     * @return
     */
    public MessageBean<String> uploadBase64ImgFile(String base64File, String fileName, String folder) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(!FileUtil.isImgFile(fileExtension)){
            return MessageBean.error("图片格式错误");
        }
        try{
            String newFileName = UUID.randomUUID() + fileExtension;
            File uploadFolder = new File("./upload/" + folder + "/");
            if(!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            String imgUrl = "./upload/" + folder + "/" + newFileName;
            FileUtil.decoderBase64File(base64File,imgUrl);
            File uploadFile = new File(imgUrl);
            if(folder.equals("gns")){
                Thumbnails.of(uploadFile.getAbsolutePath())
                        .width(1125)
                        .toFile(imgUrl);
            }else {
                Thumbnails.of(uploadFile.getAbsolutePath())
                        .scale(1f)
                        .outputQuality(0.25f)
                        .toFile(imgUrl);
            }
            return  MessageBean.ok(imgUrl.substring(1));
        }catch (Exception e){
            //e.printStackTrace();
            logger.error(e.getMessage(),e);
            return MessageBean.error(e.getMessage());
        }
    }

    /**
     * 上传文件
     * 返回文件输入流
     * @param file 文件
     * @param folder 上传路径
     * @return
     */
    public InputStream uploadFileReturnInputStream(MultipartFile file, String folder) throws FileNotFoundException {
        File uploadFile = uploadFileReturnFile(file, folder);
        return new FileInputStream(uploadFile.getAbsolutePath());
    }

    public String uploadFileReturnAbsolutePath(MultipartFile file, String folder) {
        File uploadFile = uploadFileReturnFile(file, folder);
        return uploadFile.getAbsolutePath();
    }

    private File uploadFileReturnFile(MultipartFile file, String folder){
        try {
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String newFileName = UUID.randomUUID() + fileExtension;
            File uploadFolder = new File("./upload/" + folder + "/");
            if(!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            File uploadFile = new File(uploadFolder, newFileName);
            System.out.println(uploadFile.getAbsolutePath());
            FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);
            return uploadFile;
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return null;
        }

    }

    /**
     * 解压离线文件
     */
    private String unPackFileZip(MultipartFile file,String folder) {

        //解压路径
        try{
            File uploadFolder = new File("./upload/" + folder + "/");
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            File zipFile = new File(uploadFolder, file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), zipFile);
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            List<File> files = new ArrayList<>();
            File fileDir = null;
            String fileName = null;
            for (Enumeration enumeration = zip.entries(); enumeration.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                if(entry != null ){
                    String zipEntryName = entry.getName();
                    InputStream in = zip.getInputStream(entry);
                    if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                        fileDir = new File(uploadFolder + "/" + zipEntryName);
                        if(!fileDir.exists()){
                            fileDir.mkdir();
                        }
                        continue;
                    }
                    if(zipEntryName.contains("index.html")){
                        fileName = zipEntryName;
                    }
                    File outZipFile = new File(uploadFolder, zipEntryName);
                    outZipFile.createNewFile();
                    OutputStream out = new FileOutputStream(outZipFile);
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) > 0) {
                        out.write(buff, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
            //删除压缩文件

            File deleteFile = new File(zipFile.getAbsolutePath());
            if (deleteFile.exists()) {
                Boolean flag = deleteFile.delete();
                logger.info("删除压缩文件-----》" + flag);
            }
            return "upload/" + folder + "/" + fileName;
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 更改文件路径
     * @param path
     * @param time
     * @return
     */
    private String changePath(String path,Long time) {
        //想命名的原文件夹的路径
        File oldFile = new File(path);
        //获得文件的上一级文件目录
        String parentPath = oldFile.getParent();
        String childPath = new File(parentPath).getParent();
        //将原文件夹更改为A，其中路径是必要的。注意
        File newFile = new File(childPath);
        newFile.renameTo(new File(newFile.getParent()+"/"+time));
        return "/upload/gns/"+time+"/html/index.html";
    }
}
