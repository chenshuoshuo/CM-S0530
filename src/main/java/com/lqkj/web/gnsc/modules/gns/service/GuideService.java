package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.GuideDao;
import com.lqkj.web.gnsc.modules.gns.dao.GuideVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsApplication;
import com.lqkj.web.gnsc.modules.gns.domain.GnsGuide;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GuideService {
    @Autowired
    private GuideDao guideDao;
    @Autowired
    private GuideVODao guideVODao;

    /**
     * 分页获取
     * @param title
     * @param page
     * @param pageSize
     * @return
     */
    public Page<GnsGuideVO> page(Integer campusCode, Integer typeCode, String title, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return guideVODao.page(campusCode,typeCode, title, pageable);
    }

    /**
     * 根据校区和学生分类获取
     * @return
     */
    public List<GnsGuide> queryAll(Integer campusCode,Integer typeCode){return guideDao.findAllByCampusCodeAndAndStudnetTypeCode(campusCode,typeCode);}

    /**
     * 根据主键获取
     * @param guideId
     * @return
     */
    public GnsGuide get(Integer guideId){
        return guideDao.findById(guideId).get();
    }

    public GnsGuide checkIsExistWithCampusId( String guideName, Integer campusId){
        return guideDao.checkIsExistWithCampusId(guideName, campusId);
    }

    /**
     * 新增
     * @param guide
     * @return
     */
    public GnsGuide add(GnsGuide guide){
        //增加自动排序
        Integer maxOrder = guideDao.getMaxOrder(guide.getCampusCode());
        if (maxOrder !=null && guide.getOrderId() < maxOrder) {
            guideDao.autoOrder(guide.getOrderId(),guide.getCampusCode());
        }else {
            if (maxOrder == null) {
                guide.setOrderId(1);
            }else {
                guide.setOrderId(maxOrder + 1);
            }
        }
        return guideDao.save(guide);
    }

    /**
     * 更新
     * @param guide
     * @return
     */
    public GnsGuide update(GnsGuide guide){
        Integer maxOrder = guideDao.getMaxOrder(guide.getCampusCode());
        GnsGuide oldGnsGuide = guideDao.findById(guide.getGuideId()).get();
        if(oldGnsGuide.getOrderId() > guide.getOrderId()){
            guideDao.autoOrderForUpdateDesc(oldGnsGuide.getOrderId(),guide.getOrderId(),guide.getCampusCode());
        }else {
            if (maxOrder > guide.getOrderId() && oldGnsGuide.getOrderId() < guide.getOrderId()) {
                guideDao.autoOrderForUpdateAsc(oldGnsGuide.getOrderId(),guide.getOrderId(),guide.getCampusCode());
            }else if(oldGnsGuide.getOrderId() == guide.getOrderId()){
                guide.setOrderId(oldGnsGuide.getOrderId());
            }else{
                guideDao.autoOrderForUpdateAsc(oldGnsGuide.getOrderId(),maxOrder,guide.getCampusCode());
                guide.setOrderId(maxOrder);
            }
        }
        return guideDao.save(guide);
    }

    /**
     * 删除
     * @param guideId
     * @return
     */
    public int delete(Integer guideId){
        GnsGuide oldGnsGuide = this.get(guideId);
        guideDao.deleteById(guideId);
        //重新排序
        if(oldGnsGuide != null){
            guideDao.autoOrderForDelete(oldGnsGuide.getOrderId(),oldGnsGuide.getCampusCode());
        }
        return 1;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int bulkDelete(String ids){
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            this.delete(Integer.parseInt(s));
        }
        return idArray.length;
    }
}
