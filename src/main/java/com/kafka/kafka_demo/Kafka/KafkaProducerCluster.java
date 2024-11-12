package com.kafka.kafka_demo.Kafka;

import com.kafka.kafka_demo.Entity.KafkaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerCluster {
    private KafkaTemplate<String, KafkaEntity> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    public void sendMessage(KafkaEntity kafkaEntity) {
        Message<KafkaEntity> message = MessageBuilder
                .withPayload(kafkaEntity)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        CompletableFuture<SendResult<String, KafkaEntity>> future = kafkaTemplate.send(message);

        future.whenComplete((result, ex) -> {
            if(ex == null) {
                log.info("producer: success >>> message: {}, offset: {}",
                        result.getProducerRecord().value().toString(), result.getRecordMetadata().offset());
            } else {
                log.info("producer: failure >>> message: {}", ex.getMessage());
            }
        });
    }
}
