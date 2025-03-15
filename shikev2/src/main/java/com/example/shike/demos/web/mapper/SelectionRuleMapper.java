package com.example.shike.demos.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shike.demos.web.entity.SelectionRule;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description: 连接选课规则数据库
 * @Author: songshuo
 * @Date: 2025/2/25
 * @Version: V1.0
 */
@Repository
public interface SelectionRuleMapper extends BaseMapper<SelectionRule> {
    @Select("select * from selection_rules where (course_type=#{courseType} or course_type=#{constant}) and (course_name=#{courseName} or course_name=#{constant}) and" +
            "(college=#{college} or college=#{constant}) and (grade=#{grade} or grade=#{constant}) and (major=#{major} or major=#{constant})")
    public SelectionRule queryRules(String courseType, String courseName, String college, String grade, String major, String constant);
}
