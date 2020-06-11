package com.lqkj.web.gnsc.modules.portal.controller;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.portal.service.PortalInitService;
import com.lqkj.web.gnsc.utils.DataSynLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 数据初始controller
 * 实现从CMGIS同步数据到CMDBE的功能
 */
@RestController
@RequestMapping("/portalInit")
@Api(tags = "portal-cmdbe/cmgis数据同步")
public class PortalInitController {
    @Autowired
    PortalInitService portalInitService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据校区ID（二维）初始数据
     * @param zoneId 校区ID
     * @return
     */
    @GetMapping("/init/{zoneId}")
    @ApiOperation("根据校区ID（二维）初始数据")
    public MessageBean<DataSynLog> init(@PathVariable(name = "zoneId") Integer zoneId) throws DocumentException {
        try {
            portalInitService.initCategory();
            return MessageBean.construct(portalInitService.initPublicData(zoneId),"根据校区ID（二维）初始数据");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return MessageBean.error(e.getMessage());
        }
    }

    /**
     * 初始所有数据
     * @return
     */
    @GetMapping("/initAll")
    @ApiOperation("初始所有数据")
    public MessageListBean<DataSynLog> initAll() throws DocumentException {
        try {
            portalInitService.initCategory();
            return MessageListBean.construct(portalInitService.initAllPublicData(),"初始所有数据");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return MessageListBean.error(e.getMessage());
        }
    }

    /**
     * 根据校区ID推送数据到CMGIS
     * @return
     */
    @PostMapping("/pushDataToGis/{zoneId}")
    @ApiOperation("根据校区ID推送数据到CMGIS")
    public MessageBean<DataSynLog> pushDataToGis(@PathVariable(name = "zoneId") Integer zoneId) {
        logger.info("推送数据到cmgis:zoneId:"+zoneId);
        try {
            DataSynLog dataSynLog = portalInitService.pushDataToGis(zoneId);
            return MessageBean.construct(dataSynLog,"根据校区ID推送数据到CMGIS");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return MessageBean.error(e.getMessage());
        }
    }

    /**
     * 推送数据到CMGIS
     * @return
     */
    @PostMapping("/pushAllDataToGis")
    @ApiOperation("推送所有数据到CMGIS")
    public MessageListBean<DataSynLog> pushAllDataToGis() throws DocumentException {
        try {
            return MessageListBean.construct(portalInitService.pushAllDataToGis(),"推送数据到CMGIS");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return MessageListBean.error(e.getMessage());
        }
    }

    /**
     * 初始分类
     */
    @PostMapping("/initCategory")
    @ApiOperation("初始化分类")
    public MessageBean initCategory() throws DocumentException {
        portalInitService.initCategory();
        return MessageBean.ok();
    }
}
