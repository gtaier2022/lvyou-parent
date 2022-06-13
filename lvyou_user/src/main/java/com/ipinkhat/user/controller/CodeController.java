package com.ipinkhat.user.controller;



import com.ipinkhat.lvyou.domain.User;
import com.ipinkhat.user.service.RedisService;
import com.ipinkhat.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/4/18-23:55
 */
@CrossOrigin
@RestController
@RequestMapping("code")
public class CodeController {
    @Autowired
    private RedisService redisService;
    @Autowired
    UserService userService;
    @GetMapping("getCode")
    public String  getCode(String telephone) {
        if ((telephone != null) && (!telephone.isEmpty())) {
            boolean matches = Pattern.matches("^1[3-9]\\d{9}$", telephone);
            if (matches) {
                User userTelephone = userService.findByTelephone(telephone);
                if (userTelephone!=null){
                    return "error reTelephone";
                }
                return redisService.getCode(telephone);
            }
        } else {

            return "";
        }
        return  "";
    }
    }



