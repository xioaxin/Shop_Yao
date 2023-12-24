package com.example.shop.mino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description Mino OSS文件服务
 * @Author zpx
 * @Date 2023/12/24 15:00
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MinoApp {
    public static void main(String[] args) {
        SpringApplication.run(MinoApp.class, args);
    }
}
