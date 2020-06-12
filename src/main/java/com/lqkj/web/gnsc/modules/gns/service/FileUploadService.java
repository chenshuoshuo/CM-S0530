package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreItemDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.utils.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author cs
 * @Date 2020/6/4 15:40
 * @Version 2.2.2.0
 **/
@Service
public class FileUploadService {
    @Autowired
    private GnsStoreItemService itemService;

    @Value("${web.uploadPath}")
    String uploadPath;



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
            return MessageBean.ok(this.changePath(uploadPath +"/"+ resultUrl, time));
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
            File uploadFolder = new File(uploadPath + "/upload/" + folder + "/");
            if(!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            String imgUrl = uploadPath + "/upload/" + folder + "/" + newFileName;
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
            File uploadFolder = new File(uploadPath + "/upload/" + folder + "/");
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
            File uploadFolder = new File(uploadPath + "/upload/" + folder + "/");
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
            zip.close();
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
        Boolean resultFile = newFile.renameTo(new File(newFile.getParent()+"/"+time));
        logger.info(newFile.getAbsolutePath());
        return "/upload/gns/"+time+"/html/index.html";
    }

    /** 根据指定URL将压缩文件解压到指定目录
     * urlPath  下载路径
     * downloadDir 文件存放目录
	* @return
    */
    public MessageBean synFile(String absolutePath){
        try{
            File file = new File(absolutePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            File uploadFolder = new File("./upload/");
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            File zipFile = new File(uploadFolder,file.getName());
            FileUtils.copyInputStreamToFile(fileInputStream, zipFile);
            ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
            File fileDir = null;
            for (Enumeration enumeration = zip.entries(); enumeration.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) enumeration.nextElement();
                if(entry != null ){
                    String zipEntryName = entry.getName();
                    InputStream in = zip.getInputStream(entry);
                    if (entry.isDirectory()) {      //处理压缩文件包含文件夹的情况
                        fileDir = new File( "./" + zipEntryName);
                        if(!fileDir.exists()){
                            fileDir.mkdir();
                        }
                        continue;
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
            zip.close();
            //删除压缩文件

            File deleteFile = new File(zipFile.getAbsolutePath());
            logger.info(zipFile.getAbsolutePath());
            if (deleteFile.exists()) {
                Boolean flag = deleteFile.delete();
                logger.info("删除压缩文件-----》" + flag);
            }
            return MessageBean.ok("同步成功");
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return MessageBean.error("同步失败");
        }
    }

    /**
     * 压缩指定目录下文件
     * @param fileName :压缩后文件的名称
     * @return 返回压缩后文件绝对路径
     */
    public String fileToZip(String fileName){
        FileOutputStream fos = null;
        ZipOutputStream zipOut = null;
        //指定压缩后文件存放路径
        String zipFilePath = System.getProperty("user.dir")+ File.separator + "zip" + File.separator + fileName + ".zip" ;
        //获取需要压缩的文件路劲
        String sourceFilePath = System.getProperty("user.dir") + File.separator  + "upload" +  File.separator;
        //获取需要压缩文件的文件数量
        int parentDirectoryLen=sourceFilePath.lastIndexOf(File.separator) + 1;
        File zipFile = new File(zipFilePath);
        // 校验文件夹目录是否存在，不存在就创建一个目录
        if (!zipFile.getParentFile().exists()) {
            zipFile.getParentFile().mkdirs();
        }
        File[] copyfoldersList = new File(sourceFilePath).listFiles();

        try {
            fos = new FileOutputStream(zipFilePath);
            zipOut = new ZipOutputStream(fos);
            if(copyfoldersList != null && copyfoldersList.length > 1){
                for (int i = 0; i < copyfoldersList.length; i++) {
                    if (copyfoldersList[i].isDirectory()) {
                        LinkedList<String> copysourcepath = new LinkedList<String>(Arrays.asList(copyfoldersList[i].getAbsolutePath()));
                        while (copysourcepath.size() > 0) {
                            File folders = new File(copysourcepath.peek());
                            String[] file = folders.list();
                            if(file != null && file.length > 1 ){
                                for (int j = 0; j < file.length; j++) {
                                    File ff = new File(copysourcepath.peek(), file[j]);
                                    if (ff.isFile()) {
                                        FileInputStream fis =null;
                                        fis = new FileInputStream(ff);
                                        ZipEntry entry = new ZipEntry(ff.getAbsoluteFile().getAbsolutePath().substring(parentDirectoryLen));
                                        zipOut.putNextEntry(entry);
                                        int nNumber;
                                        byte[] buffer = new byte[1024*10];
                                        while ((nNumber = fis.read(buffer)) != -1)
                                            zipOut.write(buffer, 0, nNumber);
                                    } else if (ff.isDirectory()) {
                                        if(ff.listFiles() != null){
                                            for (File f : ff.listFiles()) {
                                                if (f.isDirectory())
                                                    copysourcepath.add(f.getPath());
                                                else if (f.isFile()) {
                                                    FileInputStream fis =null;
                                                    fis = new FileInputStream(f);
                                                    ZipEntry entry = new ZipEntry(f.getAbsoluteFile().getAbsolutePath().substring(parentDirectoryLen));
                                                    zipOut.putNextEntry(entry);
                                                    int nNumber;
                                                    byte[] buffer = new byte[1024*10];
                                                    while ((nNumber = fis.read(buffer)) != -1)
                                                        zipOut.write(buffer, 0, nNumber);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            copysourcepath.removeFirst();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                 zipOut.close();
                 fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  zipFilePath;
    }
}
