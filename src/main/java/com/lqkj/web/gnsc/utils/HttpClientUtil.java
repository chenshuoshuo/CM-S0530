package com.lqkj.web.gnsc.utils;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpClientUtil {

    // 默认字符集
    private static final String ENCODING = "UTF-8";

    /**
     * @Title: sendPost
     * @Description:
     * @param url 请求地址
     * @param headers 请求头
     * @param encoding 字符集
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendPost(String url, Map<String, String> headers, String jsonData, String encoding) {
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPost httpPost = new HttpPost();

        try {
            // 设置请求地址
            httpPost.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPost.setHeaders(allHeader);
            }
            // 设置实体
            if(jsonData != null){
                httpPost.setEntity(new StringEntity(jsonData));
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPost);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            }
        } catch (Exception e) {
            System.out.println("发送post请求失败");
        } finally {
            httpPost.releaseConnection();
        }
        return resultJson;
    }


    /**
     * @Title: sendPost
     * @throws
     */
    public static String sendPost(String url, String data,String mapToken) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("authorization","Basic Q21HaXNScGM6Q01naXNUISQmKCo=");
        return sendPost(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description:
     * @param url 请求地址
     * @param headers 请求头
     * @param encoding 字符集
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendPut(String url, Map<String, String> headers, String jsonData, String encoding) {
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpPost对象
        HttpPut httpPut = new HttpPut();

        try {
            // 设置请求地址
            httpPut.setURI(new URI(url));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpPut.setHeaders(allHeader);
            }
            // 设置实体
            if(jsonData != null){
                httpPut.setEntity(new StringEntity(jsonData));
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(httpPut);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            }
        } catch (Exception e) {
            System.out.println("发送put请求失败");
        } finally {
            httpPut.releaseConnection();
        }
        return resultJson;
    }

    /**
     * @Title: sendPost
     * @throws
     */
    public static String sendPut(String url, String data,String mapToken) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("authorization", "Basic " + mapToken);
        return sendPut(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendPost
     * @Description:
     * @param url 请求地址
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendDelete(String url,Map<String,String> headers, String jsonData, String encoding) {
        // 请求返回结果
        String resultJson = null;
        // 创建Client
        CloseableHttpClient client = HttpClients.createDefault();

        MyHttpDelete delete = new MyHttpDelete(url);

        try {
            // 设置请求地址
            delete.setEntity(new StringEntity(jsonData));
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                delete.setHeaders(allHeader);
            }
            // 发送请求,返回响应对象
            CloseableHttpResponse response = client.execute(delete);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                // 获取响应结果
                resultJson = EntityUtils.toString(response.getEntity(), ENCODING);
            }
        } catch (Exception e) {
            System.out.println("发送put请求失败");
        } finally {
            delete.releaseConnection();
        }
        return resultJson;
    }

    /**
     * @Title: sendPost
     * @Description:
     * @param url 请求地址
     * @param data 请求实体
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendDelete(String url, String data,String mapToken) {
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("authorization", "Basic " + mapToken);
        return sendDelete(url, headers, data, ENCODING);
    }

    /**
     * @Title: sendGet
     * @Description:
     * @param url 请求地址
     * @param params 请求参数
     * @param encoding 编码
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendGet(String url,Map<String,String> headers,Map<String,Object> params,String encoding){

        // 请求结果
        String resultJson = null;
        // 创建client
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建HttpGet
        HttpGet httpGet = new HttpGet();
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            // 封装参数
            if(params != null){
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key).toString());
                }
            }
            URI uri = builder.build();
            // 设置请求地址
            httpGet.setURI(uri);
            // 设置请求头
            if (headers != null) {
                Header[] allHeader = new BasicHeader[headers.size()];
                int i = 0;
                for (Map.Entry<String, String> entry: headers.entrySet()){
                    allHeader[i] = new BasicHeader(entry.getKey(), entry.getValue());
                    i++;
                }
                httpGet.setHeaders(allHeader);
            }
            // 发送请求，返回响应对象
            CloseableHttpResponse response = client.execute(httpGet);
            // 获取响应状态
            int status = response.getStatusLine().getStatusCode();
            if(status == HttpStatus.SC_OK){
                // 获取响应数据
                resultJson = EntityUtils.toString(response.getEntity(), encoding);
            }
        } catch (Exception e) {
            System.out.println("发送get请求失败");
        } finally {
            httpGet.releaseConnection();
        }
        return resultJson;
    }
    /**
     * @Title: sendGet
     * @Description:
     * @param url 请求地址
     * @param params 请求参数
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendGet(String url,Map<String,Object> params,String mapToken){
        // 设置默认请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "application/json");
        headers.put("authorization", "Basic " + mapToken);
        return sendGet(url,headers,params,ENCODING);
    }
    /**
     * @Title: sendGet
     * @Description:
     * @param url 请求地址
     * @author ts
     * @return String
     * @date 2018/11/8 9:53
     * @throws
     */
    public static String sendGet(String url,Map<String,Object> params){
        return sendGet(url,null,params,ENCODING);
    }

}

