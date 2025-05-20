package com.example.ZavodTelegramBot.kafka;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaResponseRegistry {

    private final Map<String, CompletableFuture<String>> pendingResponse = new ConcurrentHashMap<>();

    public void register(String requestId, CompletableFuture<String> future) {
        pendingResponse.put(requestId, future);
    }

    public void complete(String requestId, String response) {
        CompletableFuture<String> future = pendingResponse.remove(requestId);

        if (future != null) {
            future.complete(response);
        }
    }

}
