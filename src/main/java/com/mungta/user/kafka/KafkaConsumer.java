package com.mungta.user.kafka;

import com.mungta.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
@KafkaListener(topics = "accusation-topic", groupId ="com.example")
public class KafkaConsumer {

@Autowired
private UserService userService;

  @KafkaHandler
  public void AccusationCompleted(@Payload String payload) throws JsonMappingException, JsonProcessingException{

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    AccusationCompleted event = objectMapper.readValue(payload, AccusationCompleted.class);

    if(event.getEventType().equals(new Object(){}.getClass().getEnclosingMethod().getName())){
        userService.givePenaltyUser(event.getAccusedMemberId(),event.getPartyId());
    }
  }
}
