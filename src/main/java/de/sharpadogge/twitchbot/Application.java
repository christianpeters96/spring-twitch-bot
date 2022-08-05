package de.sharpadogge.twitchbot;

import de.sharpadogge.twitchbot.modules.bot.BotClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableScheduling
@SpringBootApplication
public class Application implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BotClient.context = applicationContext;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
