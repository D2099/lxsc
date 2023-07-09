package com.zsycx.orders.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @ClassName: RabbitMQConfig
 * @Description: TODO
 * @E-mail: 209733813@qq.com
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange ordersPayExchange() {
        return new FanoutExchange("ordersPayExchange");
    }

    @Bean
    public Queue pointQueue() {
        return new Queue("pointQueue");
    }

    @Bean
    public Queue logisticsProcessing() {
        return new Queue("logisticsProcessingQueue");
    }

    @Bean
    public Binding pointQueueBinding() {
        return new Binding(
                "pointQueue",
                Binding.DestinationType.QUEUE,
                "ordersPayExchange",
                "",
                null
        );
    }

    @Bean
    public Binding logisticsProcessingQueueBinding() {
        return new Binding(
                "logisticsProcessingQueue",
                Binding.DestinationType.QUEUE,
                "ordersPayExchange",
                "",
                null
        );
    }

    // --------------------- 订单超时死信处理队列和交换机绑定区域 ---------------------
    @Bean
    public Queue ordersHandlerQueue() {
        HashMap<String, Object> deadLetterMap = new HashMap<>();
        deadLetterMap.put("x-dead-letter-exchange", "ordersDeadLetterExchange");
        deadLetterMap.put("x-dead-letter-routing-key", "orderDeadLetterKey");

        return new Queue("ordersHandlerQueue", true, false, false, deadLetterMap);
    }

    @Bean
    public FanoutExchange ordersHandlerExchange() {
        return new FanoutExchange("ordersHandlerExchange");
    }

    @Bean
    public Binding ordersHandlerBinding() {
        return new Binding(
                "ordersHandlerQueue",
                Binding.DestinationType.QUEUE,
                "ordersHandlerExchange",
                "",
                null
        );
    }

    @Bean
    public Queue ordersDeadLetterQueue() {
        return new Queue("ordersDeadLetterQueue");
    }

    @Bean
    public DirectExchange ordersDeadLetterExchange() {
        return new DirectExchange("ordersDeadLetterExchange");
    }

    @Bean
    public Binding ordersDeadLetterBinding() {
        return new Binding(
                "ordersDeadLetterQueue",
                Binding.DestinationType.QUEUE,
                "ordersDeadLetterExchange",
                "orderDeadLetterKey",
                null
        );
    }
    // --------------------- 订单超时死信处理队列和交换机绑定区域 ---------------------
}
