package com.ipinkhat.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/4/15-18:03
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    public String getCode(String telephone) {
        String code = CodeUitl.getCode();
        redisTemplate.boundValueOps(telephone).set(code, 5, TimeUnit.MINUTES);
        return code;
    }
}
