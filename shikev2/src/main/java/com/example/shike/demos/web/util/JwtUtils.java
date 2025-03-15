package com.example.shike.demos.web.util;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.shike.demos.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import com.auth0.jwt.JWT;

@Component
public class JwtUtils {
    private static final String SELECT_KEY = "c2hpa2U=";//秘钥，shike

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public static String getToken(String userId) {
        return JWT.create()
                //签收者
                .withAudience(userId)
                //主题
                .withSubject("token")
                //2小时后token过期
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                //以shike作为token的密钥
                .sign(Algorithm.HMAC256(SELECT_KEY));
    }

    @Autowired
    private static UserMapper userMapper;

    /**
     * 解析token获得Id
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {

        String userId = "";
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            j.printStackTrace();
        }
        return userId;
    }

}

