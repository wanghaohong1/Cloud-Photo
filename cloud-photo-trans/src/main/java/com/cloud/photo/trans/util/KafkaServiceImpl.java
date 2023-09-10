package com.cloud.photo.trans.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl {
    @Autowired
    private KafkaTemplate<?, String> kafkaTemplate;
    public static final String FILE_IMAGE_TOPIC = "file-image-topic";
    /**
     * 发送kafka消息
     * @param message
     */
    public void send(String message){
        // 发送消息
        kafkaTemplate.send(FILE_IMAGE_TOPIC, message);
        System.out.println("send success! message= " + message);
    }
//    /**
//     * * 读取file_image_topic里面的消息
//     * @param record
//     */
//    @KafkaListener(topics = {"file-image-topic", "file-audit-topic"})
//    public void onMessage(ConsumerRecord<String,String> record){
//        System.out.println("onMessage,key="+record.key()+",value="+record.value());
//    }
}
