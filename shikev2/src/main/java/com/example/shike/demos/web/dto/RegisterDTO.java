package com.example.shike.demos.web.dto;

import lombok.Data;

/**
 * @Description: 注册DTO
 * @Author: songshuo
 * @Date: 2025/2/15
 * @Version: V1.0
 */
@Data
public class RegisterDTO {
    private String phoneNo;
    private String password;
    private String name;
    private String gender;
    private String email;
    private String college;
    private String grade;
    private String major;
    private String role;
}
