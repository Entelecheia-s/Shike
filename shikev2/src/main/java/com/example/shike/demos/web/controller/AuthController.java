package com.example.shike.demos.web.controller;

import com.example.shike.demos.web.dto.LoginDTO;
import com.example.shike.demos.web.dto.RegisterDTO;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.service.IAuthService;
import com.example.shike.demos.web.util.Constant;
import com.example.shike.demos.web.vo.LoginInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 控制器
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
@Api(tags = "01-用户服务")
@RestController
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    private IAuthService iAuthService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取用户注册请求
     *
     * @param registerDTO
     * @return String
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiOperationSupport(order = 1)
    public String register(@RequestBody RegisterDTO registerDTO) {
        return iAuthService.register(registerDTO);
    }

    /**
     * 获取批量上传用户信息请求
     *
     * @return
     */
    @PostMapping("/uploadUsers")
    @ApiOperation(value = "批量上传用户信息", notes = "批量上传用户信息")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        return iAuthService.FileUploadUsers(file);
    }

    /**
     * 获取用户登录请求
     *
     * @param loginDTO
     * @return String
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiOperationSupport(order = 2)
    public LoginInfo login(@RequestBody LoginDTO loginDTO) {
        return iAuthService.login(loginDTO);
    }

    /**
     * 获取退出系统请求
     *
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation(value = "退出系统", notes = "退出系统")
    @ApiOperationSupport(order = 3)
    public String logout() {
        String token = request.getHeader(Constant.TOKEN);
        return iAuthService.logout(token);
    }

    /**
     * 获取更改个人邮箱请求
     *
     * @param newEmail
     * @return
     */
    @GetMapping("/changePersonalEmail")
    @ApiOperation(value = "更改个人邮箱", notes = "更改个人邮箱")
    public String changePersonalEmail(@RequestParam String newEmail) {
        String token = request.getHeader(Constant.TOKEN);
        return iAuthService.changePersonalEmail(newEmail, token);
    }

    /**
     * 获取更改手机号请求
     *
     * @param newPhoneNo
     * @return
     */
    @GetMapping("/changePersonalPhoneNo")
    @ApiOperation(value = "更改手机号", notes = "更改手机号")
    public String changePersonalPhoneNo(@RequestParam String newPhoneNo) {
        String token = request.getHeader(Constant.TOKEN);
        return iAuthService.changePersonalPhoneNo(token, newPhoneNo);
    }

    /**
     * 获取查询个人信息请求
     *
     * @return String
     */
    @GetMapping("/inquiryPersonalInformation")
    @ApiOperation(value = "查询个人信息", notes = "查询个人信息")
    public UserEntity inquiryPersonalInformation() {
        String token = request.getHeader(Constant.TOKEN);
        return iAuthService.inquiryPersonalInformation(token);
    }
}