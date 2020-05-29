package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GnsStoreDao extends JpaRepository<GnsStore, Integer>, JpaSpecificationExecutor<GnsStore> {

    GnsStore findByName(String name);

    boolean existsByNameEquals(String name);

    void deleteByNameEquals(String name);
}
