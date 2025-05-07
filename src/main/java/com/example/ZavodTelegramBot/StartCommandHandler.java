package com.example.ZavodTelegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommandHandler implements HandleCommand{

    public String handle(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();
        var userId = user.getId();

        if (user.getUserName().equals("vralmann"))
            return "Hello my nigga, glad to see ya";
        else if (user.getUserName().equalsIgnoreCase("matvey_1809"))
            return "Damn nigga why are you so big";
        else
            return "Glad to see you " + user.getUserName();
    }
}
