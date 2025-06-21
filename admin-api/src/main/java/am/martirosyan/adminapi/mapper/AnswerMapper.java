package am.martirosyan.adminapi.mapper;

import am.martirosyan.adminapi.dto.request.AnswerRequest;
import am.martirosyan.adminapi.dto.response.AnswerResponse;
import am.martirosyan.adminapi.model.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper implements Mapper<Answer, AnswerRequest, AnswerResponse> {
    @Override
    public Answer toEntity(AnswerRequest answerRequest) {
        return Answer.builder()
                .id(null)
                .text(answerRequest.text())
                .isCorrect(answerRequest.isCorrect())
                .build();
    }


    @Override
    public AnswerResponse toResponse(Answer answer) {
        return new AnswerResponse(
                answer.getId(),
                answer.getText(),
                answer.isCorrect()
        );
    }
}
