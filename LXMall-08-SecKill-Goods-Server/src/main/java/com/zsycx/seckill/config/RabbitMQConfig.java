package com.zsycx.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName: RabbitMQConfig
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue secKillQueue() {
        return new Queue("secKillQueue");
    }

    @Bean
    public FanoutExchange secKillExchange() {
        return new FanoutExchange("secKillExchange");
    }

    @Bean
    public Binding secKillBinding() {
        return new Binding(
                "secKillQueue",
                Binding.DestinationType.QUEUE,
                "secKillExchange",
                "",
                null
        );
    }
}
