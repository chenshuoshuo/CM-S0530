package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsStoreItemDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStore;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class GnsStoreItemService {

    @Autowired
    private GnsStoreDao storeDao;

    @Autowired
    private GnsStoreItemDao storeItemDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 根据名字和key获取
     * @param storeName
     * @param key
     * @return
     */
    public GnsStoreItem getByNameAndKey(String storeName, String key,Integer schoolId) {

        return storeItemDao.findByNameAndKey(storeName,key, schoolId);
    }

    /**
     * 根据学校ID和key获取
     */
    public Object findByItemKey(String itemKey,Integer schoolId){

        return storeItemDao.findByItemKey(itemKey,schoolId);
    }

    /**
     * 根据学校和配置名称获取列表
     */
    public List<Map<String,Object>> findByStoreName(String storeName,Integer schoolId){

        return storeItemDao.findAllByStoreName(storeName,schoolId);
    }


}
