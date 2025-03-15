package com.example.shike.demos.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shike.demos.web.dto.CourseDTO;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.mapper.UserMapper;
import com.example.shike.demos.web.service.ICourseService;
import com.example.shike.demos.web.util.Constant;
import com.example.shike.demos.web.util.JwtUtils;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 控制器
 * @Author: songshuo
 * @Date: 2025/2/14
 * @Version: V1.0
 */
@Api(tags = "02-课程服务")
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取新增课程请求
     *
     * @param courseDTO
     * @return
     */
    @PostMapping("/addCourse")
    @ApiOperation(value = "新增课程", notes = "新增课程")
    @ApiOperationSupport(order = 1)
    public String addCourse(@RequestBody CourseDTO courseDTO) {
        String token = request.getHeader(Constant.TOKEN);
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        String teacher = user.getName();
        courseDTO.setTeacher(teacher);
        return iCourseService.addCourse(courseDTO);
    }

    /**
     * 获取查询课程请求
     *
     * @return
     */
    @GetMapping("/readCourse")
    @ApiOperation(value = "查询课程", notes = "查询课程")
    @ApiOperationSupport(order = 4)
    public IPage<Course> readCourse(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        String token = request.getHeader(Constant.TOKEN);
        return iCourseService.readCourse(token, pageModel);
    }

    /**
     * 获取删除课程请求
     *
     * @return
     */
    @GetMapping("/deleteCourse")
    @ApiOperation(value = "删除课程", notes = "删除课程")
    @ApiOperationSupport(order = 2)
    public String deleteCourse(@RequestParam String courseId) {
        String token = request.getHeader(Constant.TOKEN);
        return iCourseService.deleteCourse(token, courseId);
    }

    /**
     * 获取更改课程容量请求
     *
     * @return
     */
    @GetMapping("/updateCourseCapacity")
    @ApiOperation(value = "更改课程容量", notes = "更改课程容量")
    @ApiOperationSupport(order = 3)
    public String updateCourseCapacity(@RequestParam String courseId, @RequestParam int newCourseCapacity) {
        String token = request.getHeader(Constant.TOKEN);
        return iCourseService.updateCourseCapacity(token, courseId, newCourseCapacity);
    }

    /**
     * 获取批量上传课程请求
     *
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "批量上传课程", notes = "批量上传课程")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        return iCourseService.FileUpload(file);
    }
}