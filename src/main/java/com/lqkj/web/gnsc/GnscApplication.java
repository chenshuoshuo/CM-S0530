package com.lqkj.web.gnsc;


import com.lqkj.web.gnsc.config.PropertyDeploy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class GnscApplication {

    public static void main(String[] args) {
        PropertyDeploy deploy=new PropertyDeploy();
        deploy.deploy();
        SpringApplication.run(GnscApplication.class, args);
    }
}
