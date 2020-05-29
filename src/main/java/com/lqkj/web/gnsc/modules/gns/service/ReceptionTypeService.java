package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.ReceptionTypeDao;
import com.lqkj.web.gnsc.modules.gns.dao.StudentTypeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReceptionTypeService {
    @Autowired
    private ReceptionTypeDao receptionTypeDao;

    public List<GnsReceptionType> getAll(Integer schoolId){
        return receptionTypeDao.findAllBySchoolId(schoolId);
    }

    public List<GnsReceptionType> saveAll(List<GnsReceptionType> receptionTypeList){

        return receptionTypeDao.saveAll(receptionTypeList);
    }

    public Integer delete(Integer typeCode){
        receptionTypeDao.deleteById(typeCode);
        return 1;
    }
}
