package am.martirosyan.userbot.util;

public class BotMessage {
    public static String MESSAGE_FOR_NEW_USER(String name) {
        return """
                Привет, %s!
                Я игровой бот Национального Музея Республики Татарстан. Хочешь поиграть в квест-игру по нашему музею?
                """.formatted(name);
    }

    public static String MESSAGE_FOR_OLD_USER(String name) {
        return """
                Привет, %s!
                Я игровой бот Национального Музея Республики Татарстан. Продолжим квест-игру по нашему музею?
                """.formatted(name);
    }

    public static String MESSAGE_FOR_COMPLETED_QUIZ(String name, int points) {
        return """
                Поздравляю, %s!
                Ты прошел квест-игру по нашему музею.
                Ты набрал %d баллов.
                Спасибо за участие!
                """.formatted(name, points);
    }
}
