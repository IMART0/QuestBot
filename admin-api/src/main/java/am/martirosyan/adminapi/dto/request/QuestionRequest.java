package am.martirosyan.adminapi.dto.request;

import java.util.List;

public record QuestionRequest(
        String text,
        String imageUrl,
        List<AnswerRequest> answers
) {
}
