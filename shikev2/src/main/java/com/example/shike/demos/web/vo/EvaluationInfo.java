package com.example.shike.demos.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 课程评价封装类
 * @Author: songshuo
 * @Date: 2025/2/19
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationInfo {
    private String courseName;
    private String evaluation;
    private String evaluationStatus;
}
