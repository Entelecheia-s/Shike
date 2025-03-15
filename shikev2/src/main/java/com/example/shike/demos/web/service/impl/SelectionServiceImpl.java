package com.example.shike.demos.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.Selection;
import com.example.shike.demos.web.entity.SelectionRule;
import com.example.shike.demos.web.mapper.CourseMapper;
import com.example.shike.demos.web.mapper.SelectionMapper;
import com.example.shike.demos.web.mapper.SelectionRuleMapper;
import com.example.shike.demos.web.service.ICourseService;
import com.example.shike.demos.web.service.ISelectionService;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.mapper.UserMapper;
import com.example.shike.demos.web.util.JwtUtils;
import com.example.shike.demos.web.vo.EvaluationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @Description: 学生选课功能
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
@Service
@Slf4j
public class SelectionServiceImpl extends ServiceImpl<SelectionMapper, Selection> implements ISelectionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private SelectionMapper selectionMapper;

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    @Lazy
    private ISelectionService iSelectionService;

    @Autowired
    private SelectionRuleMapper selectionRuleMapper;

    @Override
    public String CourseSelection(String token, String courseId) {
        //1、获取选课时间
        LocalDateTime now = LocalDateTime.now();

        //2、获取课程信息
        Course course = courseMapper.queryByCourseId(courseId);

        //3、获取学生信息
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        String studentName = user.getName();

        //4、获取选课规则
        String constant = "All";
        SelectionRule selectionRule = selectionRuleMapper.queryRules(course.getCourseType(), course.getName(), user.getCollege(), user.getGrade(), user.getMajor(), constant);

        //5、检查是否超出限选课程数
        ArrayList<Selection> selections = selectionMapper.queryAllByCourseType(course.getCourseType());
        int i = 0;
        while (i < selections.size()) {
            i++;
        }
        if (i > selectionRule.getNumber()) {
            return "超出" + course.getCourseType() + "限选课程数，无法选课！";
        }

        //6、检查课程容量是否足够
        if (course.getCapacity() == 0) {
            return "课程容量不足，无法选课！";
        }

        //7、检查是否在选课时间内
        LocalDateTime startTime = selectionRule.getSelectionStartTime();
        LocalDateTime deadline = selectionRule.getSelectionDeadline();
        if (startTime == null || deadline == null) {
            return "管理员还未设置选课时间，请等待具体通知！";
        }
        if (now.isBefore(startTime) || now.isAfter(deadline)) {
            return "不在该课程选课时间内，无法选课！";
        }

        //8、插入选课信息
        Selection selection = new Selection();
        selection.setStudentName(studentName);
        selection.setCourseName(course.getName());
        selection.setCourseType(course.getCourseType());
        selection.setCreateTime(now);
        selection.setWithDraw(false);
        selectionMapper.insert(selection);

        //9、更新课程容量
        course.setCapacity(course.getCapacity() - 1);
        courseMapper.updateById(course);
        return "选课成功！";
    }

    @Override
    public IPage<Course> readAllCourses(String semester, PageModel pageModel) {
        //1、设置查询条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("semester", semester);

        //2、分页查询
        Page<Course> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<Course> pageList = iCourseService.page(page, wrapper);
        return pageList;
    }

    @Override
    public IPage<Course> readCourseToBeSelect(String token, String semester, PageModel pageModel) {
        //1、加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        String major = user.getMajor();

        //2、设置查询条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("major", major);
        wrapper.eq("semester", semester);

        //3、分页查询
        Page<Course> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<Course> pageList = iCourseService.page(page, wrapper);
        return pageList;
    }

    @Override
    public IPage<Selection> readStudentCourseSelection(String token, PageModel pageModel) {
        //1、加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        String studentName = user.getName();

        //2、设置查询条件
        QueryWrapper<Selection> wrapper = new QueryWrapper<>();
        wrapper.eq("student_name", studentName);
        wrapper.eq("with_draw", "false");

        //3、分页查询
        Page<Selection> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<Selection> pageList = iSelectionService.page(page, wrapper);
        return pageList;
    }

    @Override
    public String studentCourseWithdraw(String token, String selectionId) {
        //1、加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        String studentName = user.getName();

        //2、检查管理员权限
        Selection selection = selectionMapper.queryBySelectionId(selectionId);
        if (studentName.equals(selection.getStudentName()) || user.isSuperAdmin()) {
            //获取课程信息
            Course course = courseMapper.queryByCourseName(selection.getCourseName());

            //获取选课规则
            String constant = "All";
            SelectionRule selectionRule = selectionRuleMapper.queryRules(course.getCourseType(), course.getName(), user.getCollege(), user.getGrade(), user.getMajor(), constant);

            //检查是否在退课时间内
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = selectionRule.getSelectionStartTime();
            LocalDateTime deadline = selectionRule.getSelectionDeadline();
            if (startTime == null || deadline == null) {
                return "管理员还未设置退课时间，请等待具体通知！";
            }
            if (now.isBefore(startTime) || now.isAfter(deadline)) {
                return "不在该课程退课时间内，无法退课";
            }

            //改动退选状态
            selection.setEvaluationStatus(true);

            //更新课程容量
            course.setCapacity(course.getCapacity() + 1);
            courseMapper.updateById(course);
            return "退课成功！";
        }
        return "您没有删除权限！";
    }

    @Override
    public String studentCourseEvaluation(String token, String selectionId, String evaluation) {
        //1、加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        String studentName = user.getName();

        //2、比对用户名
        Selection selection = selectionMapper.queryBySelectionId(selectionId);
        if (!studentName.equals(selection.getStudentName())) {
            return "您没有评价权限！";
        }

        //3、找到相应选课信息
        Selection scs = selectionMapper.queryBySelectionId(selectionId);

        //4、写评价并插入选课表
        scs.setEvaluation(evaluation);
        selectionMapper.updateById(scs);

        //默认审核状态为false
        scs.setEvaluationStatus(false);
        return "评价成功，待审核";
    }

    @Override
    public ArrayList<EvaluationInfo> viewEvaluationStatus(String token) {
        //加载选课信息
        String id = JwtUtils.getUserId(token);
        String userName = userMapper.queryByID(id).getName();
        ArrayList<Selection> selections = selectionMapper.queryAllByStudentName(userName);

        //输出评价信息
        ArrayList<EvaluationInfo> evaluationsAndStatus = new ArrayList<>();
        for (int i = 0; i < selections.size(); i++) {
            EvaluationInfo evaluationInfo = new EvaluationInfo();
            evaluationInfo.setCourseName(selections.get(i).getCourseName());
            evaluationInfo.setEvaluation(selections.get(i).getEvaluation());
            evaluationInfo.setEvaluationStatus(String.valueOf(selections.get(i).isEvaluationStatus()));
            evaluationsAndStatus.add(evaluationInfo);
        }
        return evaluationsAndStatus;
    }

    @Override
    public ArrayList<String> readEvaluationByCourse(String CourseName) {
        //1、加载相关选课信息
        ArrayList<Selection> selections = selectionMapper.queryAllByCourseName("%" + CourseName + "%");

        //2、调出评价集合
        ArrayList<String> evaluations = new ArrayList<>();
        for (int i = 0; i < selections.size(); i++) {
            String evaluation = selections.get(i).getEvaluation();
            if (evaluation != null) {
                if (selections.get(i).isEvaluationStatus()) {
                    evaluations.add(evaluation);
                }
            }
        }

        //3、整合为集合输出
        return evaluations;
    }

    @Override
    public ArrayList<String> readEvaluationByTeacher(String TeacherName) {
        //1、加载所有相关选课信息
        ArrayList<Course> courses = courseMapper.queryByTeacherName("%" + TeacherName + "%");

        //2、调出评价集合
        ArrayList<String> evaluations = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            ArrayList<Selection> selections = selectionMapper.queryAllByCourseName(courses.get(i).getName());
            for (int j = 0; j < selections.size(); j++) {
                String evaluation = selections.get(j).getEvaluation();
                if (evaluation != null) {
                    if (selections.get(j).isEvaluationStatus()) {
                        evaluations.add(evaluation);
                    }
                }
            }
        }

        //3、整合为集合输出
        return evaluations;
    }
}
