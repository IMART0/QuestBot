package am.martirosyan.adminbot.service;

import am.martirosyan.adminbot.model.Answer;
import am.martirosyan.adminbot.model.Question;
import am.martirosyan.adminbot.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public void saveQuestionFromString(String input) {
        String[] lines = input.split("\\r?\\n");

        if (lines.length < 3) {
            throw new IllegalArgumentException("Неверный формат. Должно быть минимум 3 строки: вопрос, картинка, ответы.");
        }

        String questionText = lines[0].trim();
        String imageUrl = lines[1].trim();
        if (imageUrl.isEmpty()) {
            imageUrl = null;  // если пустая строка — считаем, что картинки нет
        }

        Question question = Question.builder()
                .text(questionText)
                .imageUrl(imageUrl)
                .build();

        Set<Answer> answers = new HashSet<>();

        for (int i = 2; i < lines.length; i++) {
            String ans = lines[i].trim();
            if (ans.isEmpty()) continue;

            boolean isCorrect = ans.endsWith("*");
            String answerText = isCorrect ? ans.substring(0, ans.length() - 1).trim() : ans;

            Answer answer = Answer.builder()
                    .text(answerText)
                    .isCorrect(isCorrect)
                    .question(question)
                    .build();

            answers.add(answer);
        }

        if (answers.isEmpty()) {
            throw new IllegalArgumentException("Должен быть хотя бы один ответ.");
        }

        question.setAnswers(answers);

        questionRepository.save(question);
    }

}

