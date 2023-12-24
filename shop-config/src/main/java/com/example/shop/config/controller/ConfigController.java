package com.example.shop.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 刷新配置
 * @Author zpx
 * @Date 2023/12/24 14:19
 * @Version 1.0
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}
