package am.martirosyan.userbot.util;

import am.martirosyan.userbot.bot.UserBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
public class MessageSender {
    private final UserBot bot;

    public void executeMessage(SendMessage message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    public void executeMessage(SendPhoto message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error sending photo: {}", e.getMessage());
        }
    }

    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup keyboardMarkup) {
        executeMessage(
                SendMessage.builder()
                        .chatId(String.valueOf(chatId))
                        .text(text)
                        .parseMode("Markdown")
                        .replyMarkup(keyboardMarkup)
                        .build()
        );
    }

    public void sendMessage(Long chatId, String text) {
        executeMessage(
                SendMessage.builder()
                        .chatId(String.valueOf(chatId))
                        .text(text)
                        .parseMode("Markdown")
                        .build()
        );
    }

    public void sendQuestion(long chatId, String imageUrl, String text, InlineKeyboardMarkup answerKeyboard) {
        SendPhoto message = SendPhoto.builder()
                .chatId(String.valueOf(chatId))
                .photo(new InputFile(imageUrl))
                .caption(text)
                .parseMode("Markdown")
                .replyMarkup(answerKeyboard)
                .build();

        executeMessage(message);
    }

    public void sendQuestion(long chatId, String imageUrl, String text, InlineKeyboardMarkup answerKeyboard, boolean isCorrect) {
        String head = isCorrect ? "✅ Верно\n" : "❌ Неверно\n";
        SendPhoto message = SendPhoto.builder()
                .chatId(String.valueOf(chatId))
                .photo(new InputFile(imageUrl))
                .caption(head + text)
                .parseMode("Markdown")
                .replyMarkup(answerKeyboard)
                .build();

        executeMessage(message);
    }
}
