package com.example.shike.demos.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 选课时间DTO
 * @Author: songshuo
 * @Date: 2025/2/23
 * @Version: V1.0
 */
@Data
public class SelectionTimeDTO {
    /**
     * 课程类别
     */
    private String courseType;
    /**
     * 课程名称
     */
    private String name;
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
     * 选课开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 选课结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadline;
    /**
     * 限选数量
     */
    private Integer number;
}
