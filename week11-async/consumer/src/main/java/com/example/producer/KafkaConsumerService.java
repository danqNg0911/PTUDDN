package com.example.producer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(UserEvent event) {
        System.out.println("Received: " + event);
    }
}