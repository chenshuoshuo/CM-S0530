package com.lqkj.web.gnsc.modules.gns.service;

import com.lqkj.web.gnsc.modules.gns.dao.GnsGroupPhotoDao;
import com.lqkj.web.gnsc.modules.gns.domain.GnsGroupPhoto;
import com.lqkj.web.gnsc.modules.portal.dao.MapBuildingDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapOthersPolygonDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapPointDao;
import com.lqkj.web.gnsc.modules.portal.dao.MapRoomDao;
import com.lqkj.web.gnsc.modules.portal.model.MapBuilding;
import com.lqkj.web.gnsc.modules.portal.model.MapOthersPolygon;
import com.lqkj.web.gnsc.modules.portal.model.MapPoint;
import com.lqkj.web.gnsc.modules.portal.model.MapRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author cs
 * @Date 2020/6/2 9:30
 * @Version 2.2.2.0
 **/
@Service
@Transactional
public class GnsGroupPhotoService {

    @Autowired
    private GnsGroupPhotoDao photoDao;
    @Autowired
    private MapPointDao pointDao;
    @Autowired
    private MapBuildingDao buildingDao;
    @Autowired
    private MapRoomDao roomDao;
    @Autowired
    private MapOthersPolygonDao othersPolygonDao;

    /**
     * 上传留影
     */
    public GnsGroupPhoto save(String userCode,Integer mapCode,String mapType,String photoUrl){
        String landMarkName = null;

        if("point".equals(mapType) && pointDao.existsByPointCode(mapCode)){
            MapPoint point = pointDao.queryByPointCode(mapCode);
            landMarkName = point.getPointName();
            point.setPohotoTakenCount(point.getPohotoTakenCount() + 1);
            pointDao.save(point);
        }else {
            //获取房间 大楼 其他面信息
            if(buildingDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                MapBuilding building = buildingDao.queryByMapCode(Long.parseLong(mapCode.toString()));
                landMarkName = building.getBuildingName();
                building.setPohotoTakenCount(building.getPohotoTakenCount() + 1);
                buildingDao.save(building);
            }else if(roomDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                MapRoom room = roomDao.queryByMapCode(Long.parseLong(mapCode.toString()));
                landMarkName = room.getRoomName();
                room.setPohotoTakenCount(room.getPohotoTakenCount() + 1);
                roomDao.save(room);
            }else if(othersPolygonDao.existsByMapCode(Long.parseLong(mapCode.toString()))){
                MapOthersPolygon othersPolygon = othersPolygonDao.queryByMapCode(Long.parseLong(mapCode.toString()));
                landMarkName = othersPolygon.getPolygonName();
                othersPolygon.setPohotoTakenCount(othersPolygon.getPohotoTakenCount() + 1);
                othersPolygonDao.save(othersPolygon);
            }
        }
        //上传留影
        GnsGroupPhoto photo = new GnsGroupPhoto(UUID.fromString(userCode),Long.parseLong(mapCode.toString()),landMarkName,mapType,photoUrl);
        return photoDao.save(photo);
    }
}
