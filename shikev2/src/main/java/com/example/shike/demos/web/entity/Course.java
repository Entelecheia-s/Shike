package com.example.shike.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 课程实体
 * @Author: songshuo
 * @Date: 2025/2/10
 * @Version: V1.0
 */
@Data
@TableName("courses")
@ApiModel(value = "Courses对象", description = "课程表")
public class Course implements Serializable {
    /**
     * 课程ID
     */
    private Integer id;
    /**
     * 课程名称
     */
    private String name;
    /**
     * 授课教师
     */
    private String teacher;
    /**
     * 面向年级
     */
    private String grade;
    /**
     * 面向院系
     */
    private String college;
    /**
     * 面向专业
     */
    private String major;
    /**
     * 课程容量
     */
    private int capacity;
    /**
     * 课程性质
     */
    private String courseNature;
    /**
     * 课程类别
     */
    private String courseType;
    /**
     * 开设学期
     */
    private String semester;
    /**
     * 是否删除
     */
    private boolean deleteStatus;
}
