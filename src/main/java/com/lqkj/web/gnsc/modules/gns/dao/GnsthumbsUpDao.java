package com.lqkj.web.gnsc.modules.gns.dao;

import com.lqkj.web.gnsc.modules.gns.domain.GnsGroupPhoto;
import com.lqkj.web.gnsc.modules.gns.domain.GnsThumbsUp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cs
 * @Date 2020/6/2 9:29
 * @Version 2.2.2.0
 **/
public interface GnsthumbsUpDao extends JpaRepository<GnsThumbsUp,Integer> {
}
