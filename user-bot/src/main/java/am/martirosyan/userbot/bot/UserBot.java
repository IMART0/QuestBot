package am.martirosyan.userbot.bot;

import am.martirosyan.userbot.handler.ResponseHandler;
import am.martirosyan.userbot.repository.QuestionRepository;
import am.martirosyan.userbot.repository.UserRepository;
import am.martirosyan.userbot.util.MessageSender;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserBot extends AbilityBot {

    private final long CREATOR_ID;
    private final ResponseHandler responseHandler;

    public UserBot(String username, String token, long creatorId,
                   UserRepository userRepository, QuestionRepository questionRepository) {
        super(token, username);
        this.CREATOR_ID = creatorId;

        MessageSender messageSender = new MessageSender(this);

        this.responseHandler = new ResponseHandler(
                userRepository,
                questionRepository,
                messageSender
        );
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }


    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        long chatId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        if (data.equals("START_GAME")) {
            responseHandler.replyToStartGameCallback(chatId, callbackQuery.getFrom().getFirstName());
        } else if (data.startsWith("ANSWER_")) {

            String[] parts = data.split("_");
            boolean isCorrect = Boolean.parseBoolean(parts[1]);

            responseHandler.handleAnswer(chatId, callbackQuery.getFrom().getFirstName(), isCorrect);
        }

    }

    private void handleMessage(Message message) {
        if (message.getText().equals("/start")) {
            long chatId = message.getChatId();
            String name = message.getFrom().getFirstName();

            responseHandler.replyToStart(chatId, name);
        }
    }

    @Override
    public long creatorId() {
        return CREATOR_ID;
    }
}
