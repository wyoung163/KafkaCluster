package com.kafka.kafka_demo.Config;

import com.kafka.kafka_demo.Entity.KafkaEntity;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfigCluster {
    @Value("${spring.kafka.bootstrap-servers")
    private String booststrapAddress;

    @Value("${spring.kafka.cosumer.group-id")
    private String groupId;

    @Bean
    public ConsumerFactory<String, KafkaEntity> pushEntityConsumerFactory() {
        JsonDeserializer<KafkaEntity> deserializer = gcmPushEntityJsonDeserializer();
        return new DefaultKafkaConsumerFactory<>(
                cosumerFactoryConfig(deserializer),
                new StringDeserializer(),
                deserializer);
    }

    private Map<String, Object> cosumerFactoryConfig(JsonDeserializer<KafkaEntity> deserializer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, booststrapAddress);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return configProps;
    }

    private JsonDeserializer<KafkaEntity> gcmPushEntityJsonDeserializer() {
        JsonDeserializer<KafkaEntity> deserializer = new JsonDeserializer<>(KafkaEntity.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaEntity>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEntity> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pushEntityConsumerFactory());
        return factory;
    }

}
