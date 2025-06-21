package am.martirosyan.adminapi.dto.response;

public record UserResponse(
        Long id,
        Long chatId,
        Boolean completedQuiz,
        Integer points
) {
}


