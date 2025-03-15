package com.example.shike.demos.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shike.demos.web.dto.PageModel;
import com.example.shike.demos.web.dto.SelectionTimeDTO;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.entity.Selection;
import com.example.shike.demos.web.entity.UserEntity;

import java.io.IOException;

/**
 * @Description: 高级功能接口
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
public interface IAdvancedService extends IService<UserEntity> {


    /**
     * 查看所有选课信息
     *
     * @param pageModel
     * @return
     */
    public IPage<Selection> readAllSelectionsPage(PageModel pageModel);

    /**
     * 查看所有课程信息
     *
     * @param pageModel
     * @return
     */
    public IPage<Course> readAllCoursesPage(PageModel pageModel);


    /**
     * 查看所有用户信息
     *
     * @param pageModel
     * @return
     */
    public IPage<UserEntity> readAllUsersPage(PageModel pageModel);

    /**
     * 更改任意用户信息
     *
     * @param userId
     * @param tableName
     * @param newParam
     * @return
     */
    public String updateUserInformation(String userId, String tableName, String newParam);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    public String deleteUser(String id);

    /**
     * 课程评价审核
     *
     * @param selectionId
     * @return
     * @throws IOException
     */
    public String examineCourseEvaluation(String selectionId) throws IOException;

    /**
     * 设置选课时间
     *
     * @param selectionTimeDTO
     * @return
     */
    public String setCourseSelectionRule(SelectionTimeDTO selectionTimeDTO);
}
