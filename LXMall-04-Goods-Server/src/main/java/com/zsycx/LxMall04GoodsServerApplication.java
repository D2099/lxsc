package com.zsycx;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.zsycx.goods.mapper"})
@EnableAutoDataSourceProxy
public class LxMall04GoodsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxMall04GoodsServerApplication.class, args);
    }

}
