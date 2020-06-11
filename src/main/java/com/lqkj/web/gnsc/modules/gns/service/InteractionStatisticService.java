package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.FeedbackDao;
import com.lqkj.web.gnsc.modules.gns.dao.GnsUserInfoDao;
import com.lqkj.web.gnsc.modules.gns.dao.InteractionStatisticDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsInteractionStatistic;
import com.lqkj.web.gnsc.modules.gns.domain.GnsUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/1 19:53
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class InteractionStatisticService {
    @Autowired
    private InteractionStatisticDao interactionStatisticDao;

    public GnsInteractionStatistic save(Integer schoolId,String name) {
        GnsInteractionStatistic interaction = interactionStatisticDao.findBySchoolIdAndStatisticName(schoolId,name);
        if(interaction != null){
            interaction.setStatisticData(interaction.getStatisticData() + 1);
        }else {
            interaction = new GnsInteractionStatistic(schoolId,name,1);
        }
        return interactionStatisticDao.save(interaction);
    }
}
