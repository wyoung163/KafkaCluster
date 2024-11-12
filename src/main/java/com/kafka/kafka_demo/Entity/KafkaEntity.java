package com.kafka.kafka_demo.Entity;

import lombok.*;

/*
    메시지로 전달할 엔티티
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaEntity {
    private String id;
    private String message;
}