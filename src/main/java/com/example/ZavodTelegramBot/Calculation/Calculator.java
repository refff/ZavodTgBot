package com.example.ZavodTelegramBot.Calculation;

import com.example.ZavodTelegramBot.kafka.KafkaResponseRegistry;
import com.example.ZavodTelegramBot.kafka.producer.KafkaProducer;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private final String  ingredientResponse = "Масса ингридиентов: \n\nПарафин - %.2f кг.\n" +
    "Воск - %.3f кг.\nОлейновая кислота - %.1f мл.";
    private final String  mishMashResponse = "Процент связки в каждой крышке:\s\n1 - %.2f\n2 - %.2f\n3 - %.2f\nСреднее значение - %.2f";

    @Autowired
    public Calculator(KafkaProducer producer,
                      KafkaResponseRegistry kafkaResponseRegistry) {
        this.producer = producer;
        this.responseRegistry = kafkaResponseRegistry;
    }

    public SendMessage calculate(Update update, String topic) {
        String message = update.getMessage().getText();

        String key = UUID.randomUUID().toString();
        CompletableFuture<ObjectNode> future = register(key);

        producer.sendMessage(topic, key, message);

        ObjectNode json = getResponse(future);
        String response = convertToString(json);

        return new SendMessage(update.getMessage().getFrom().getId().toString(), response);
    }

    private CompletableFuture<ObjectNode> register(String key) {
        CompletableFuture<ObjectNode> future = new CompletableFuture<>();
        responseRegistry.register(key, future);

        return future;
    }

    private ObjectNode getResponse(CompletableFuture<ObjectNode> future) {
        ObjectNode response;
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return  response;
    }

    private String convertToString(ObjectNode json) {
        String result = null;

        if (json.size() == 3) {
            result = String.format(ingredientResponse, json.get("paraffin").asDouble(),
                    json.get("wax").asDouble(), json.get("acid").asDouble());
        } else if (json.size() == 4) {
            result = String.format(mishMashResponse, json.get("1").asDouble(), json.get("2").asDouble(),
                    json.get("3").asDouble(), json.get("average").asDouble());
        } else {
            result = "Something went wrong, there is error in message transferring. Please, try again";
        }

        return result;
    }
}
