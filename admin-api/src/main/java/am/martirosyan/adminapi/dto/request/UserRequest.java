package am.martirosyan.adminapi.dto.request;

public record UserRequest(
        Long chatId,
        Boolean completedQuiz,
        Integer points
) {
}
