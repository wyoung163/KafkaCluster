package com.kafka.kafka_demo.Controller;

import com.kafka.kafka_demo.Entity.KafkaEntity;
import com.kafka.kafka_demo.Kafka.KafkaProducerCluster;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaControllerCluster {

    private final KafkaProducerCluster producer = new KafkaProducerCluster();

    @PostMapping("/kafka/produce")
    public String sendMessage(@RequestBody KafkaEntity message) {
        producer.sendMessage(message);
        return "ok";
    }

}
