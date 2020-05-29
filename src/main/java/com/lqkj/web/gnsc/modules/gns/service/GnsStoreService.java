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

import java.util.List;

@Service
@Transactional
public class GnsStoreService{

    @Autowired
    private GnsStoreDao storeDao;

    @Autowired
    private GnsStoreItemDao storeItemDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 插入内容
     *
     * @param storeName 储存名称
     * @param key       键名
     * @param value     键值
     */
    public GnsStoreItem put(Integer schoolId,String storeName, String key, String value, String contentType) {

        GnsStore store = createOrGetStore(storeName);
        return putStoreItem(schoolId,store, key, value, contentType);
    }

    /**
     * 删除内容
     *
     * @param storeName 储存名称
     * @param key       键名
     */
    public void remove(String storeName, String key,Integer schoolId) {

        if (storeDao.existsByNameEquals(storeName)) {
            GnsStore store = storeDao.findByName(storeName);
            storeItemDao.deleteByItemKeyAndStoreIdAndSchoolId(key, store.getId(),schoolId);
        }
    }

    /**
     * 得到或者创建储存对象
     *
     * @param storeName 储存名称
     * @return id
     */
    private GnsStore createOrGetStore(String storeName) {
        GnsStore store;

        if (storeDao.existsByNameEquals(storeName)) {
            store = storeDao.findByName(storeName);
        } else {
            store = storeDao.save(new GnsStore(storeName));
        }
        return store;
    }

    /**
     * 保存项
     *
     * @param store 储存对象
     * @param key   键名
     * @param value 键值
     * @return id
     */
    private GnsStoreItem putStoreItem(Integer schoolId,GnsStore store, String key, String value, String contentType) {
        GnsStoreItem storeItem = new GnsStoreItem(schoolId,key,value,store.getId(),contentType);

        if (storeItemDao.existsByItemKeyAndStoreIdAndSchoolId(key, store.getId(),schoolId)) {
            storeItemDao.deleteByItemKeyAndStoreIdAndSchoolId(key, store.getId(),schoolId);
        }
        return storeItemDao.save(storeItem);
    }


    /**
     * 根据名字查询
     * @param name
     * @return
     */
    public GnsStore getByName(String name) {
        return storeDao.findByName(name);
    }

}
