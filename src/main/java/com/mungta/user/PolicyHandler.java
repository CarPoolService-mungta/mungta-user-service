package com.mungta.user;

import com.mungta.user.kafka.KafkaProcessor;
import com.mungta.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
/*
    @Autowired
    private UserService userService;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverAccusationCompleted_GivePenaltyUser(@Payload String userId ){

        // 신고에서 topic 받아서 페널티 횟수 업데이트 해야할듯
        userService.givePenaltyUser(userId);

    }


 */
}


