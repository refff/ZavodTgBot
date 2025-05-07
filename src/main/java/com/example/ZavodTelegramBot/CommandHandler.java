package com.example.ZavodTelegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {

    HandleCommand handleCommand;

    @Autowired
    private StartCommandHandler startCommandHandler;

    public SendMessage handleCommand(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();
        var userId = user.getId();

        if (message.getText().equals("/start")) {
            handleCommand = new StartCommandHandler();
            String txt  = handleCommand.handle(update);
            return sendText(userId, txt);
        }

        return null;
    }

    public SendMessage handleButtons(Update update) {
        return null;
    }

    public SendMessage sendText(Long who, String what) {
        return SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
    }
}
