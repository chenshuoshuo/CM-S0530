package com.lqkj.web.gnsc.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * jackson支持反序列化jts空间对象
 */
public class JacksonGeometryDeserializer extends JsonDeserializer<org.locationtech.jts.geom.Geometry> {
    @Override
    public org.locationtech.jts.geom.Geometry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String geojsonString = p.getCodec().readTree(p).toString();

        if (geojsonString!=null) {
            return GeoJSON.gjson.read(geojsonString);
        }

        return null;
    }
}
