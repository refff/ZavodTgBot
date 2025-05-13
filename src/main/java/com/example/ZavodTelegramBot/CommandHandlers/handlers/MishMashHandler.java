package com.example.ZavodTelegramBot.CommandHandlers.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class MishMashHandler implements HandleCommand {

    private ReplyKeyboardMarkup keyboardM1;

    @Override
    public String handle(Update update) {
        return "Введите следующие значения: \n" +
                "- массу пустых крышек;\n" +
                "- массу наполненных крышек;\n" +
                "- массу крышек после термической обработки;\n"
                ;

    }
}
