package com.example.shike.demos.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shike.demos.web.dto.LoginDTO;
import com.example.shike.demos.web.dto.RegisterDTO;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.vo.LoginInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 用户基本功能接口
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
public interface IAuthService extends IService<UserEntity> {

    /**
     * 注册
     *
     * @param registerDTO
     * @return
     */
    public String register(RegisterDTO registerDTO);

    /**
     * 导入excel课程信息
     *
     * @return
     */
    public String FileUploadUsers(MultipartFile file);

    /**
     * 登录
     *
     * @param loginDTO
     * @return
     */
    public LoginInfo login(LoginDTO loginDTO);

    /**
     * 查看个人信息
     *
     * @param token
     * @return
     */
    public UserEntity inquiryPersonalInformation(String token);

    /**
     * 改动个人邮箱
     *
     * @param email
     * @param token
     * @return
     */
    public String changePersonalEmail(String email, String token);

    /**
     * 改动个人手机号
     *
     * @param token
     * @param newPhoneNo
     * @return
     */
    public String changePersonalPhoneNo(String token, String newPhoneNo);

    /**
     * 登出
     *
     * @param token
     * @return
     */
    public String logout(String token);


}

