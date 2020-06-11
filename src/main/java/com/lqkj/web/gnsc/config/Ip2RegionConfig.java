package com.lqkj.web.gnsc.config;

import org.apache.commons.io.IOUtils;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * ip地址转换器配置
 */
@Configuration
public class Ip2RegionConfig {

    @Bean(destroyMethod = "close")
    public DbSearcher searcher() throws DbMakerConfigException, IOException {
        ClassPathResource classPathResource = new ClassPathResource("location/ip2region.db");

        InputStream is = classPathResource.getInputStream();

        try {
            return new DbSearcher(dbConfig(), IOUtils.toByteArray(classPathResource.getInputStream()));
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private DbConfig dbConfig() throws DbMakerConfigException {
        return new DbConfig(8 * 2048);
    }
}
