/*
package com.example.ZavodTelegramBot.manager;

import com.example.ZavodTelegramBot.Calculation.Calculator;
import com.example.ZavodTelegramBot.Calculation.IngredientCalc;
import com.example.ZavodTelegramBot.Calculation.MishmashCalc;
import com.example.ZavodTelegramBot.CommandHandlers.CommandHandler;
import com.example.ZavodTelegramBot.bot.ZavodBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class UpdateDispatcher {

    private boolean ingredients = false;
    private boolean mishmash = false;

    private Calculator calculator;
    private CommandHandler commandHandler;

    @Autowired
    public UpdateDispatcher(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public BotApiMethod<?> distribute(Update update, ZavodBot bot) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();

            if (update.getMessage().isCommand()) {

                if (message.equals("/mishmash"))
                    mishmash = true;
                else if (message.equals("/ingredients"))
                    ingredients = true;

                sendMessage(commandHandler.handleCommand(update), bot);
                return null;

            }

            if (ingredients) {
                calculator = new IngredientCalc();
                sendMessage(calculator.calculate(update), bot);

                ingredients = false;
            } else if (mishmash) {
                calculator = new MishmashCalc();
                sendMessage(calculator.calculate(update), bot);

                mishmash = false;
            }
        }
        return null;
    }

    private void sendMessage(SendMessage msg, ZavodBot bot) {
        try {
            bot.execute(msg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
*/
