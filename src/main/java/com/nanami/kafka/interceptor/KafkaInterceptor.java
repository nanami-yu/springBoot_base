package com.nanami.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author fengyu
 * @version 1.0
 * @Title: kafka拦截器
 * @date 2020/11/24 14:20
 */
public class KafkaInterceptor implements ProducerInterceptor<String,String> {

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        String value = producerRecord.value();
        System.out.println(">>>>" + producerRecord.value());
        return new ProducerRecord<String,String>(producerRecord.topic(),
                producerRecord.partition(),producerRecord.key(),System.currentTimeMillis() + "--" + value);
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
