package com.yaothao.order.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MqClientController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/amqp/message")
    public void sendMessage() {
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }
}
