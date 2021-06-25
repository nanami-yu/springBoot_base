package com.nanami.kafka.producer;

import com.nanami.kafka.dto.Message;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Date;
import java.util.UUID;

/**
 * @author fengyu
 * @version 1.0
 * @Title: 生产者文件
 * @date 2020/11/23 9:49
 */
@Component
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    /**
     * @Description:没有回调发送数据
     *
     * @author fengyu
     * @date 2020/11/23 13:13
     * @return void
     */
    public void send() {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        //kafkaTemplate.send("topic1", gson.toJson(message));
        for (int i = 0;i < 10;i++){
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("topic1", i + "");
            future.addCallback(success -> {
                // 消息发送到的topic
                String topic = success.getRecordMetadata().topic();
                // 消息发送到的分区
                int partition = success.getRecordMetadata().partition();
                // 消息在分区内的offset
                long offset = success.getRecordMetadata().offset();
                System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
            },failure -> {
                System.out.println("发送消息失败:" + failure.getMessage());
            });
        }
    }
}
