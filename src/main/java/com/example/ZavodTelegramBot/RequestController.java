package com.example.ZavodTelegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class RequestController {

    @Autowired
    private CommandHandler commandHandler;

    public SendMessage requestMapping(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();

        if (message.isCommand())
            return commandHandler.handleCommand(update);
        else if (message.getText().equals("Ингридиенты") || message.getText().equals("Связка"))
            return commandHandler.handleButtons(update);

        System.out.println("get \"" + message.getText() + "\" from " + user.getUserName());

        return null;
    }
}
