package com.example.ZavodTelegramBot.Calculation;

import com.example.ZavodTelegramBot.kafka.KafkaResponseRegistry;
import com.example.ZavodTelegramBot.kafka.consumer.KafkaIngredientConsumer;
import com.example.ZavodTelegramBot.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class Calculator {

    private KafkaProducer producer;
    private KafkaResponseRegistry responseRegistry;

    @Autowired
    public Calculator(KafkaProducer producer,
                      KafkaResponseRegistry kafkaResponseRegistry) {
        this.producer = producer;
        this.responseRegistry = kafkaResponseRegistry;
    }

    public SendMessage calculate(Update update, String topic) {
        String message = update.getMessage().getText();

        String key = UUID.randomUUID().toString();
        CompletableFuture<String> future = register(key);

        producer.sendMessage(topic, key, message);

        String response = getResponse(future);

        return new SendMessage(update.getMessage().getFrom().getId().toString(), response);
    }

    private CompletableFuture<String> register(String key) {
        CompletableFuture<String> future = new CompletableFuture<>();
        responseRegistry.register(key, future);

        return future;
    }

    private String getResponse(CompletableFuture<String> future) {
        String response;
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return  response;
    }
}
