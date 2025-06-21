package am.martirosyan.adminapi.dto.response;

public record AnswerResponse(
        Long id,
        String text,
        boolean isCorrect
) {
}
