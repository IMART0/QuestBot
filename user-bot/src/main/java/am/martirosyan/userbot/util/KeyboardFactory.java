package am.martirosyan.userbot.util;

import am.martirosyan.userbot.model.Answer;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Set;

public class KeyboardFactory {
    public static InlineKeyboardMarkup getStartKeyboard() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(
                List.of(
                        InlineKeyboardButton.builder()
                                .text("Начать игру")
                                .callbackData("START_GAME")
                                .build()
                )
        ));
        return keyboard;
    }

    public static InlineKeyboardMarkup getStartKeyboardForOldUser() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(List.of(
                List.of(
                        InlineKeyboardButton.builder()
                                .text("Продолжить игру")
                                .callbackData("START_GAME")
                                .build()
                )
        ));
        return keyboard;
    }

    public static InlineKeyboardMarkup getAnswerKeyboard(Set<Answer> answers) {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = answers.stream()
                .map(answer -> List.of(
                        InlineKeyboardButton.builder()
                                .text(answer.getText())
                                .callbackData("ANSWER_%s".formatted(answer.isCorrect()))
                                .build()
                ))
                .toList();
        keyboard.setKeyboard(rows);
        return keyboard;
    }
}
