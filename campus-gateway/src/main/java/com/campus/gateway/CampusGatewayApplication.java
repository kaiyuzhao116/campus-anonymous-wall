package com.campus.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
@EnableDiscoveryClient // 注册到Nacos
public class CampusGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusGatewayApplication.class, args);
    }
}
