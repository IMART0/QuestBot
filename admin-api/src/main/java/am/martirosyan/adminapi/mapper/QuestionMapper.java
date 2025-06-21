package am.martirosyan.adminapi.mapper;

import am.martirosyan.adminapi.dto.request.QuestionRequest;
import am.martirosyan.adminapi.dto.response.QuestionResponse;
import am.martirosyan.adminapi.model.Answer;
import am.martirosyan.adminapi.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionMapper implements Mapper<Question, QuestionRequest, QuestionResponse> {

    private final AnswerMapper answerMapper;

    @Override
    public Question toEntity(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setId(null);
        question.setText(questionRequest.text());
        question.setImageUrl(questionRequest.imageUrl());

        Set<Answer> answers = questionRequest.answers().stream()
                .map(answerMapper::toEntity)
                .peek(answer -> answer.setQuestion(question))
                .collect(Collectors.toSet());

        question.setAnswers(answers);

        return question;
    }


    @Override
    public QuestionResponse toResponse(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getText(),
                question.getImageUrl(),
                question.getAnswers().stream()
                        .map(answerMapper::toResponse)
                        .collect(Collectors.toSet())
        );
    }
}
