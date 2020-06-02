package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.HelperTypeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelperType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class HelperTypeService {

    @Autowired
    private HelperTypeDao helperTypeDao;

    public List<GnsHelperType> queryList(Integer schoolId){
        return helperTypeDao.findAllBySchoolId(schoolId);
    }

    public GnsHelperType get(Integer typeCode){
        return helperTypeDao.findById(typeCode).get();
    }

    public GnsHelperType getByTypeName(String typeName){
        return helperTypeDao.findByTypeName(typeName);
    }

    public List<GnsHelperType> saveAll(List<GnsHelperType> helperTypeList){

        return helperTypeDao.saveAll(helperTypeList);
    }

    public Integer delete(Integer typeCode){
        helperTypeDao.deleteById(typeCode);
        return 1;
    }
}
