package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Класс, хранящий точку входа приложения.
 *
 * @author Aurelius
 */
@SpringBootApplication
public class DiscordBotServerApplication {
	/**
	 * Точка входа приложения.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DiscordBotServerApplication.class, args);
	}
}
