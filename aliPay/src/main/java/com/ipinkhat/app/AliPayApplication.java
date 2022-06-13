package com.ipinkhat.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/17-20:54
 */
@SpringBootApplication
@EnableFeignClients
public class AliPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AliPayApplication.class,args);
    }
}
