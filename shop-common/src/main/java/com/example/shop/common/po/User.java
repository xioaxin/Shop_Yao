package com.example.shop.common.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Description 用户PO
 * @Author zpx
 * @Date 2023/12/24 10:22
 * @Version 1.0
 */
@Data
@TableName("tb_user")
public class User {
    @TableId
    private Long user_id;           // 用户ID
    private String username;        // 用户名
    private String password;        // 密码
    private String mobile;          // 手机号
    private String email;           // 邮箱
    private Date create_time;       // 创建时间
}
