package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsGroupPhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author cs
 * @Date 2020/6/2 9:29
 * @Version 2.2.2.0
 **/
public interface GnsGroupPhotoDao extends JpaRepository<GnsGroupPhoto, Integer> {

    @Query(nativeQuery = true, value = "select * from gns.gns_group_photo p where p.user_id||'' = :userId" +
            " order by p.create_time desc")
    Page<GnsGroupPhoto> getUserPhotos(@Param("userId") String userId, Pageable pageable);
}
