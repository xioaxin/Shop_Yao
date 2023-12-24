package com.example.shop.common.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 返回消息体包装类
 * @Author zpx
 * @Date 2023/12/24 15:39
 * @Version 1.0
 */
@Data
public class R {
    private Integer code;
    private String msg;
    private Map<Object, Object> data;

    public static R ok() {
        R r = new R();
        r.code = 2000;
        return r;
    }

    public static R error(Integer code, String msg) {
        R r = new R();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public static R error() {
        R r = new R();
        r.code = 2001;
        r.msg = "系统异常";
        return r;
    }

    public R put(Object key, Object value) {
        this.put(key, value);
        return this;
    }
}
