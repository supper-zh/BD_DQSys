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
package com.aliyun.openservices.tcp.example;

/**
 * MQ 配置
 * 使用阿里云消息队列（MQ）服务的Java应用程序，分为生产者（producer）、消费者（consumer）和配置（config）三个主要部分。
 * MqConfig.java文件中包含了配置阿里云MQ服务所需的一些基本信息。
 * 下面是对这个配置文件的详细解释：
 * TOPIC和ORDER_TOPIC：这两个常量定义了消息主题（topic），
 * 它是消息发布和订阅的地址，可以理解为一个消息队列的名字。在这个例子中，
 * 两者都被设置为"sg_ax_beidou_track"，这意味着生产者和消费者将会向这个主题发送和接收消息。
 * GROUP_ID和ORDER_GROUP_ID：这两个常量定义了消费者组的ID。
 * 在MQ中，同一个消费者组内的所有消费者共同消费某个主题的消息，以实现负载均衡和消息广播。
 * 这里，两者都被设置为"GID_sg_beidou_ZFW"。
 * ACCESS_KEY和SECRET_KEY：这两个常量提供了访问阿里云MQ服务的认证信息。
 * ACCESS_KEY是访问密钥ID，而SECRET_KEY是密钥。这对密钥用于验证客户端的身份，
 * 确保消息传输的安全。
 * TAG：这个常量用于标记特定的消息，使消费者可以根据标签选择性地消费消息。
 * 这里它被设置为null，意味着不对消息进行特别的标记。
 * NAMESRV_ADDR：这个常量定义了MQ服务的TCP协议接入点地址。
 * 它是MQ服务的网络地址，客户端通过这个地址与MQ服务建立连接。
 * 这里被设置为"http://mq.namesrv.paas.sgpt.gov:0000"。
 */
public class MqConfig {
    /**
     * 启动测试之前请替换如下 XXX 为您的配置
     */
    public static final String TOPIC = "sg_ax_beidou_track";
    public static final String GROUP_ID = "GID_sg_beidou_ZFW";
    public static final String ORDER_TOPIC = "sg_ax_beidou_track";
    public static final String ORDER_GROUP_ID = "GID_sg_beidou_ZFW";
    public static final String ACCESS_KEY = "6f181321efd9449ba45d2c69796b17f5";
    public static final String SECRET_KEY = "OopH5XhRhlZfCg/O7iFaWotHkLQ=";
    public static final String TAG = null;

    /**
     * NAMESRV_ADDR, 请在mq控制台 https://ons.console.aliyun.com 通过"实例管理--获取接入点信息--TCP协议接入点"获取
     */
//    public static final String NAMESRV_ADDR = "http://127.0.0.1:8081";
    public static final String NAMESRV_ADDR = "http://mq.namesrv.paas.sgpt.gov:9876";

}
