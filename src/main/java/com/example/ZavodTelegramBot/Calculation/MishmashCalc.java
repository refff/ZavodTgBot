package com.example.ZavodTelegramBot.Calculation;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Component
public class MishmashCalc implements Calculator {

    @Override
    public SendMessage calculate(Update update) {
        String[] requestList = update.getMessage().getText().split("\n");
        List<ArrayList<Double>> ddt = convertToDoubleList(requestList);


        return new SendMessage(update.getMessage().getFrom().getId().toString(),
                capsCalc(ddt.get(0), ddt.get(1), ddt.get(2)));
    }

    private  String capsCalc(ArrayList<Double> empty, ArrayList<Double> filled, ArrayList<Double> cooked) {
        ArrayList<Double> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            data.add(((filled.get(i) - cooked.get(i)) /  (filled.get(i) - empty.get(i))) * 100);
        }

        double avg = 0;
        for (Double num:data) avg += num;
        avg = avg / 3;

        return String.format("""
                Процент связки в каждой крышке:\s
                1 - %.2f
                2 - %.2f
                3 - %.2f
                Среднее значение - %.2f""", data.get(0), data.get(1), data.get(2), avg);
    }

    private ArrayList<ArrayList<Double>> convertToDoubleList(String[] request) {
        ArrayList<ArrayList<Double>> result = new ArrayList<>();

        for (String line: request) {
            ArrayList<Double> row = new ArrayList<>();
            String[] tempRow = line.split(" ");

            for (String num: tempRow) {
                row.add(Double.parseDouble(num));
            }
            result.add(row);
        }

        return result;
    }
}
