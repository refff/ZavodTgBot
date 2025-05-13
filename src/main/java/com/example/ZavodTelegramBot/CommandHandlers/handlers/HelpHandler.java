package com.example.ZavodTelegramBot.CommandHandlers.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpHandler implements HandleCommand {

    @Override
    public String handle(Update update) {
        return """
                Правила использования бота:\s
                - все цифры дробные числа необходимо писать через точку;
                - форма отправки данных для рачета процента связки следующая:
                    + в первой строчке записывается масса каждой крышки;
                    + во второй строчке записывается масса крышки с сырым шликером;
                    + в третьей строчке записывается масса крышки с высушенным шликером;
                
                Итоговое сообщение для расчета связки имеет вид:
                xx.xx xx.xx xx.xx
                xx.xx xx.xx xx.xx
                xx.xx xx.xx xx.xx
                """;
    }
}
