package com.lqkj.web.gnsc.utils;

import org.apache.commons.lang.StringUtils;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

/**
 * geo工具
 */
public class GeoUtils {
    private static GeometryFactory GEOMETRY_FACTORY = JTSFactoryFinder.getGeometryFactory(null);

    /**
     * 创建点
     * @param lngLatString [经度,纬度]格式
     * @return
     */
    public static Point createPoint(String lngLatString){
        if(StringUtils.isNotEmpty(lngLatString)){
            String[] lngLat = lngLatString.split(",");
            if(lngLat.length == 2){
                Double x = Double.parseDouble(lngLat[0]);
                Double y = Double.parseDouble(lngLat[1]);
                Coordinate coordinate = new Coordinate(x, y);
                return GEOMETRY_FACTORY.createPoint(coordinate);
            }
            return null;
        }
        return null;
    }
}
