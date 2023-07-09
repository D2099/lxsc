package com.zsycx.goods.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName: DataSourceConfig
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
// @Configuration
public class DataSourceConfig {

    // @Bean
    // @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
