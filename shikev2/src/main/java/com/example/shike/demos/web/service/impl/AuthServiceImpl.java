package com.example.shike.demos.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shike.demos.web.dto.LoginDTO;
import com.example.shike.demos.web.dto.RegisterDTO;
import com.example.shike.demos.web.entity.Course;
import com.example.shike.demos.web.util.RedisUtil;
import com.example.shike.demos.web.vo.LoginInfo;
import com.example.shike.demos.web.entity.UserEntity;
import com.example.shike.demos.web.mapper.UserMapper;
import com.example.shike.demos.web.service.IAuthService;
import com.example.shike.demos.web.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Description: 用户功能
 * @Author: songshuo
 * @Date: 2025/1/30
 * @Version: V1.0
 */
@Service
@Slf4j
public class AuthServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IAuthService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String register(RegisterDTO registerDTO) {
        //防止重复注册
        String phoneNo = registerDTO.getPhoneNo();
        if (userMapper.queryByPhoneNo(phoneNo) != null) {
            log.info("user exist");
            return "用户已存在！";
        }

        //自动生成学号
        String studentNo = genStudentNo();

        //创建一个用户并将其插入数据库
        UserEntity user = new UserEntity();
        user.setName(registerDTO.getName());
        user.setPhoneNo(phoneNo);
        user.setGender(registerDTO.getGender());
        user.setEmail(registerDTO.getEmail());
        user.setCollege(registerDTO.getCollege());
        user.setGrade(registerDTO.getGrade());
        user.setMajor(registerDTO.getMajor());
        user.setStudentNo(studentNo);
        user.setPassword(registerDTO.getPassword());
        user.setRole(registerDTO.getRole());
        user.setSuperAdmin(false);
        return "注册成功，欢迎您！";
    }

    @Override
    public String FileUploadUsers(MultipartFile file) {
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
                Double d0 = row.getCell(0).getNumericCellValue();
                Integer id = d0.intValue();
                user.setId(id);
                double d1 = row.getCell(1).getNumericCellValue();
                String studentNo = String.valueOf(d1);
                user.setStudentNo(studentNo);
                user.setName(row.getCell(2).getStringCellValue());
                user.setGender(row.getCell(3).getStringCellValue());
                double d2 = row.getCell(4).getNumericCellValue();
                String phoneNo = String.valueOf(d2);
                user.setPhoneNo(phoneNo);
                user.setEmail(row.getCell(5).getStringCellValue());
                double d3 = row.getCell(6).getNumericCellValue();
                String password = String.valueOf(d2);
                String md5Password = DigestUtils.md5Hex(password);
                user.setPassword(md5Password);
                user.setCollege(row.getCell(7).getStringCellValue());
                user.setRole(row.getCell(8).getStringCellValue());
                user.setGrade(row.getCell(9).getStringCellValue());
                user.setMajor(row.getCell(10).getStringCellValue());
                user.setSuperAdmin(row.getCell(11).getBooleanCellValue());
                userMapper.insert(user);
                i++;
            }
            return "文件上传并解析成功，共插入 " + i + " 条记录";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }

    @Override
    public LoginInfo login(LoginDTO loginDTO) {

        //加载用户
        String md5Password = DigestUtils.md5Hex(loginDTO.getPassword());
        UserEntity user = userMapper.queryByPhoneNoOrStudentNo(loginDTO.getParam(), md5Password);
        if (user == null) {
            log.info("password wrong!");
            return null;
        }

        //生成登录成功结果
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(user.getId());
        loginInfo.setParam(loginDTO.getParam());
        loginInfo.setName(user.getName());
        loginInfo.setRole(user.getRole());

        //生成token
        String token = JwtUtils.getToken(String.valueOf(user.getId()));
        loginInfo.setToken(token);

        //将用户信息放入缓存
        try {
            boolean successSet = redisUtil.set(String.valueOf(user.getId()), token, 2 * 60 * 60);
            if (successSet) {
                log.info("成功将登录信息放入缓存");
            }
        } catch (Exception e) {
            log.info("登录信息放置缓存失败！");
            e.printStackTrace();
        }

        return loginInfo;
    }

    @Override
    public UserEntity inquiryPersonalInformation(String token) {
        log.info("user inquiry personal information");
        UserEntity student = userMapper.queryByID(JwtUtils.getUserId(token));
        return student;
    }

    @Override
    public String changePersonalEmail(String email, String token) {
        //加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        if (user == null) {
            log.info("not found user");
            return "please register!";
        }

        //更改信息
        user.setEmail(email);
        userMapper.updateById(user);
        return "成功更改邮箱！";
    }

    @Override
    public String changePersonalPhoneNo(String token, String newPhoneNo) {
        //加载用户
        String id = JwtUtils.getUserId(token);
        UserEntity user = userMapper.queryByID(id);
        if (user == null) {
            log.info("not found user");
            return "请注册！";
        }

        //更改信息
        user.setPhoneNo(newPhoneNo);
        userMapper.updateById(user);
        return "成功更改手机号！";
    }

    @Override
    public String logout(String token) {
        //删除缓存中的信息
        String userId = JwtUtils.getUserId(token);
        redisUtil.del(userId);
        return "成功登出！";
    }

    //生成学号方法
    public static String genStudentNo() {
        Random random = new Random();
        int randomNum = random.nextInt(90000000) + 10000000;
        return randomNum + "";
    }

}