package com.example.shike.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户实体
 * @Author: songshuo
 * @Date: 2025/2/14
 * @Version: V2.0
 */
@Data
@TableName("users")
@ApiModel(value = "users对象", description = "用户表")
public class UserEntity implements Serializable {
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
    /**
     * 是否为超级管理员
     */
    private boolean superAdmin;
}
