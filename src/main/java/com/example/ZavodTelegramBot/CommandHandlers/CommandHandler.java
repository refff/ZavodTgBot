package com.example.ZavodTelegramBot.CommandHandlers;

import com.example.ZavodTelegramBot.CommandHandlers.handlers.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {

    private HandleCommand handleCommand;

    public SendMessage handleCommand(Update update) {
        var message = update.getMessage().getText();
        var userId = update.getMessage().getFrom().getId();

        if (message.equals("/start")) {
            handleCommand = new StartHandler();
        } else if (message.equals("/mishmash")) {
            handleCommand = new MishMashHandler();
        } else if (message.equals("/ingredients")) {
            handleCommand = new IngredientsHandler();
        } else if (message.equals("/help")) {
            handleCommand = new HelpHandler();
        }

        String txt  = handleCommand.handle(update);
        return createText(userId, txt);
    }

    private SendMessage createText(Long who, String what) {
        return SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
    }
}
