package com.example.ZavodTelegramBot.CommandHandlers.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartHandler implements HandleCommand {

    @Override
    public String handle(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();

        if (user.getUserName().equals("vralmann"))
            return "Hello my nigga, glad to see ya";
        else if (user.getUserName().equalsIgnoreCase("matvey_1809"))
            return "Damn nigga why are you so big";
        else
            return "Добро пожаловать, для получения информации о том," +
                    " как пользоваться ботом - оправьте комманду /help";
    }
}
