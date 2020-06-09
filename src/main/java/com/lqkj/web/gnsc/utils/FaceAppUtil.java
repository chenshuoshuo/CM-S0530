package com.lqkj.web.gnsc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class FaceAppUtil {

    private static Logger logger = LoggerFactory.getLogger(FaceAppUtil.class);

    private static final String MERGE_URL = "https://api-cn.faceplusplus.com/imagepp/v1/mergeface";

    private static final String SEGMENT_URL = "https://api-cn.faceplusplus.com/humanbodypp/v2/segment";


    public static String mergeImg(String temImgPath,String mergeImgPath, String apiKey, String apiSecret) throws IOException{
        Date date = new Date();
        String temBase = FaceAppUtil.ImageToBase64("." + temImgPath);
        String mergeBase = FaceAppUtil.ImageToBase64("." + mergeImgPath);
        logger.info("人脸融合转BASE64："+(new Date().getTime()-date.getTime()));
        date = new Date();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("api_key", apiKey);
        paramMap.put("api_secret", apiSecret);
        paramMap.put("template_base64", ""+temBase);
        paramMap.put("merge_base64", mergeBase);
        paramMap.put("merge_rate", 100);
        paramMap.put("feature_rate", 100);
        String result = FaceAppUtil.doPost(MERGE_URL, paramMap);
        logger.info("Face++返回："+result);
        logger.info("人脸融合请求时间："+(new Date().getTime()-date.getTime()));
        date = new Date();
        if(result.contains("error_message")){
            return "";
        }
        String faceImgBase64 = JSON.parseObject(result).getString("result");
        FaceAppUtil.createFile("./upload/gns/merge/");
        String mergeResultImg = "./upload/gns/merge/"+UUID.randomUUID().toString().replace("-","")+".jpg";
        String transferAlphaImg = "./upload/gns/merge/"+UUID.randomUUID().toString().replace("-","")+".png";
        FaceAppUtil.generateImage(faceImgBase64, mergeResultImg);
        boolean alpha2File = Alpha.transferAlpha2File(mergeResultImg, transferAlphaImg);
        logger.info("人脸融合处理背景："+(new Date().getTime()-date.getTime()));
        if(alpha2File){
            return transferAlphaImg.substring(1);
        }
        return "";
    }

    public static String humanBodySegment(String imgPath, String apiKey, String apiSecret) throws Exception{
        HashMap<String, Object> paramMap = new HashMap<>();
        FaceAppUtil.createFile("./upload/gns/merge/");
        String mergeResultImg = "./upload/gns/merge/"+UUID.randomUUID().toString().replace("-","")+".png";
        String imgageBase = FaceAppUtil.ImageToBase64("."+imgPath);

        //paramMap.put("api_key", "oZ6nQ3rBLuFb1yrpcSNlWa5r55kMNJMQ");
        paramMap.put("api_key", apiKey);
        //paramMap.put("api_secret", "NitkqZ-F6KbDrYWM_TQiIMbGFWCMv2Y4");
        paramMap.put("api_secret", apiSecret);
        paramMap.put("image_base64", imgageBase);
        paramMap.put("return_grayscale", 0);
        String result = FaceAppUtil.doPost(SEGMENT_URL, paramMap);
        JSONObject retJson = JSON.parseObject(result);
        logger.info("抠图结果:"+result);
        if(retJson.containsKey("body_image")){
            String baseB64Image = retJson.getString("body_image");
            FaceAppUtil.generateImage(baseB64Image, mergeResultImg);
            return mergeResultImg.substring(1);
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
       // FaceAppUtil.humanBodySegment();
    }

    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
     */

    private static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(Objects.requireNonNull(data));
    }

    /**
     * base64字符串转化成图片
     *
     * @param imgData
     *            图片编码
     * @param imgFilePath
     *            存放到本地路径
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static boolean generateImage(String imgData, String imgFilePath) throws IOException { // 对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgFilePath);
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
            return true;
        }
    }

    public static String doPost(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();



            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void createFile(String fileImgPath){
        File targetFile = new File(fileImgPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }



}
