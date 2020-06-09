package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import com.lqkj.web.gnsc.utils.FaceAppUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

@Service
public class GnsMergeImgService {

    @Autowired
    private GnsStoreItemService storeItemService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageBean<Object> mergeface(Integer schoolId,String backGroundPhotoUrl, String mergeImgPath) {

        GnsStoreItem faceApiKeyItem = storeItemService.getByNameAndKey("otherConfigurations", "faceApiKey",schoolId);
        if(faceApiKeyItem == null || StringUtils.isBlank(faceApiKeyItem.getItemValue())){
            return MessageBean.error("apiKey参数值错误");
        }
        GnsStoreItem faceApiSecretItem = storeItemService.getByNameAndKey("otherConfigurations", "faceApiSecret",schoolId);
        if(faceApiSecretItem == null || StringUtils.isBlank(faceApiSecretItem.getItemValue())){
            return MessageBean.error("apiSecret参数值错误");
        }
        try {
            String mergeImg = FaceAppUtil.mergeImg(backGroundPhotoUrl, mergeImgPath,faceApiKeyItem.getItemValue(),faceApiSecretItem.getItemValue());
            if(StringUtils.isNotBlank(mergeImg)){
                File file = new File("./" + mergeImg);
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(file));
                HashMap<String, Object> retMap = new HashMap<>();
                retMap.put("mergeImgPath",mergeImg);
                retMap.put("mergeImgWidth",sourceImg.getWidth());
                retMap.put("mergeImgHeight",sourceImg.getHeight());
                return MessageBean.ok(retMap);
            }
            return MessageBean.error("图片融合失败");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return MessageBean.error(e.getMessage());
        }
    }

    public MessageBean<Object> humanBodySegment(Integer schoolId,String mergeImgPath){
        GnsStoreItem faceApiKeyItem = storeItemService.getByNameAndKey("otherConfigurations", "faceApiKey",schoolId);
        if(faceApiKeyItem == null || StringUtils.isBlank(faceApiKeyItem.getItemValue())){
            return MessageBean.error("apiKey参数值错误");
        }
        GnsStoreItem faceApiSecretItem = storeItemService.getByNameAndKey("otherConfigurations", "faceApiSecret",schoolId);
        if(faceApiSecretItem == null || StringUtils.isBlank(faceApiSecretItem.getItemValue())){
            return MessageBean.error("apiSecret参数值错误");
        }
        if(mergeImgPath.indexOf("/") != 0){
            mergeImgPath = "/"+mergeImgPath;
        }
        try {
            String mergeImg = FaceAppUtil.humanBodySegment(mergeImgPath,faceApiKeyItem.getItemValue(),faceApiSecretItem.getItemValue());
            if(StringUtils.isNotBlank(mergeImg)){
                File file = new File("./" + mergeImg);
                BufferedImage sourceImg = ImageIO.read(new FileInputStream(file));
                HashMap<String, Object> retMap = new HashMap<>();
                retMap.put("mergeImgPath",mergeImg);
                retMap.put("mergeImgWidth",sourceImg.getWidth());
                retMap.put("mergeImgHeight",sourceImg.getHeight());
                return MessageBean.ok(retMap);
            }
            return MessageBean.error("人体识别失败");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return MessageBean.error(e.getMessage());
        }
    }
}
