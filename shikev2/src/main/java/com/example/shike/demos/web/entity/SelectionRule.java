package com.example.shike.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 选课规则实体
 * @Author: songshuo
 * @Date: 2025/2/25
 * @Version: V1.0
 */
@Data
@TableName("selection_rules")
@ApiModel(value = "selection_rules对象", description = "选课规则表")
public class SelectionRule {
    /**
     * ID
     */
    private Integer id;
    /**
     * 课程类别
     */
    private String courseType;
    /**
     * 课程名称
     */
    private String courseName;
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
     * 选课起始时间
     */
    private LocalDateTime selectionStartTime;
    /**
     * 选课截止时间
     */
    private LocalDateTime selectionDeadline;
    /**
     * 限选数量
     */
    private Integer number;
}
