package com.example.ZavodTelegramBot.controller;

import com.example.ZavodTelegramBot.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final KafkaProducer kafkaProducer;

    public Controller(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(path = "/kafka/send")
    public String send(@RequestBody String messsage) {

        kafkaProducer.sendMessage(messsage);

        return "success";
    }
}
