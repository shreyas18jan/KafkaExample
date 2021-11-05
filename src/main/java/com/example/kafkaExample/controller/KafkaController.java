package com.example.kafkaExample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api")
@EnableKafka
@Service
public class KafkaController {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private final String topicName = "KafkaTopic";

    @PostMapping("/publish_message_v1/{message}")
    public String publishMessageV1(@PathVariable String message) {
        kafkaTemplate.send(topicName, message);
        return "Published V1 Message Successfully!";
    }

    @KafkaListener(topics = topicName, groupId = "group_id")
    public void consumeMessageV1(String message) {
        System.out.println("Consumed the message : " + message);
    }
}
