package com.ipinkhat.lvyou.category;

import com.ipinkhat.lvyou.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 类描述:该类用于
 *
 * @Author:张文
 * @CreateTime:2022/5/14-16:05
 */
@SpringBootApplication
    public class CategoryApplication {
        public static void main(String[] args) {
            SpringApplication.run(CategoryApplication.class, args);
        }

        @Bean
        public IdWorker idWorkker(){
            return new IdWorker();
        }
}

