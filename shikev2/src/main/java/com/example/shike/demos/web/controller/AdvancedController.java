package com.example.shike.demos.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.dto.SelectionTimeDTO;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.Selection;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.service.IAdvancedService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Description: 控制器
 * @Author: songshuo
 * @Date: 2025/2/17
 * @Version: V1.0
 */
@Api(tags = "04-高级管理")
@RestController
@RequestMapping("/advanced")
public class AdvancedController {
    @Autowired
    private IAdvancedService iAdvancedService;

    /**
     * 获取查看所有用户信息请求
     *
     * @returnb
     */
    @GetMapping("/readAllUsersPage")
    @ApiOperation(value = "查看所有用户信息", notes = "查看所有用户信息")
    @ApiOperationSupport(order = 1)
    public IPage<UserEntity> readAllUsersPage(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        return iAdvancedService.readAllUsersPage(pageModel);
    }

    /**
     * 获取更改用户信息请求
     *
     * @returnb
     */
    @GetMapping("/updateUserInformation")
    @ApiOperation(value = "更改用户信息", notes = "更改用户信息")
    @ApiOperationSupport(order = 2)
    public String updateUserInformation(@RequestParam String userId, @RequestParam String tableName, @RequestParam String newParam) {
        return iAdvancedService.updateUserInformation(userId, tableName, newParam);
    }

    /**
     * 获取查看所有课程信息请求
     *
     * @returnb
     */
    @GetMapping("/readAllCourse")
    @ApiOperation(value = "查看所有课程信息", notes = "查看所有课程信息")
    @ApiOperationSupport(order = 3)
    public IPage<Course> readAllCourse(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        return iAdvancedService.readAllCoursesPage(pageModel);
    }

    /**
     * 获取查看所有选课信息请求
     *
     * @returnb
     */
    @GetMapping("/readAllSelection")
    @ApiOperation(value = "查看所有选课信息", notes = "查看所有选课信息")
    @ApiOperationSupport(order = 4)
    public IPage<Selection> readAllSelection(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        return iAdvancedService.readAllSelectionsPage(pageModel);
    }

    /**
     * 获取删除用户请求
     *
     * @returnb
     */
    @GetMapping("/deleteUser")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiOperationSupport(order = 5)
    public String deleteUser(@RequestParam String id) {
        return iAdvancedService.deleteUser(id);
    }

    /**
     * 获取审核课程评价请求
     *
     * @returnb
     */
    @GetMapping("/examineCourseEvaluation")
    @ApiOperation(value = "审核课程评价", notes = "审核课程评价")
    @ApiOperationSupport(order = 6)
    public String examineCourseEvaluation(@RequestParam String selectionId) throws IOException {
        return iAdvancedService.examineCourseEvaluation(selectionId);
    }

    /**
     * 获取设置选课规则请求
     *
     * @return
     */
    @PostMapping("/setCourseSelectionRule")
    @ApiOperation(value = "设置选课规则", notes = "设置选课规则")
    @ApiOperationSupport(order = 7)
    public String setCourseSelectionRule(@RequestBody SelectionTimeDTO selectionTimeDTO) {
        return iAdvancedService.setCourseSelectionRule(selectionTimeDTO);
    }
}
