package com.example.ZavodTelegramBot.kafka.consumer;

import com.example.ZavodTelegramBot.kafka.KafkaResponseRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMishMashConsumer {

    private KafkaResponseRegistry responseRegistry;

    @Autowired
    public KafkaMishMashConsumer(KafkaResponseRegistry responseRegistry) {
        this.responseRegistry = responseRegistry;
    }

    @KafkaListener(topics = "MishmashAns", groupId = "MishMashAnswerConsumer")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            responseRegistry.complete(record.key(), record.value());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
