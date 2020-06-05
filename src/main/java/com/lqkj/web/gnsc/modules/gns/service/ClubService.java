package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.ClubDao;
import com.lqkj.web.gnsc.modules.gns.dao.ClubVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsClub;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsClubVO;
import com.lqkj.web.gnsc.modules.log.service.GnsManageLogService;
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

    @Autowired
    GnsManageLogService logService;

    public Page<GnsClubVO> page(Integer campusCode,String clubName,Integer page,Integer pageSize,String userName){
        logService.addLog("校园社团管理", "page",
                "社团分页查询", userName);
        Pageable pageable = PageRequest.of(page,pageSize);
        return clubVODao.page(campusCode,clubName,pageable);
    }

    public List<Map<String,Object>> queryList(Integer campusCode,String userName){
        logService.addLog("校园社团管理", "query",
                "H5社团列表查询", userName);

        return clubDao.queryList(campusCode);
    }

   public GnsClub checkBycClubName(String clubName,Integer campusId){return clubDao.findAllByClubNameAndCampusCode(clubName,campusId);}

    public GnsClub get(Integer clubId,String userName){
        logService.addLog("校园社团管理", "detail",
                "社团详情查询", userName);
        GnsClub club = clubDao.findById(clubId).get();
        if(club.getClick() != null){
            club.setClick(club.getClick() + 1);
        } else{
            club.setClick(1);
        }

        return clubDao.save(club);
    }

    public MessageBean add(GnsClub club,String userName){
        logService.addLog("校园社团管理", "add",
                "新增社团", userName);
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

    public MessageBean update(GnsClub club,String userName){
        logService.addLog("校园社团管理", "update",
                "更新社团", userName);
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


    public int delete(Integer helperId,String userName){
        logService.addLog("校园社团管理", "delete",
                "删除社团", userName);
        GnsClub oldGnsClub = this.get(helperId,userName);
        clubDao.deleteById(helperId);
        //重新排序
        if(oldGnsClub != null){
            clubDao.autoOrderForDelete(oldGnsClub.getOrderId());
        }
        return 1;
    }

    public int bulkDelete(String ids,String userName){
        logService.addLog("校园社团管理", "delete",
                "批量删除社团", userName);
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s),userName);
        }
        return idArray.length;
    }
}
