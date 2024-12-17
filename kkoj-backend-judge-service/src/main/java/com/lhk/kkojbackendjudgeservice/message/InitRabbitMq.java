package com.lhk.kkojbackendjudgeservice.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 初始化 RabbitMq，创建交换机、队列、绑定
 */
@Slf4j
public class InitRabbitMq {
    public static void doInit() {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            //交换机
            String judgeExchangeName = "judge_exchange";
            channel.exchangeDeclare(judgeExchangeName, "direct");
            //队列
            String judgeQueueName = "judge_queue";
            channel.queueDeclare(judgeQueueName, true, false, false, null);
            //绑定交换机和队列
            channel.queueBind(judgeQueueName, judgeExchangeName, "judge_routing_key");
            log.info("启动并初始化 RabbitMq 成功");
        } catch (IOException e) {
            log.info("启动并初始化 RabbitMq 失败");
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            log.info("连接 RabbitMq 超时");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        doInit();
    }
}
