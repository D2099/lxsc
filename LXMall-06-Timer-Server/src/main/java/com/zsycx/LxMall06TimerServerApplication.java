package com.zsycx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableScheduling // 开启定时器
@EnableEurekaClient
@EnableFeignClients
public class LxMall06TimerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxMall06TimerServerApplication.class, args);
    }

}
