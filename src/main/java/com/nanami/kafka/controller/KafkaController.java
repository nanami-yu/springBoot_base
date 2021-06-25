package com.nanami.kafka.controller;


import com.nanami.kafka.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fengyu
 * @version 1.0
 * @Title: kafka
 * @date 2020/11/23 10:23
 */
@RestController
public class KafkaController {

    @Autowired
    private Producer producer;

    @GetMapping("/message/send")
    public boolean send(){
        producer.send();
        return true;
    }


}
