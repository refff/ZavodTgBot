package com.example.ZavodTelegramBot.Calculation;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public interface Calculator {
    SendMessage calculate(Update update);
}
