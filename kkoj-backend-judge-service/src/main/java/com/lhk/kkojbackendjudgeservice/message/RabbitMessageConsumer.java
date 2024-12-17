package com.lhk.kkojbackendjudgeservice.message;

import com.lhk.kkojbackendjudgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息消费者
 */
@Slf4j
@Component
public class RabbitMessageConsumer {

    @Resource
    private JudgeService judgeService;

    /**
     * 接收消息
     * @param message
     * @param channel
     * @param deliveryTag
     */
    @SneakyThrows
    @RabbitListener(queues = {"judge_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receive message: {}", message);
        // 此处可以根据业务逻辑判断是否进行消息确认，可以对消息进行拒绝，拒绝后会重新放入队列，直到消费成功
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.JudgeQuestion(questionSubmitId);
            channel.basicAck(deliveryTag, false);
        }catch (Exception e){
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
