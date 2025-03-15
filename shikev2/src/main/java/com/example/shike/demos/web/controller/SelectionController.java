package com.example.shike.demos.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.Selection;
import com.example.shike.demos.web.service.ISelectionService;
import com.example.shike.demos.web.util.Constant;
import com.example.shike.demos.web.vo.EvaluationInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @Description: 控制器
 * @Author: songshuo
 * @Date: 2025/2/16
 * @Version: V1.0
 */
@Api(tags = "03-学生课程管理")
@RestController
@RequestMapping("")
public class SelectionController {
    @Autowired
    private ISelectionService iSelectionService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取选课请求
     *
     * @param courseId
     * @return
     */
    @GetMapping("/studentCourseSelection")
    @ApiOperation(value = "选课", notes = "选课")
    @ApiOperationSupport(order = 1)
    public String studentCourseSelection(@RequestParam String courseId) {
        String token = request.getHeader(Constant.TOKEN);
        return iSelectionService.CourseSelection(token, courseId);
    }

    /**
     * 获取按课程查询评价请求
     *
     * @param courseName
     * @return
     */
    @GetMapping("/readEvaluationByCourse")
    @ApiOperation(value = "按课程查询评价", notes = "按课程查询评价")
    @ApiOperationSupport(order = 2)
    public ArrayList<String> readEvaluationByCourse(@RequestParam String courseName) {
        return iSelectionService.readEvaluationByCourse(courseName);
    }

    /**
     * 获取按教师查询评价请求
     *
     * @param TeacherName
     * @return
     */
    @GetMapping("/readEvaluationByTeacher")
    @ApiOperation(value = "按教师查询评价", notes = "按教师查询评价")
    @ApiOperationSupport(order = 3)
    public ArrayList<String> readEvaluationByTeacher(@RequestParam String TeacherName) {
        return iSelectionService.readEvaluationByTeacher(TeacherName);
    }

    /**
     * 获取查询各学期课程请求
     *
     * @param semester
     * @return
     */
    @GetMapping("/readAllCourses")
    @ApiOperation(value = "查询各学期课程", notes = "查询各学期课程")
    @ApiOperationSupport(order = 4)
    public IPage<Course> readAllCourse(@RequestParam String semester, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        return iSelectionService.readAllCourses(semester, pageModel);
    }

    /**
     * 获取查询待选课程请求
     *
     * @param semester
     * @return
     */
    @GetMapping("/readCourseToBeSelect")
    @ApiOperation(value = "查询待选课程", notes = "查询待选课程")
    @ApiOperationSupport(order = 5)
    public IPage<Course> readCourseToBeSelect(@RequestParam String semester, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String token = request.getHeader(Constant.TOKEN);
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        return iSelectionService.readCourseToBeSelect(token, semester, pageModel);
    }

    /**
     * 获取查询已选课程请求
     *
     * @return
     */
    @GetMapping("/readStudentCourseSelection")
    @ApiOperation(value = "查询已选课程", notes = "查询已选课程")
    @ApiOperationSupport(order = 6)
    public IPage<Selection> readStudentCourseSelection(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String token = request.getHeader(Constant.TOKEN);
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        return iSelectionService.readStudentCourseSelection(token, pageModel);
    }

    /**
     * 获取退课请求
     *
     * @param selectionId
     * @return
     */
    @GetMapping("/studentCourseWithdraw")
    @ApiOperation(value = "退课", notes = "退课")
    @ApiOperationSupport(order = 7)
    public String studentCourseWithdraw(@RequestParam String selectionId) {
        String token = request.getHeader(Constant.TOKEN);
        return iSelectionService.studentCourseWithdraw(token, selectionId);
    }

    /**
     * 获取评课请求
     *
     * @param selectionId
     * @param evaluation
     * @returnb String
     */
    @GetMapping("/studentCourseEvaluation")
    @ApiOperation(value = "评课", notes = "评课")
    @ApiOperationSupport(order = 8)
    public String studentCourseEvaluation(@RequestParam String selectionId, @RequestParam String evaluation) {
        String token = request.getHeader(Constant.TOKEN);
        return iSelectionService.studentCourseEvaluation(token, selectionId, evaluation);
    }

    /**
     * 获取查询评价状态请求
     *
     * @returnb String
     */
    @GetMapping("/viewEvaluationStatus")
    @ApiOperation(value = "查询评价状态", notes = "查询评价状态")
    @ApiOperationSupport(order = 9)
    public ArrayList<EvaluationInfo> viewEvaluationStatus() {
        String token = request.getHeader(Constant.TOKEN);
        return iSelectionService.viewEvaluationStatus(token);
    }
}