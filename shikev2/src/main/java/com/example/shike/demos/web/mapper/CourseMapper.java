package com.example.shike.demos.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shike.demos.web.entity.Course;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @Description: 连接课程数据库
 * @Author: songshuo
 * @Date: 2025/2/14
 * @Version: V1.0
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * @Description: 用授课教师姓名查询课程
     * @Author: songshuo
     * @Date: 2025/2/14
     * @Version: V1.0
     */
    @Select("select * from courses where teacher like #{teacher}")
    ArrayList<Course> queryByTeacherName(String teacher);

    /**
     * @Description: 用id查询课程
     * @Author: songshuo
     * @Date: 2025/2/16
     * @Version: V1.0
     */
    @Select("select * from courses where id = #{id}")
    Course queryByCourseId(String id);

    /**
     * @Description: 用课程名称查询课程
     * @Author: songshuo
     * @Date: 2025/2/18
     * @Version: V1.0
     */
    @Select("select * from courses where name = #{courseName}")
    Course queryByCourseName(String courseName);
}


