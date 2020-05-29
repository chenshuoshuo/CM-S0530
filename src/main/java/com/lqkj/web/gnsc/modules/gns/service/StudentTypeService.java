package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.StudentTypeDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsStudentType;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentTypeService {
    @Autowired
    private StudentTypeDao studentTypeDao;

    public List<GnsStudentType> getAll(Integer schoolId){
        return studentTypeDao.findAllBySchoolId(schoolId);
    }

    public List<GnsStudentType> loadStudentTypeWithGuide(Integer schoolId){
        return studentTypeDao.loadStudentTypeWithGuide(schoolId);
    }

    public GnsStudentType loadDefault(Integer schoolId){
        return studentTypeDao.loadDefaultStudentType(schoolId);
    }

    public List<GnsStudentType> saveAll(List<GnsStudentType> studentTypeList){

        if(studentTypeList.size() >0){
            for (int i = 0; i < studentTypeList.size(); i++) {
                if(i == 0){
                    studentTypeList.get(i).setDefaultType(true);
                }else {
                    studentTypeList.get(i).setDefaultType(false);
                }
            }
        }
        return studentTypeDao.saveAll(studentTypeList);
    }

    public Integer delete(Integer typeCode){
        studentTypeDao.deleteById(typeCode);
        return 1;
    }
}
