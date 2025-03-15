package com.example.shike.demos.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shike.demos.web.entity.UserEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description: 连接数据库
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
@Repository
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * @Description: 用手机号查询
     * @Author: songshuo
     * @Date: 2025/1/30
     * @Version: V1.0
     */
    @Select("select * from users where phone_no = #{phoneNo}")
    UserEntity queryByPhoneNo(String phoneNo);

    /**
     * @Description: 用手机号或学号和密码查询
     * @Author: songshuo
     * @Date: 2025/2/24
     * @Version: V2.0
     */
    @Select("select * from users where phone_no = #{param} or student_no = #{param} and password = #{md5Password}")
    UserEntity queryByPhoneNoOrStudentNo(String param, String md5Password);

    /**
     * @Description: 用id查询
     * @Author: songshuo
     * @Date: 2025/2/14
     * @Version: V1.0
     */
    @Select("select * from users where id = #{id} ")
    UserEntity queryByID(String id);

}
