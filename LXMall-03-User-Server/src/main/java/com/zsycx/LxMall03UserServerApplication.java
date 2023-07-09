package com.zsycx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.zsycx.user.mapper"})
public class LxMall03UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxMall03UserServerApplication.class, args);
    }

}
