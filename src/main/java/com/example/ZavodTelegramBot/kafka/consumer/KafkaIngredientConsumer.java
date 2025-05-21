package com.example.ZavodTelegramBot.kafka.consumer;

import com.example.ZavodTelegramBot.kafka.KafkaResponseRegistry;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaIngredientConsumer {

    private KafkaResponseRegistry responseRegistry;

    @Autowired
    public KafkaIngredientConsumer(KafkaResponseRegistry responseRegistry) {
        this.responseRegistry = responseRegistry;
    }

    @KafkaListener(topics = "IngredientAns", groupId = "IngredientAnswerConsumer")
    public void consume(ConsumerRecord<String, ObjectNode> record) {
        try {
            responseRegistry.complete(record.key(), record.value());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
