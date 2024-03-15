package com.aliyun.openservices.tcp.example.consumer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.aliyun.openservices.tcp.example.MongoDBConfig;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MessageOrderListenerImpl implements MessageOrderListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageOrderListenerImpl.class);
    private static final MongoClient mongoClient;
    private static final MongoCollection<Document> collection;

    // MongoClient的复用：MongoClient和MongoCollection被声明为静态变量，并在静态初始化块中初始化。
    // 这确保了整个应用程序只创建和使用一个MongoDB连接
    // 静态初始化块
    static {
        // 直接使用MongoDBConfig类的静态方法获取配置信息
        mongoClient = MongoClients.create("mongodb://" + MongoDBConfig.getHost() + ":" + MongoDBConfig.getPort());
        MongoDatabase database = mongoClient.getDatabase(MongoDBConfig.getDatabase());
        collection = database.getCollection(MongoDBConfig.getCollection());

        // 添加JVM关闭钩子以关闭MongoClient
        // 通过Runtime.getRuntime().addShutdownHook添加了一个关闭钩子，当应用程序关闭时，这个钩子会调用mongoClient.close()来清理资源。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing MongoClient.");
            mongoClient.close();
        }));
    }

    @Override
    public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        try {
            String body = new String(message.getBody(), "UTF-8");
            if (body == null || body.isEmpty()) {
                logger.warn("Received empty message body.");
                return OrderAction.Success; // 或根据实际情况采取其他行动
            }
            // System.out.println("Received message: " + body);
            // 使用日志框架代替直接使用System.out.println
            // 日志框架提供了更多功能，如日志级别控制、日志文件的自动切割、异步日志记录等，这些都是在生产环境中非常有用的特性。
            // 以SLF4J配合Logback为例
            logger.info("message.getBody():{}", body);

            Document doc = Document.parse(body);
            // collection.insertOne(doc);
            // 单个插入改成批量插入：
            List<Document> documents = new ArrayList<>();
            // 添加文档到列表中
            documents.add(doc);
            try {
                // collection.insertOne(doc);
                collection.insertMany(documents);
            } catch (MongoWriteException e) {
                logger.error("Insertion failed due to write error: ", e);
            } catch (MongoException e) {
                logger.error("Insertion failed due to MongoDB error: ", e);
            }

        } catch (Exception e) {
            // System.err.println("Error processing message: " + e.getMessage());
            // e.printStackTrace();
            // 移除了 System.err.println 和 e.printStackTrace() 的调用，使用 logger.error 来记录错误，这样可以保持日志记录的一致性
            logger.error("Error processing message: ", e);
            return OrderAction.Suspend; // 如果处理失败，则请求消息系统稍后重试
        }
        return OrderAction.Success; // 消息处理成功
    }
}


// public class MessageOrderListenerImpl implements MessageOrderListener {
//
//     private MongoCollection<Document> collection;
//
//     public MessageOrderListenerImpl() {
//         MongoClient mongoClient = MongoClients.create("mongodb://" + MongoDBConfig.getHost() + ":" + MongoDBConfig.getPort());
//         MongoDatabase database = mongoClient.getDatabase(MongoDBConfig.getDatabase());
//         this.collection = database.getCollection(MongoDBConfig.getCollection());
//     }
//
//     @Override
//     public OrderAction consume(final Message message, final ConsumeOrderContext context) {
//         try {
//             String body = new String(message.getBody(),"UTF-8");
//             // 打印消息内容
//             System.out.println("message.getBody():"+body);
//             // 将接收到的消息字符串转换为Document
//             Document doc = Document.parse(body);
//             // 插入到MongoDB集合中
//             collection.insertOne(doc);
//
//         } catch (UnsupportedEncodingException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }
//         return OrderAction.Success;
//     }
// }
