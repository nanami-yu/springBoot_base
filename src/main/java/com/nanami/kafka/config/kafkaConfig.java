package com.nanami.kafka.config;

import com.nanami.kafka.interceptor.KafkaInterceptor;
import com.nanami.kafka.partitoner.MyPartitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fengyu
 * @version 1.0
 * @Title: kafka配置文件
 * @date 2020/11/24 10:26
 */
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class kafkaConfig {



    @Bean
    public ProducerFactory<Object,Object> kafkaProducerFactory(KafkaProperties kafkaProperties){
        Map<String,Object> properties = kafkaProperties.buildProducerProperties();
        //自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartitioner.class);
        //自定义拦截器
        List<Object> interceptorList = new ArrayList<>();
        interceptorList.add("com.nanami.kafka.interceptor.KafkaInterceptor");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorList);
        return new DefaultKafkaProducerFactory<>(properties);
    }
}
