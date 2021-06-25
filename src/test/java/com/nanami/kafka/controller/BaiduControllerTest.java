package com.nanami.kafka.controller;

import com.nanami.kafka.producer.Producer;
import com.nanami.kafka.service.BaiduService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaiduControllerTest {

    @Autowired
    private BaiduService baiduService;

    @Test
    public void testSend() {
        baiduService.getAuth();
    }

    @Test
    public void test(){
        int a  = 1;
        boolean isOk = false;
        do{
            System.out.println("正在进行" + a);
            if(a >=3){
                isOk = true;
            }
            a++;
        }while (!isOk);
    }
}