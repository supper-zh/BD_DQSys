package com.example.bd_dqsys;

import com.aliyun.openservices.tcp.consumer.SimpleOrderConsumer;
import com.aliyun.openservices.tcp.consumer.SimpleOrderConsumer1;
import com.aliyun.openservices.tcp.consumer.SimpleOrderConsumer2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdDqSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdDqSysApplication.class, args);
        // 启动消费者:
        // 1. 用于测试
        // SimpleOrderConsumer.main(args);
        // 2. 第1种实现
        // SimpleOrderConsumer1.main(args);
        // 2. 第2种实现
        SimpleOrderConsumer2.main(args);

    }

}
