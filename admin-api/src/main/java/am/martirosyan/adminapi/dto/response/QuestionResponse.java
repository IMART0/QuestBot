package am.martirosyan.adminapi.dto.response;

import java.util.Set;

public record QuestionResponse(
        Long id,
        String text,
        String imageUrl,
        Set<AnswerResponse> answers
) {
}
