package com.example.ZavodTelegramBot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private RequestController controller;
    @Autowired
    private CommandHandler commandHandler;

    private ReplyKeyboardMarkup keyboardM1;
    private InlineKeyboardMarkup keyboardM2;

    @Override
    public String getBotUsername() {
        return "ZavodCalc_bot";
    }

    //move token to database
    @Override
    public String getBotToken() {
        return "7693471844:AAH7OaGEY5Pp8LnUEn2Gb51M5t0uNGmExAs";
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            if (update.getMessage().getText().equals("/menu")) {
                //assignKeyboards();
                sendMenu(update.getMessage().getFrom().getId(), "<b>Choose needed option</b>", keyboardM1);
            }

            execute(controller.requestMapping(update));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void assignKeyboards() {
        /*var next = InlineKeyboardButton.builder()
                .text("Ингридиенты").callbackData("next")
                .build();

        var back = InlineKeyboardButton.builder()
                .text("Связка").callbackData("back")
                .build();

        var url = InlineKeyboardButton.builder()
                .text("Tutorial")
                .url("https://core.telegram.org/bots/api")
                .build();
*/
        KeyboardRow row = new KeyboardRow();
        row.add("Ингридиенты");
        row.add("Связка");

        keyboardM1 = ReplyKeyboardMarkup.builder()
                .keyboardRow(row).build();

        /*keyboardM2 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(back))
                .keyboardRow(List.of(url))
                .build();*/
    }

    public void sendMenu(Long who, String txt, ReplyKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
