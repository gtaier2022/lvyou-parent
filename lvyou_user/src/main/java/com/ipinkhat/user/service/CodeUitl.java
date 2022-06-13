package com.ipinkhat.user.service;

import java.util.Random;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/4/18-12:34
 */
public class CodeUitl {
    public static String getCode(){

        Random random = new Random();
        int c = random.nextInt(999999);
        if (c<100000){
            c=c+100000;
        }
        return c+"";
    }

}
