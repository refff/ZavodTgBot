package com.example.ZavodTelegramBot;

import com.example.ZavodTelegramBot.Calculation.Calculator;
import com.example.ZavodTelegramBot.Calculation.IngredientCalc;
import com.example.ZavodTelegramBot.Calculation.MishmashCalc;
import com.example.ZavodTelegramBot.CommandHandlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class ZavodBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient client;

    private boolean ingredients = false;
    private boolean mishmash = false;

    @Autowired
    private CommandHandler commandHandler;
    private Calculator calculator;

    public ZavodBot() {
        this.client = new OkHttpTelegramClient(getBotToken());
    }

    @Override
    public String getBotToken() {
        return "7693471844:AAH7OaGEY5Pp8LnUEn2Gb51M5t0uNGmExAs";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message = update.getMessage().getText();

            if (update.getMessage().isCommand()) {

                if (message.equals("/mishmash"))
                    mishmash = true;
                else if (message.equals("/ingredients"))
                    ingredients = true;

                sendMessage(commandHandler.handleCommand(update));
                return;

            }

            if (ingredients) {
                calculator = new IngredientCalc();
                sendMessage(calculator.calculate(update));

                ingredients = false;
            } else if (mishmash) {
                calculator = new MishmashCalc();
                sendMessage(calculator.calculate(update));

                mishmash = false;
            }


            /*SendMessage message = SendMessage.builder()
                    .chatId(chat_id)
                    .text(message_text)
                    .build();
            try {
                client.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
        }
    }

    private void sendMessage(SendMessage msg) {
        try {
            client.execute(msg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
