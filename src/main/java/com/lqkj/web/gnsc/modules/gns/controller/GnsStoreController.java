package com.lqkj.web.gnsc.modules.gns.controller;

import com.lqkj.web.gnsc.APIVersion;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.message.MessageListBean;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStore;
import com.lqkj.web.gnsc.modules.gns.service.GnsStoreItemService;
import com.lqkj.web.gnsc.modules.gns.service.GnsStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 配置信息
 */
@Api(tags = "产品配置")
@RestController
@RequestMapping("/gns/store/")
public class GnsStoreController {

    @Autowired
    private GnsStoreItemService storeItemService;

    @Autowired
    private GnsStoreService storeService;

    @PostMapping( APIVersion.V1 + "/put")
    @ApiOperation("保存产品配置")
    public MessageBean put(@ApiParam(name="schoolId",value="学校ID,地理信息中cmips,cmgis,token保存时不需要填写学校ID",required=false) @RequestParam(name = "schoolId",required = false) Integer schoolId,
                                 @RequestParam(name = "storeName") String storeName,
                                 @RequestParam(name = "itemKey") String itemKey,
                                 @RequestParam(name = "itemValue") String itemValue,
                                 @RequestParam(required = false, defaultValue = MediaType.APPLICATION_JSON_VALUE)
                                         String contentType,
                                 @ApiIgnore Authentication authentication) {

        return MessageBean.ok(storeService.put(schoolId,storeName, itemKey, itemValue, contentType));
    }

    @DeleteMapping(APIVersion.V1 + "/remove")
    @ApiOperation("删除产品配置")
    public MessageBean remove(@RequestParam(name = "storeName") String storeName,
                              @RequestParam(name = "itemKey") String itemKey,
                              @RequestParam(name = "schoolId") Integer schoolId,
                              @ApiIgnore Authentication authentication) {

        storeService.remove(storeName, itemKey, schoolId);
        return MessageBean.ok();
    }

    /**
     * 获取单个产品配置参数
     * @param itemKey
     * @return
     */
    @GetMapping(APIVersion.V1 + "/query")
    @ApiOperation("获取指定产品配置信息")
    public MessageBean queryConfiguration(@RequestParam(name = "schoolId") Integer schoolId, @RequestParam(name = "itemKey") String itemKey) {

        return MessageBean.ok(storeItemService.findByItemKey(itemKey, schoolId));
    }

    /**
     * 获取分类下产品配置参数列表
     * @param storeName
     * @return
     */
    @GetMapping(APIVersion.V1 + "/queryList")
    @ApiOperation("获取分类下产品配置列表")
    public MessageListBean queryListConfiguration(@RequestParam(name = "storeName") String storeName,
                                                  @RequestParam(name = "schoolId") Integer schoolId) {

        return MessageListBean.ok(storeItemService.findByStoreName(storeName,schoolId));
    }


}