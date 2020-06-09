package com.lqkj.web.gnsc.utils;

import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * @ClassName FileUtils
 * @Description: TODO
 * @Author wells
 * @Date 2019/11/29 9:22
 * @Version V1.0
 **/
public class FileUtil {

    public static void dirCopy(File src, String destPath) throws Exception {
        if (!new File(destPath).exists()) {
            new File(destPath).mkdirs();
        }
        for (File s : src.listFiles()) {
            if (s.isFile()) {
                fileCopy(s.getPath(), destPath + File.separator + s.getName());
            } else {
                dirCopy(s, destPath + File.separator + s.getName());
            }
        }
    }

    public static void fileCopy(String srcPath, String destPath) throws IOException {
        File src = new File(srcPath);
        File dest = new File(destPath);
        try (InputStream is = new BufferedInputStream(new FileInputStream(src));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                out.write(flush, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileCopyIn(InputStream srcIn, String destPath, String fileName) {
        if (!new File(destPath).exists()) {
            new File(destPath).mkdirs();
        }
        File dest = new File(destPath + fileName);
        try (
                OutputStream out = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] flush = new byte[1024];
            int len = -1;
            while ((len = srcIn.read(flush)) != -1) {
                out.write(flush, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64File(String base64Code, String targetPath)
            throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 判断是否为图片格式
     * @param fileExtension
     * @return
     */
    public static Boolean isImgFile(String fileExtension){
        if(fileExtension.toLowerCase().contains("jpg") || fileExtension.toLowerCase().contains("jpeg") || fileExtension.toLowerCase().contains("png")
                || fileExtension.contains("svg") || fileExtension.contains("gif")){
            return true;
        }
        return false;
    }
}
