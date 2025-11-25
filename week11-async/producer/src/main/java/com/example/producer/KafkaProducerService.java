package com.example.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    public KafkaProducerService(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(UserEvent event) {
        kafkaTemplate.send("test-topic", event);
    }
}