package com.lqkj.web.gnsc.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;
import org.opengis.geometry.BoundingBox;

import java.io.IOException;

/**
 * jackson支持jts空间对象
 */
public class JacksonGeometrySerializer extends JsonSerializer<Geometry> {

    /**
     * 序列化
     */
    @Override
    public void serialize(Geometry value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        String geojson = GeoJSON.gjson.toString((BoundingBox) value);

        gen.writeRawValue(geojson);
    }
}
