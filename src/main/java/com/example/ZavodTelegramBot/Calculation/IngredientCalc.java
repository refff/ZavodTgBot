package com.example.ZavodTelegramBot.Calculation;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class IngredientCalc implements Calculator {

    @Override
    public SendMessage calculate(Update update) {
        double mass = Double.parseDouble(update.getMessage().getText().toString());

        double paraffin = (mass * 11.6) / 87.5;
        double wax = (mass * 0.5) / 87.5;
        double acid = (mass * 400) / (87.5 * 0.895);

        String message = String.format("Масса ингридиентов: \n\n" +
                "Парафин - %.2f кг.\n" +
                "Воск - %.3f кг.\n" +
                "Олейновая кислота - %.1f мл.", paraffin, wax, acid);


        return new SendMessage(update.getMessage().getFrom().getId().toString(), message.toString());
    }
}
