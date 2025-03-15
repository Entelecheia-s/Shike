package com.example.shike.demos.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shike.demos.web.dto.CourseDTO;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.entity.Course;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 课程功能接口
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
public interface ICourseService extends IService<Course> {
    /**
     * 添加课程
     *
     * @param courseDTO
     * @return
     */
    public String addCourse(CourseDTO courseDTO);

    /**
     * 查看课程
     *
     * @param token
     * @param pageModel
     * @return
     */
    public IPage<Course> readCourse(String token, PageModel pageModel);

    /**
     * 删除课程
     *
     * @param token
     * @param courseId
     * @return
     */
    public String deleteCourse(String token, String courseId);

    /**
     * 改动课程容量
     *
     * @param token
     * @param courseId
     * @param newCourseCapacity
     * @return
     */
    public String updateCourseCapacity(String token, String courseId, int newCourseCapacity);

    /**
     * 导入excel课程信息
     *
     * @return
     */
    public String FileUpload(MultipartFile file);
}
