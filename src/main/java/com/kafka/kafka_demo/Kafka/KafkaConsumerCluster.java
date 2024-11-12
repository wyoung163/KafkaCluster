package com.kafka.kafka_demo.Kafka;

import com.kafka.kafka_demo.Entity.KafkaEntity;
import com.kafka.kafka_demo.KafkaDemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Slf4j
@Component
public class KafkaConsumerCluster {
    @KafkaListener(topics = "${sprig.kafka.template.default-topic}",
            groupId = "${spring.kafka.template.group-id}")
    public void consume(@Payload KafkaEntity message,
                       @Headers MessageHeaders messageHeaders) {
        /*
            Header: 메시지의 offset, partion 정보, 토픽 이름 등
         */
        log.info("consumer: success >>> message: {}, headers: {}", message.toString(), messageHeaders);
    }
}
