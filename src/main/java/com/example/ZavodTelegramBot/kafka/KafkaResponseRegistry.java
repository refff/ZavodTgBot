package com.example.ZavodTelegramBot.kafka;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class KafkaResponseRegistry {

    private final Map<String, CompletableFuture<ObjectNode>> pendingResponse = new ConcurrentHashMap<>();

    public void register(String requestId, CompletableFuture<ObjectNode> future) {
        pendingResponse.put(requestId, future);
    }

    public void complete(String requestId, ObjectNode response) {
        CompletableFuture<ObjectNode> future = pendingResponse.remove(requestId);

        if (future != null) {
            future.complete(response);
        }
    }

}
