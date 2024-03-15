package com.example.bd_dqsys;

import com.aliyun.openservices.tcp.consumer.SimpleOrderConsumer;
import com.aliyun.openservices.tcp.consumer.SimpleOrderConsumer2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdDqSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdDqSysApplication.class, args);
        // 启动消费者
        // SimpleOrderConsumer.main(args);
        SimpleOrderConsumer2.main(args);

    }

}
