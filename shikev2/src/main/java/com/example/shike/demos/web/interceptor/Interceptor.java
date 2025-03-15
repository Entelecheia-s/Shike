package com.example.shike.demos.web.interceptor;

import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.mapper.UserMapper;
import com.example.shike.demos.web.util.Constant;
import com.example.shike.demos.web.util.JwtUtils;
import com.example.shike.demos.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description: 拦截器类
 * @Author: songshuo
 * @Date: 2025/2/3
 * @Version: V1.0
 */
@Component
@Slf4j
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //1、获取请求头中的token
        log.info("preHandle...");
        String token = request.getHeader(Constant.TOKEN);
        String url = request.getRequestURI();

        Set adminRights = new HashSet<>();
        adminRights.add("/shike/course/addCourse");
        adminRights.add("/shike/course/readCourse");
        adminRights.add("/shike/course/deleteCourse");
        adminRights.add("/shike/course/updateCourseCapacity");
        adminRights.add("/shike/course/upload");

        Set sAdminRights = new HashSet<>();
        sAdminRights.add("/shike/advanced/readAllUsersPage");
        sAdminRights.add("/shike/advanced/readAllCourse");
        sAdminRights.add("/shike/advanced/readAllSelection");
        sAdminRights.add("/shike/advanced/examineCourseEvaluation");
        sAdminRights.add("/shike/advanced/setCourseSelectionTime");
        sAdminRights.add("/shike/advanced/updateUserInformation");

        log.info("url={}", url);
        log.info("token={}", token);

        //2（用户未登录）、如果请求中没有token或者缓存中没有token，拦截并返回401
        try {
            String userId = JwtUtils.getUserId(token);
            String redisValue = (String) redisUtil.get(userId);

            log.info("redisValue={}", redisValue);
            log.info("userId={}", userId);

            if (redisValue == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        // 设置响应状态码
                response.setContentType("application/json");                    // 设置响应内容类型
                response.getWriter().write("Illegal user, please login!");   // 写入响应体
                return false;
            }
           
            //3、对于特定的功能，学生角色被拦截
            UserEntity user = userMapper.queryByID(userId);
            if (user.getRole().equals("student")) {
                if (adminRights.contains(url) || sAdminRights.contains(url)) {

                    log.info("学生无权限！");

                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        // 设置响应状态码
                    response.setContentType("application/json");                    // 设置响应内容类型
                    response.getWriter().write("学生角色无此权限!");               // 写入响应体
                    return false;
                }
            }
            if (user.getRole().equals("admin")) {
                if (sAdminRights.contains(url)) {

                    log.info("普通管理员无权限！");

                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        // 设置响应状态码
                    response.setContentType("application/json");                    // 设置响应内容类型
                    response.getWriter().write("普通管理员无权限!");               // 写入响应体
                    return false;
                }
            }
        } catch (Exception e) {

            log.info("缓存查询失败！");

            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);        // 设置响应状态码
            response.setContentType("application/json");                    // 设置响应内容类型
            response.getWriter().write("Illegal user, please login!");   // 写入响应体
            return false;
        }

        //4、放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("postHandle: " + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("afterCompletion: " + request.getRequestURI());
    }
}
