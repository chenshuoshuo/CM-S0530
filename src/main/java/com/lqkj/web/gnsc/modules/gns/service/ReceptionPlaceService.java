package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.ReceptionPlaceDao;
import com.lqkj.web.gnsc.modules.gns.dao.ReceptionPlaceVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionPlace;
import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsReceptionPlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceptionPlaceService {
    @Autowired
    private ReceptionPlaceDao receptionPlaceDao;
    @Autowired
    private ReceptionPlaceVODao receptionPlaceVODao;

    /**
     * 分页
     * @param typeCode
     * @param campusCode
     * @return
     */
    public Page<GnsReceptionPlaceVO> page(Integer campusCode, Integer typeCode, String title, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return receptionPlaceVODao.page(campusCode,typeCode, title, pageable);
    }

    /**
     * H5获取接待点列表
     * @param typeCode
     * @return
     */
    public List<GnsReceptionPlace> queryList(Integer typeCode){
        return receptionPlaceDao.queryList(typeCode);
    }

    public GnsReceptionPlace checkIsExistWithCampusId(String title, Integer campusCode){
        return receptionPlaceDao.findByTitleAndCampusCode(title, campusCode);
    }

    public MessageBean add(GnsReceptionPlace receptionPlace){
        GnsReceptionPlace place = this.checkIsExistWithCampusId(receptionPlace.getTitle(),receptionPlace.getCampusCode());
        if(place != null){
            return MessageBean.error("名称重复");
        }
        return MessageBean.ok(receptionPlaceDao.save(receptionPlace));
    }

    public GnsReceptionPlace get(Integer replaceId){
        return receptionPlaceDao.findById(replaceId).get();
    }

    public MessageBean update(GnsReceptionPlace receptionPlace){
        GnsReceptionPlace place = this.checkIsExistWithCampusId(receptionPlace.getTitle(),receptionPlace.getCampusCode());
        if(place != null && place.getPlaceId() != receptionPlace.getPlaceId()){
            return MessageBean.error("名称重复");
        }
        return MessageBean.ok(receptionPlaceDao.save(receptionPlace));
    }

    public int delete(Integer placeId){
        receptionPlaceDao.deleteById(placeId);
        return 1;
    }

    public int bulkDelete(String ids){
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }


}
