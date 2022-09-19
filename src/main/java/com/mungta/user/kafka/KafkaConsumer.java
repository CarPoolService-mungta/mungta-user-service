package com.mungta.user.kafka;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
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
  public void wheneverAccusationCompleted_GivePenaltyUser(@Payload String userId){
    log.debug("Kafka Accusation Consumed message = " + userId);
    userService.givePenaltyUser(userId);
  }
}
