package com.campus.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 无邮箱版用户服务主启动类
 */
@SpringBootApplication
@EnableDiscoveryClient // 注册到Nacos
public class CampusUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusUserApplication.class, args);
    }
}