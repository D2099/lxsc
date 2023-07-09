package com.zsycx;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// @EnableEurekaClient
@MapperScan(basePackages = {"com.zsycx.orders.mapper"})
@EnableFeignClients // 开启 feign 客户端
@EnableTransactionManagement // 开启本地事务
@EnableAutoDataSourceProxy
public class LxMall05OrdersServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxMall05OrdersServerApplication.class, args);
    }

}
