package com.example.shike.demos.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.dto.SelectionTimeDTO;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.Selection;
import com.example.shike.demos.web.entity.SelectionRule;
import com.example.shike.demos.web.mapper.CourseMapper;
import com.example.shike.demos.web.mapper.SelectionMapper;
import com.example.shike.demos.web.mapper.SelectionRuleMapper;
import com.example.shike.demos.web.service.IAdvancedService;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.mapper.UserMapper;
import com.example.shike.demos.web.service.ICourseService;
import com.example.shike.demos.web.service.ISelectionService;
import com.example.shike.demos.web.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @Description: 高级管理功能
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
@Service
@Slf4j
public class AdvancedServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IAdvancedService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SelectionMapper selectionMapper;

    @Autowired
    private ISelectionService iSelectionService;

    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private SelectionRuleMapper selectionRuleMapper;

    @Override
    public IPage<UserEntity> readAllUsersPage(PageModel pageModel) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        Page<UserEntity> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<UserEntity> pageList = this.page(page, wrapper);

        return pageList;
    }

    @Override
    public String updateUserInformation(String userId, String tableName, String newParam) {
        //1、加载用户
        UserEntity user = userMapper.queryByID(userId);

        //2、分辨信息种类
        if (tableName.equals("student_no")) {
            user.setStudentNo(newParam);
        }
        if (tableName.equals("name")) {
            user.setName(newParam);
        }
        if (tableName.equals("gender")) {
            user.setGender(newParam);
        }
        if (tableName.equals("phone_no")) {
            user.setPhoneNo(newParam);
        }
        if (tableName.equals("email")) {
            user.setEmail(newParam);
        }
        if (tableName.equals("password")) {
            String newPassword = DigestUtils.md5Hex(newParam);
            user.setPassword(newPassword);
        }
        if (tableName.equals("college")) {
            user.setCollege(newParam);
        }
        if (tableName.equals("role")) {
            user.setRole(newParam);
        }
        if (tableName.equals("grade")) {
            user.setGrade(newParam);
        }
        if (tableName.equals("major")) {
            user.setMajor(newParam);
        }

        //3、更改并插入
        userMapper.updateById(user);
        return "更改成功";
    }

    @Override
    public IPage<Selection> readAllSelectionsPage(PageModel pageModel) {
        QueryWrapper<Selection> wrapper = new QueryWrapper<>();
        Page<Selection> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<Selection> pageList = iSelectionService.page(page, wrapper);
        return pageList;
    }

    @Override
    public IPage<Course> readAllCoursesPage(PageModel pageModel) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        Page<Course> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<Course> pageList = iCourseService.page(page, wrapper);
        return pageList;
    }

    @Override
    public String deleteUser(String id) {
        UserEntity user = userMapper.queryByID(id);
        userMapper.deleteById(user);
        return "成功删除该用户！";
    }

    @Override
    public String examineCourseEvaluation(String selectionId) throws IOException {
        Selection stu = selectionMapper.queryBySelectionId(selectionId);
        stu.setEvaluationStatus(true);
        selectionMapper.updateById(stu);
        return "审核通过！";
    }

    @Override
    public String setCourseSelectionRule(SelectionTimeDTO selectionTimeDTO) {
        //创造课程规则对象并插入数据库
        SelectionRule selectionRule = new SelectionRule();
        selectionRule.setCourseName(selectionTimeDTO.getName());
        selectionRule.setGrade(selectionTimeDTO.getGrade());
        selectionRule.setCollege(selectionTimeDTO.getCollege());
        selectionRule.setMajor(selectionTimeDTO.getMajor());
        selectionRule.setSelectionStartTime(selectionTimeDTO.getStartTime());
        selectionRule.setSelectionDeadline(selectionTimeDTO.getDeadline());
        selectionRule.setCourseType(selectionTimeDTO.getCourseType());
        selectionRule.setNumber(selectionTimeDTO.getNumber());
        selectionRuleMapper.insert(selectionRule);

        log.info("success add a selectionRule");
        return "成功添加选课规则！";
    }
}
