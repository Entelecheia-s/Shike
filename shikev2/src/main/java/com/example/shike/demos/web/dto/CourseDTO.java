package com.example.shike.demos.web.dto;

import lombok.Data;

/**
 * @Description: 课程DTO
 * @Author: songshuo
 * @Date: 2025/2/23
 * @Version: V1.0
 */
@Data
public class CourseDTO {

    private String name;
    private String grade;
    private String college;
    private String major;
    private int capacity;
    private String courseNature;
    private String courseType;
    private String semester;
    private String teacher;
}
