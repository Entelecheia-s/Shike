package com.example.shike.demos.web.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户展示实体")
public class UserVO {
    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户学号/工号
     */
    private String studentNo;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户手机号
     */
    private String phoneNo;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户院系
     */
    private String college;
    /**
     * 用户角色
     */
    private String role;
    /**
     * 学生年级
     */
    private String grade;
    /**
     * 学生专业
     */
    private String major;
}
