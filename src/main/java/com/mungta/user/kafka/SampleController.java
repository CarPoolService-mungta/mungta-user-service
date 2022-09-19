package com.mungta.user.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/kafka/test")
public class SampleController {

    private final KafkaProducer producer;

    @Autowired
    SampleController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/message")
    public String sendMessage(@RequestParam("message") String message) {
      log.debug("com in here = " + message);
        this.producer.send(message);
        return "success";
    }
}
