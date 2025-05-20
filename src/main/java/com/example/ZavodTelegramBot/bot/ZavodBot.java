package com.example.ZavodTelegramBot.bot;

import com.example.ZavodTelegramBot.Calculation.Calculator;
import com.example.ZavodTelegramBot.CommandHandlers.CommandHandler;
import com.example.ZavodTelegramBot.infrastructure.BotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private CommandHandler commandHandler;
    private BotProperties properties;
    private Calculator calculator;

    private boolean ingredients = false;
    private boolean mishmash = false;

    @Autowired
    public ZavodBot(CommandHandler commandHandler,
                    @Qualifier("botProperties") BotProperties properties,
                    Calculator calculator) {
        this.commandHandler = commandHandler;
        this.properties = properties;
        this.client = new OkHttpTelegramClient(getBotToken());
        this.calculator = calculator;
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
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

            String topic = null;

            if (ingredients) {
                ingredients = false;
                topic = "ingredients";
            } else if (mishmash) {
                mishmash = false;
                topic = "test";
            }

            sendMessage(calculator.calculate(update, topic));
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
