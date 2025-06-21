package am.martirosyan.adminapi.dto.request;

public record AnswerRequest(
        String text,
        boolean isCorrect
) {}
