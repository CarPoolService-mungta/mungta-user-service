package com.mungta.user.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.retry.annotation.EnableRetry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@EnableRetry
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.cloud.stream.kafka.binder.brokers}")
    private String bootstrapServers;

    // @Value("${spring.kafka.consumer.group-id}")
    // private String groupId;

    // @Value("${spring.kafka.consumer.retry.back-off-period}")
    // private Integer backOffPeriod;

    // @Value("${spring.kafka.consumer.retry.max-attempts}")
    // private Integer maxAttempts;

    @Bean
    public Map<String, Object> consumerConfigs() {

      log.debug("Kafka bootstrapServers = " + bootstrapServers);

        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kafka cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // allow a pool of processes to divide the work of consuming and processing records
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "com.example");
        // automatically reset the offset to the earliest offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }
 }

