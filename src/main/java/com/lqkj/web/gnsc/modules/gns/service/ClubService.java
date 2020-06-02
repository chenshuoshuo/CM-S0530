package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.ClubDao;
import com.lqkj.web.gnsc.modules.gns.dao.ClubVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsClubVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author cs
 */
@Service
@Transactional
public class ClubService {

    @Autowired
    private ClubDao clubDao;
    @Autowired
    private ClubVODao clubVODao;

    public Page<GnsClubVO> page(Integer campusCode,String clubName,Integer page,Integer pageSize){

        Pageable pageable = PageRequest.of(page,pageSize);
        return clubVODao.page(campusCode,clubName,pageable);
    }

    public List<Map<String,Object>> queryList(Integer campusCode){
        return clubDao.queryList(campusCode);
    }

   public GnsClub checkBycClubName(String clubName,Integer campusId){return clubDao.findAllByClubNameAndCampusCode(clubName,campusId);}

    public GnsClub get(Integer clubId){
        GnsClub club = clubDao.findById(clubId).get();
        if(club.getClick() != null){
            club.setClick(club.getClick() + 1);
        } else{
            club.setClick(1);
        }

        return clubDao.save(club);
    }

    public MessageBean add(GnsClub club){
        GnsClub he = this.checkBycClubName(club.getClubName(),club.getCampusCode());
        if(he != null){
            return MessageBean.error("社团名称重复");
        }
        Integer maxOrder = clubDao.getMaxOrder();
        if (maxOrder !=null && club.getOrderId() != null && club.getOrderId() < maxOrder) {
            clubDao.autoOrder(club.getOrderId());
        }else {
            if (maxOrder == null) {
                club.setOrderId(1);
            }else {
                club.setOrderId(maxOrder + 1);
            }
        }
        return MessageBean.ok(clubDao.save(club));
    }

    public MessageBean update(GnsClub club){
        GnsClub he = this.checkBycClubName(club.getClubName(),club.getCampusCode());
        if(he != null && he.getClubId() != club.getClubId()){
            return MessageBean.error("社团名称重复");
        }
        Integer maxOrder = clubDao.getMaxOrder();
        GnsClub oldGnsClub = clubDao.findById(club.getClubId()).get();
        if(oldGnsClub.getOrderId() > club.getOrderId()){
            clubDao.autoOrderForUpdateDesc(oldGnsClub.getOrderId(),club.getOrderId());
        }else {
            if (maxOrder > club.getOrderId() && oldGnsClub.getOrderId() < club.getOrderId()) {
                clubDao.autoOrderForUpdateAsc(oldGnsClub.getOrderId(),club.getOrderId());
            }else if(oldGnsClub.getOrderId() == club.getOrderId()){
                club.setOrderId(oldGnsClub.getOrderId());
            }else{
                clubDao.autoOrderForUpdateAsc(oldGnsClub.getOrderId(),maxOrder);
                club.setOrderId(maxOrder);
            }
        }
        return MessageBean.ok(clubDao.save(club));
    }


    public int delete(Integer helperId){
        GnsClub oldGnsClub = this.get(helperId);
        clubDao.deleteById(helperId);
        //重新排序
        if(oldGnsClub != null){
            clubDao.autoOrderForDelete(oldGnsClub.getOrderId());
        }
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
