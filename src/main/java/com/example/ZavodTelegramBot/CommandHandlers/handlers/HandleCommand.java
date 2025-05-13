package com.example.ZavodTelegramBot.CommandHandlers.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface HandleCommand {
    String handle(Update update);
}
