/**
 * Copyright (C) 2010-2016 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aliyun.openservices.tcp.producer;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.aliyun.openservices.tcp.MqConfig;

import java.util.Date;
import java.util.Properties;

/**
 * MQ发送普通消息示例 Demo
 */
public class SimpleMQProducer {


    public static void main(String[] args) {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.GROUP_ID, MqConfig.GROUP_ID);
        producerProperties.setProperty(PropertyKeyConst.AccessKey, MqConfig.ACCESS_KEY);
        producerProperties.setProperty(PropertyKeyConst.SecretKey, MqConfig.SECRET_KEY);
        producerProperties.setProperty(PropertyKeyConst.NAMESRV_ADDR, MqConfig.NAMESRV_ADDR);
        producerProperties.setProperty(PropertyKeyConst.INSTANCE_ID, "MQ_INST_edas_BdfH8n6w");
//        创建生产者实例
        Producer producer = ONSFactory.createProducer(producerProperties);
//        启动生产者：通过调用producer.start()方法启动生产者，准备发送消息
        producer.start();
        System.out.println("Producer Started");

//构造了10条消息，每条消息使用相同的主题（TOPIC）、标签（TAG，如果有的话），
// 以及一个字符串消息体（这里为"mq send transaction message test"的字节表示）。
// 消息通过调用producer.send(message)方法发送。
// 发送结果（SendResult）被用来验证消息是否成功发送，并且打印一条日志消息到控制台上。
        for (int i = 0; i < 10; i++) {
            Message message = new Message(MqConfig.TOPIC, MqConfig.TAG, "mq send transaction message test".getBytes());
            try {
                SendResult sendResult = producer.send(message);
                assert sendResult != null;
                System.out.println(new Date() + " Send mq message success! Topic is:" + MqConfig.TOPIC + " msgId is: " + sendResult.getMessageId());
            } catch (ONSClientException e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                System.out.println(new Date() + " Send mq message failed! Topic is:" + MqConfig.TOPIC);
                e.printStackTrace();
            }
        }
//        过调用producer.shutdown()方法关闭生产者实例，释放相关资源
        producer.shutdown();
    }
}
