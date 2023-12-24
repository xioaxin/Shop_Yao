package com.example.shop.mino.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description Mino工具类
 * @Author zpx
 * @Date 2023/12/24 15:23
 * @Version 1.0
 */
public class MinoUtils {
    /**
     * 获取当前时间方法
     *
     * @param pattern
     * @return
     */
   public static String getNowDateLongStr(String pattern) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(currentDateTime);
    }
}
