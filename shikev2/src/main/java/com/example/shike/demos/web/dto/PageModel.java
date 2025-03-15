package com.example.shike.demos.web.dto;

import lombok.Data;

/**
 * @Description: 分页Model
 * @Author: songshuo
 * @Date: 2025/2/23
 * @Version: V1.0
 */
@Data
public class PageModel {
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 页容量
     */
    private Integer pageSize;
}
