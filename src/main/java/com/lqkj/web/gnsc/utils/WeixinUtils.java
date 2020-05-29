package com.lqkj.web.gnsc.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeixinUtils {


    /**
     * 获取用户信息
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     */

    public static JSONObject getUserInfo(String appid, String secret, String code) throws Exception {
        String get_openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String get_info_url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,Object> para = new HashMap<>();
        para.put("appid", appid);
        para.put("secret", secret);
        para.put("code", code);
        para.put("grant_type", "authorization_code");
        String tokenStr = HttpClientUtil.sendGet(get_openid_url, para);
        JSONObject tokenJSON = JSONObject.parseObject(tokenStr);
        if(tokenJSON.containsKey("errcode")){
            return tokenJSON;
        }
        String accessToken = tokenJSON.getString("access_token");
        String openid = tokenJSON.getString("openid");
        para.clear();
        para.put("access_token", accessToken);
        para.put("openid", openid);
        para.put("lang", "zh_CN");
        String sendGet = HttpClientUtil.sendGet(get_info_url, para);
        JSONObject jsonObject = JSONObject.parseObject(sendGet);
        return jsonObject;
    }


}
