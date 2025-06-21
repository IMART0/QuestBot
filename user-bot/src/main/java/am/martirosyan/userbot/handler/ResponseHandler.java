package am.martirosyan.userbot.handler;

import am.martirosyan.userbot.model.Question;
import am.martirosyan.userbot.model.User;
import am.martirosyan.userbot.repository.QuestionRepository;
import am.martirosyan.userbot.repository.UserRepository;
import am.martirosyan.userbot.util.KeyboardFactory;
import am.martirosyan.userbot.util.BotMessage;
import am.martirosyan.userbot.util.MessageSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final MessageSender messageSender;

    private final Map<Long, Map<Question, Boolean>> userAnswers;

    public ResponseHandler(UserRepository userRepository, QuestionRepository questionRepository,
                           MessageSender messageSender) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.messageSender = messageSender;
        this.userAnswers = new HashMap<>();
    }

    public void replyToStart(Long chatId, String name) {
        if (!userRepository.existsByChatId(chatId)) {
            userRepository.save(
                    User.builder()
                            .chatId(chatId)
                            .completedQuiz(false)
                            .build()
            );
            messageSender.sendMessage(chatId, BotMessage.MESSAGE_FOR_NEW_USER(name), KeyboardFactory.getStartKeyboard());
        }
        else {
            if (!userRepository.findByChatId(chatId).isCompletedQuiz()) {
                messageSender.sendMessage(
                        chatId, BotMessage.MESSAGE_FOR_OLD_USER(name),
                        KeyboardFactory.getStartKeyboardForOldUser()
                );
            } else {
                User user = userRepository.findByChatId(chatId);
                messageSender.sendMessage(chatId, BotMessage.MESSAGE_FOR_COMPLETED_QUIZ(name, user.getPoints()));
            }
        }
    }

    public void replyToStartGameCallback(long chatId, String name) {
        Question question = getRandomQuestion(chatId);
        if (question != null) {
            sendQuestion(chatId, question , true, false);
        } else {
            User user = userRepository.findByChatId(chatId);
            messageSender.sendMessage(chatId, BotMessage.MESSAGE_FOR_COMPLETED_QUIZ(name, user.getPoints()));
            user.setCompletedQuiz(true);
            userRepository.save(user);
            userAnswers.remove(chatId);
        }
    }

    public void handleAnswer(long chatId, String name, boolean isCorrect) {
        Question question = getRandomQuestion(chatId);

        if (isCorrect) {
            User user = userRepository.findByChatId(chatId);
            user.setPoints(user.getPoints() + 1);
            userRepository.save(user);
        }

        if (question == null) {
            User user = userRepository.findByChatId(chatId);
            messageSender.sendMessage(chatId, BotMessage.MESSAGE_FOR_COMPLETED_QUIZ(name, user.getPoints()));
            return;
        }

        sendQuestion(chatId, question, false, isCorrect);
    }

    private void sendQuestion(long chatId, Question question, boolean isFirst, boolean isCorrect) {
        if (isFirst) {
            messageSender.sendQuestion(
                    chatId,
                    question.getImageUrl(),
                    question.getText(),
                    KeyboardFactory.getAnswerKeyboard(question.getAnswers())
            );
        }
        else {
            messageSender.sendQuestion(
                    chatId,
                    question.getImageUrl(),
                    question.getText(),
                    KeyboardFactory.getAnswerKeyboard(question.getAnswers()),
                    isCorrect
            );
        }
    }

    private Question getRandomQuestion(long chatId) {
        if (!userAnswers.containsKey(chatId)) {
            userAnswers.put(chatId, generateRandomQuestions());
        }
        Map<Question, Boolean> questions = userAnswers.get(chatId);
        for (Map.Entry<Question, Boolean> entry : questions.entrySet()) {
            if (!entry.getValue()) {
                entry.setValue(true);
                return entry.getKey();
            }
        }
        return null;
    }

    private Map<Question, Boolean> generateRandomQuestions() {
        List<Question> questions = questionRepository.findRandomQuestions();
        Map<Question, Boolean> questionMap = new HashMap<>();
        for (Question question : questions) {
            questionMap.put(question, false);
        }
        return questionMap;
    }
}
