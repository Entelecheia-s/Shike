package com.example.shike.demos.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.Selection;
import com.example.shike.demos.web.vo.EvaluationInfo;

import java.util.ArrayList;

/**
 * @Description: 学生课程管理接口
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
public interface ISelectionService extends IService<Selection> {
    /**
     * 学生选课
     *
     * @param courseId
     * @param token
     * @return
     */
    public String CourseSelection(String token, String courseId);

    /**
     * 查看所有课程评价
     *
     * @param CourseName
     * @return
     */
    public ArrayList<String> readEvaluationByCourse(String CourseName);

    /**
     * 查看所有教师评价
     *
     * @param TeacherName
     * @return
     */
    public ArrayList<String> readEvaluationByTeacher(String TeacherName);

    /**
     * 查询本专业某学期待选课程
     *
     * @param semester
     * @param pageModel
     * @return
     */
    public IPage<Course> readAllCourses(String semester, PageModel pageModel);

    /**
     * 查询本专业某学期待选课程
     *
     * @param token
     * @param semester
     * @param pageModel
     * @return
     */
    public IPage<Course> readCourseToBeSelect(String token, String semester, PageModel pageModel);

    /**
     * 学生选课结果查询
     *
     * @param token
     * @param pageModel
     * @return
     */
    public IPage<Selection> readStudentCourseSelection(String token, PageModel pageModel);

    /**
     * 学生退课
     *
     * @param token
     * @param selectionId
     * @return
     */
    public String studentCourseWithdraw(String token, String selectionId);

    /**
     * 学生评课
     *
     * @param token
     * @param selectionId
     * @param evaluation
     * @return
     */
    public String studentCourseEvaluation(String token, String selectionId, String evaluation);

    /**
     * 查询评价状态
     *
     * @param token
     * @return
     */
    public ArrayList<EvaluationInfo> viewEvaluationStatus(String token);
}
