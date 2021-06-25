package com.nanami.kafka.partitoner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fengyu
 * @version 1.0
 * @Title: 自定义分区器
 * @date 2020/11/24 10:05
 */
public class MyPartitioner implements Partitioner {

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        //TODO 根据业务逻辑自定义分区
        //System.out.println("1");
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
