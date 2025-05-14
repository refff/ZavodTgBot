package com.example.ZavodTelegramBot;

import com.example.ZavodTelegramBot.infrastructure.BotProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BotProperties.class)
public class ZavodTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZavodTelegramBotApplication.class, args);
	}
}
