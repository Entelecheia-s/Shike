package com.example.shike.demos.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 登录成功结果封装类
 * @Author: songshuo
 * @Date: 2025/2/11
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    // 登录员工的id
    private Integer id;
    private String param;//手机号或学号
    private String name;
    private String role;
    // 返回的JWT令牌
    private String token;
}