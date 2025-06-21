package am.martirosyan.adminbot.config;

import am.martirosyan.adminbot.bot.AdminBot;
import am.martirosyan.adminbot.config.properties.BotProperties;
import am.martirosyan.adminbot.service.QuestionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public BotProperties botProperties() {
        return new BotProperties();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(AdminBot adminBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(adminBot);
        return botsApi;
    }

    @Bean
    public AdminBot questionBot(QuestionService questionService) {
        return new AdminBot(
                botProperties().getToken(),
                botProperties().getUsername(),
                botProperties().getCreatorId(),
                questionService
        );
    }
}
