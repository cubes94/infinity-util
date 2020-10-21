package com.infinity.training.mybatis.spring.boot.starter.test;

import com.infinity.training.mybatis.spring.annotation.InfinityMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author whc
 * @version 1.0.0
 * @since 2020/10/21 21:03
 */
@SpringBootApplication(scanBasePackages = "com.infinity")
@InfinityMapperScan("com.infinity.**.dao")
public class InfinityMybatisSpringBootTestApp {

    public static void main(String[] args) {
        SpringApplication.run(InfinityMybatisSpringBootTestApp.class, args);
    }
}
