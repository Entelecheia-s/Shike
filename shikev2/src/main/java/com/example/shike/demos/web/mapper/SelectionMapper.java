package com.example.shike.demos.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shike.demos.web.entity.Selection;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @Description: 连接学生选课数据库
 * @Author: songshuo
 * @Date: 2025/2/16
 * @Version: V1.0
 */
@Repository
public interface SelectionMapper extends BaseMapper<Selection> {
    /**
     * @Description: 用学生姓名查询所有选课信息
     * @Author: songshuo
     * @Date: 2025/2/18
     * @Version: V1.0
     */
    @Select("select * from selections where student_name = #{StudentName} and with_draw = false")
    ArrayList<Selection> queryAllByStudentName(String StudentName);

    /**
     * @Description: 用课程名称查询所有选课信息
     * @Author: songshuo
     * @Date: 2025/2/19
     * @Version: V1.0
     */
    @Select("select * from selections where course_name like #{courseName} and with_draw = false")
    ArrayList<Selection> queryAllByCourseName(String courseName);

    /**
     * @Description: 用课程类别查询选课信息
     * @Author: songshuo
     * @Date: 2025/2/25
     * @Version: V1.0
     */
    @Select("select * from selections where course_type = #{courseType} and with_draw = false")
    ArrayList<Selection> queryAllByCourseType(String courseType);

    /**
     * @Description: 用id查询选课信息
     * @Author: songshuo
     * @Date: 2025/2/16
     * @Version: V1.0
     */
    @Select("select * from selections where id = #{id} and with_draw = false")
    Selection queryBySelectionId(String id);


}