package com.mungta.user.kafka;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungta.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {

  @Autowired
  private UserService userService;

  @KafkaListener(topics = "mungta", groupId ="com.mungta")
  public void consume(String message) throws IOException {
    log.debug("Kafka Consumed message = " + message);
  }

  @KafkaListener(topics = "accusation-topic", groupId ="com.example")
  public void AccusationCompleted(@Payload String payload) throws JsonMappingException, JsonProcessingException{
    log.debug("Kafka Accusation Consumed message = " + payload);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    AbstractEvent abstractEvent = objectMapper.readValue(payload, AbstractEvent.class);
    if(abstractEvent.getEventType().equals(new Object(){}.getClass().getEnclosingMethod().getName())){
      userService.givePenaltyUser(abstractEvent.getUserId(),payload);
    }
  }
}
//@KafkaListener(topics = "mungta", groupId ="com.mungta")
//String payload = "{\"eventType\":\"AccusationCompleted\",\"timestamp\":\"2022-09-20 22:23:18\",\"accusedMemberId\":\"2\"}";
