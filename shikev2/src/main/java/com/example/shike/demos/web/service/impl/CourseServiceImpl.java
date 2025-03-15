package com.example.shike.demos.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shike.demos.web.dto.CourseDTO;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.mapper.CourseMapper;
import com.example.shike.demos.web.mapper.UserMapper;
import com.example.shike.demos.web.service.ICourseService;
import com.example.shike.demos.web.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @Description: 课程管理功能
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public String addCourse(CourseDTO courseDTO) {
        //创造课程对象并插入数据库
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setGrade(courseDTO.getGrade());
        course.setCollege(courseDTO.getCollege());
        course.setMajor(courseDTO.getMajor());
        course.setCapacity(courseDTO.getCapacity());
        course.setCourseNature(courseDTO.getCourseNature());
        course.setCourseType(courseDTO.getCourseType());
        course.setSemester(courseDTO.getSemester());
        course.setTeacher(courseDTO.getTeacher());
        course.setDeleteStatus(false);
        courseMapper.insert(course);

        log.info("success add a course");
        return "成功添加课程！";
    }

    @Override
    public IPage<Course> readCourse(String token, PageModel pageModel) {
        log.info("user inquiry course");

        //1、加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);

        //2、设置查询条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher", user.getName());
        wrapper.eq("delete_status", false);

        //3、分页查询
        Page<Course> page = new Page<>(pageModel.getPageNo(), pageModel.getPageSize());
        IPage<Course> pageList = this.page(page, wrapper);
        return pageList;
    }

    @Override
    public String deleteCourse(String token, String courseId) {
        //1、加载课程
        Course course = courseMapper.queryByCourseId(courseId);

        //2、校验管理员身份
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        if (user.getName().equals(course.getTeacher()) || user.isSuperAdmin()) {
            course.setDeleteStatus(true);
            courseMapper.updateById(course);
            return "成功删除课程！";
        }

        //3、无权限拦截
        return "您没有删除权限！";
    }

    @Override
    public String updateCourseCapacity(String token, String courseId, int newCourseCapacity) {
        //1、加载课程
        Course course = courseMapper.queryByCourseId(courseId);

        //2、校验管理员身份
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);

        if (user.getName().equals(course.getTeacher()) || user.isSuperAdmin()) {
            course.setCapacity(newCourseCapacity);
            courseMapper.updateById(course);
            return "成功变动课程容量！";
        }

        //3、无权限拦截
        return "您没有改动权限！";
    }

    @Override
    public String FileUpload(MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            return "文件为空";
        }

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // 跳过表头
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            //计数
            int i = 0;

            //读取文件
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                UserEntity user = new UserEntity();
                Course course = new Course();
                Double d0 = row.getCell(0).getNumericCellValue();
                Integer integer0 = d0.intValue();
                course.setId(integer0);
                Double d1 = row.getCell(6).getNumericCellValue();
                Integer integer1 = d1.intValue();
                course.setName(row.getCell(1).getStringCellValue());
                course.setTeacher(row.getCell(2).getStringCellValue());
                course.setGrade(row.getCell(3).getStringCellValue());
                course.setCollege(row.getCell(4).getStringCellValue());
                course.setMajor(row.getCell(5).getStringCellValue());
                course.setCapacity(integer1);
                course.setCourseNature(row.getCell(7).getStringCellValue());
                course.setCourseType(row.getCell(8).getStringCellValue());
                course.setSemester(row.getCell(9).getStringCellValue());
                courseMapper.insert(course);
                i++;
            }
            return "文件上传并解析成功，共插入 " + i + " 条记录";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }
}


