package com.zsycx.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.zsycx.seckill.mapper"})
public class LxMall09SecKillOrdersServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxMall09SecKillOrdersServerApplication.class, args);
    }

}
