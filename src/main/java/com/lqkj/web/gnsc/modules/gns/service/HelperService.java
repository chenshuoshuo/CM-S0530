package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.HelperDao;
import com.lqkj.web.gnsc.modules.gns.dao.HelperVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsHelperVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HelperService {
    @Autowired
    private HelperDao helperDao;
    @Autowired
    private HelperVODao helperVODao;

    public Page<GnsHelperVO> page(Integer typeCode, String title, Integer page, Integer pageSize){

        Pageable pageable = PageRequest.of(page,pageSize);
        return helperVODao.page(typeCode,title,pageable);
    }

    public List<GnsHelper> queryList(Integer categoryId){
        return helperDao.findAllByTypeCode(categoryId);
    }


    public GnsHelper get(Integer helperId){
        return helperDao.findById(helperId).get();
    }

    public GnsHelper checkByContact(String contact){
        return helperDao.findByContact(contact);
    }

    public MessageBean add(GnsHelper helper){
        GnsHelper he = this.checkByContact(helper.getContact());
        if(he != null){
            return MessageBean.error("联系方式已存在");
        }else {
            Integer maxOrder = helperDao.getMaxOrder();
            if (maxOrder !=null && helper.getOrderId() != null && helper.getOrderId() < maxOrder) {
                helperDao.autoOrder(helper.getOrderId());
            }else {
                if (maxOrder == null) {
                    helper.setOrderId(1);
                }else {
                    helper.setOrderId(maxOrder + 1);
                }
            }
            return MessageBean.ok(helperDao.save(helper));
        }
    }

    public MessageBean update(GnsHelper helper){
        GnsHelper he = this.checkByContact(helper.getContact());
        if(he != null && he.getHelperId() != helper.getHelperId()){
            return MessageBean.error("联系方式已存在");
        }
        Integer maxOrder = helperDao.getMaxOrder();
        GnsHelper oldGnsHelper = helperDao.findById(helper.getHelperId()).get();
        if(oldGnsHelper.getOrderId() > helper.getOrderId()){
            helperDao.autoOrderForUpdateDesc(oldGnsHelper.getOrderId(),helper.getOrderId());
        }else {
            if (maxOrder > helper.getOrderId() && oldGnsHelper.getOrderId() < helper.getOrderId()) {
                helperDao.autoOrderForUpdateAsc(oldGnsHelper.getOrderId(),helper.getOrderId());
            }else if(oldGnsHelper.getOrderId() == helper.getOrderId()){
                helper.setOrderId(oldGnsHelper.getOrderId());
            }else{
                helperDao.autoOrderForUpdateAsc(oldGnsHelper.getOrderId(),maxOrder);
                helper.setOrderId(maxOrder);
            }
        }
        return MessageBean.ok(helperDao.save(helper));
    }


    public int delete(Integer helperId){
        GnsHelper helper = this.get(helperId);
        helperDao.deleteById(helperId);
        //重新排序
        if(helper != null){
            helperDao.autoOrderForDelete(helper.getOrderId());
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
