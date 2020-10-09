package com.lqkj.web.gnsc.modules.gns.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.lqkj.web.gnsc.message.MessageBean;
import com.lqkj.web.gnsc.modules.gns.dao.ReceptionPlaceDao;
import com.lqkj.web.gnsc.modules.gns.dao.ReceptionPlaceVODao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelper;
import com.lqkj.web.gnsc.modules.gns.domain.GnsHelperType;
import com.lqkj.web.gnsc.modules.gns.domain.GnsReceptionPlace;
import com.lqkj.web.gnsc.modules.gns.domain.GnsRegisteringNotice;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsGuideVO;
import com.lqkj.web.gnsc.modules.gns.domain.vo.GnsReceptionPlaceVO;
import com.lqkj.web.gnsc.utils.DataImportLog;
import com.lqkj.web.gnsc.utils.ExcelModel;
import com.lqkj.web.gnsc.utils.ExcelUtils;
import com.lqkj.web.gnsc.utils.GeoJSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public Page<GnsReceptionPlaceVO> page(Integer schoolId,Integer campusCode, Integer typeCode, String title, Integer page, Integer pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return receptionPlaceVODao.page(schoolId,campusCode,typeCode, title, pageable);
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

    /**
     * 导出
     * @return
     */
    public ResponseEntity<InputStreamResource> download(Integer schoolId) throws IOException {
        List<GnsReceptionPlace> receptionPlaceList = receptionPlaceDao.findAllBySchoolId(schoolId);
        return ExcelUtils.downloadOneSheetExcel(GnsReceptionPlace.class, receptionPlaceList, "迎新接待点", "迎新接待点.xlsx");
    }

    public Object upload(InputStream inputStream, Integer schoolId) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {

        List<Object> dataList = ExcelUtils.readOneSheetExcel(inputStream, GnsReceptionPlace.class);
        DataImportLog dataImportLog = new DataImportLog("迎新接待点导入", dataList.size());
        List<GnsReceptionPlace> list = new ArrayList<>();
        for (int i = 0; i < dataImportLog.getTotalCount(); i++) {
            GnsReceptionPlace place = (GnsReceptionPlace) dataList.get(i);
            String errMsg = "";

            if (place.getTypeCode() == null || !StringUtils.isNumeric(place.getTypeCode().toString())) {
                errMsg += "分类编号不能为空且只能是数字；";
            }

            if (place.getCampusCode() == null || !StringUtils.isNumeric(place.getCampusCode().toString())) {
                errMsg += "校区区域组编号不能为空且只能是数字；";
            }

            if (place.getTitle() == null) {
                errMsg += "接待点名称不能为空；";
            }

            if(place.getLngLatString() != null){
                place.setLngLat(null);
            }

            if (errMsg.length() > 0) {
                Map<String, Object> errMap = new HashMap<>();
                errMap.put("errMsg", errMsg);
                dataImportLog.addError(false, errMap);
            } else {
                list.add(place);
            }
        }
        List<String> errMsgList = new ArrayList<>();
        list.forEach(v ->{
            MessageBean messageBean = this.add(v);
            String errMsg = "";
            if(!messageBean.isStatus()){
                errMsg =  v.getTitle() + messageBean.getMessage();
                errMsgList.add(errMsg);
            }
        });
        if(errMsgList.size() > 0){
            dataImportLog.addError(false,errMsgList);
            dataImportLog.setErrorCount(errMsgList.size());
        }
        return dataImportLog;
    }

}
