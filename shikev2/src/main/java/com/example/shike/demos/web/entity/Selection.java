package com.example.shike.demos.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 学生选课
 * @Author: songshuo
 * @Date: 2025/2/16
 * @Version: V1.0
 */
@Data
@TableName("selections")
@ApiModel(value = "selections对象", description = "学生选课表")
public class Selection implements Serializable {
    /**
     * 学生选课ID
     */
    private Integer id;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 是否取消选课
     */
    private boolean withDraw;
    /**
     * 学生对课程（老师）的评价
     */
    private String evaluation;
    /**
     * 评价审核状态
     */
    private boolean evaluationStatus;
    /**
     * 课程类别
     */
    private String courseType;

}
