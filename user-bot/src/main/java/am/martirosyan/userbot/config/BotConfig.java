package am.martirosyan.userbot.config;

import am.martirosyan.userbot.bot.UserBot;
import am.martirosyan.userbot.config.properties.BotProperties;
import am.martirosyan.userbot.repository.QuestionRepository;
import am.martirosyan.userbot.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public BotProperties botProperties() {
        return new BotProperties();
    }

    @Bean
    public UserBot userBot(BotProperties botProperties,
                           UserRepository userRepository, QuestionRepository questionRepository) {
        return new UserBot(
                botProperties.getUsername(),
                botProperties.getToken(),
                botProperties.getCreatorId(),
                userRepository,
                questionRepository
        );
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(UserBot userBot) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(userBot);
        return botsApi;
    }
}
