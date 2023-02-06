package com.yaothao.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*
 * 接受mq消息
 */
@Slf4j
@Component
public class MqReceiver {

    @RabbitListener(queues = "myQueue")
    public void process(String message) {
        log.info("MqReceiver = {}",message);
    }
}
