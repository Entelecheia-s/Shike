package com.example.shike.demos.web.config;

import com.example.shike.demos.web.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 拦截器配置
 * @Author: songshuo
 * @Date: 2025/2/3
 * @Version: V1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private Interceptor interceptor;

    //注册拦截器并设置拦截路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/register")
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/auth/logout")
                .excludePathPatterns("/auth/inquiryPersonalInformation")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/*.ico");
    }
}

