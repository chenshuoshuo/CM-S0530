package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsStore;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface GnsStoreItemDao extends JpaRepository<GnsStoreItem, Integer>, JpaSpecificationExecutor<GnsStoreItem> {

    /**
     * 判断配置是否存在
     */
    Boolean existsByItemKeyAndStoreIdAndSchoolId (String key,Integer storeId,Integer schoolId);

    /**
     * 删除指定配置
     */
    void deleteByItemKeyAndStoreIdAndSchoolId(String key,Integer storeId,Integer schoolId);

    /**
     * 根据配置名字和key获取
     * @param key
     * @return
     */
    @Query(nativeQuery = true,
        value = "select i.* from gns.gns_store_item i left join gns.gns_store s on s.sore_id = i.sore_id where s.name = :storeName and i.item_key = :key and i.school_id = :schoolId")
    GnsStoreItem findByNameAndKey(String storeName, String key,Integer schoolId);

    /**
     * 根据配置名字和key获取
     * @param key
     * @return
     */
    @Query(nativeQuery = true,
            value = "select i.* from gns.gns_store_item i left join gns.gns_store s on s.sore_id = i.sore_id where s.name = :storeName and i.item_key = :key")
    GnsStoreItem findMapConfig(String storeName, String key);

    /**
     * 根据key获取
     */
    @Query(nativeQuery = true,
            value = "select i.item_key itemKey,i.item_value as itemValue from gns.gns_store_item i where i.item_key = :key and i.school_id = :schoolId")
    Map<String,Object> findByItemKey(String key,Integer schoolId);

    /**
     * 根据配置名称获取
     */
    @Query(nativeQuery = true,
        value = "select i.item_key itemKey,i.item_value as itemValue from gns.gns_store_item i left join gns.gns_store s on s.sore_id = i.sore_id where s.name = :storeName and i.school_id = :schoolId")
    List<Map<String,Object>> findAllByStoreName(String storeName,Integer schoolId);

}
