package com.nanami.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

/**
 * @author fengyu
 * @version 1.0
 * @Title: kafka消费者
 * @date 2020/11/24 10:41
 */

@Component
public class Consumer {

    /**
     * @Description:监听topic1  暂时关闭
     *
     * @param record
     * @author fengyu
     * @date 2020/11/24 10:51
     * @return void
     */
    //@KafkaListener(topics = {"topic1"})
    public void onMessage0(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费全部：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

    /**
     * @Description: kafka 监听暂时关闭
     *
     * @param record
     * @author fengyu
     * @date 2020/11/24 13:35
     * @return void
     */
//    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
//            @TopicPartition(topic = "topic1", partitions = { "0" }),
//            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
//    })
    public void onMessage1(ConsumerRecord<?, ?> record) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费0号：" + record.topic() + "-" + record.partition() + "-" + record.value());
    }

}
