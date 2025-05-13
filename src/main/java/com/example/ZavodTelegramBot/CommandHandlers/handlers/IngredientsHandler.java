package com.example.ZavodTelegramBot.CommandHandlers.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IngredientsHandler implements HandleCommand {

    @Override
    public String handle(Update update) {
        return "Введите массу порошка";
    }
}
