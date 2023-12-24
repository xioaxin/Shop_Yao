package com.example.shop.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description 用户应用
 * @Author zpx
 * @Date 2023/12/24 14:26
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.shop.user.mapper")
public class ShopUserApp {
    public static void main(String[] args) {
        SpringApplication.run(ShopUserApp.class, args);
    }
}
