package am.martirosyan.adminbot.bot;

import am.martirosyan.adminbot.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class AdminBot extends AbilityBot {
    private final long creatorId;
    private final QuestionService questionService;

    public AdminBot(String token, String username, long creatorId,
                    QuestionService questionService) {
        super(token, username);
        this.creatorId = creatorId;
        this.questionService = questionService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        super.onUpdateReceived(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            try {
                questionService.saveQuestionFromString(messageText);
                sendMessage(chatId, "Вопрос успешно сохранен!");
            } catch (Exception e) {
                String text = """
                        Чтобы сохранить вопрос используйте формат:
                        Описание вопроса
                        URL_картинки
                        Ответ1
                        Ответ2*
                        Ответ3
                        
                        (звездочка указывает на правильный ответ)
                        Пример:
                        Какой цвет неба?
                        https://example.com/image.jpg
                        Синий*
                        Красный
                        Зеленый
                        """;
                sendMessage(chatId, text);
            }
        }

    }

    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .build();
        try {
            execute(sendMessage);
        } catch (Exception e) {
            log.error("Failed to send message to chat {}: {}", chatId, e.getMessage());
        }
    }

    @Override
    public long creatorId() {
        return creatorId;
    }
}