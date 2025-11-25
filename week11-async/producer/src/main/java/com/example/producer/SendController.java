package com.example.producer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SendController {
    private final KafkaProducerService producer;
    public SendController(KafkaProducerService producer){ this.producer = producer; }

    @PostMapping("/send")
    public String send(@RequestBody UserEvent event){
        producer.send(event);
        return "sent";
    }
}