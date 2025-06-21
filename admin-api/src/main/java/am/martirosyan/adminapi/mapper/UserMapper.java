package am.martirosyan.adminapi.mapper;

import am.martirosyan.adminapi.dto.request.UserRequest;
import am.martirosyan.adminapi.dto.response.UserResponse;
import am.martirosyan.adminapi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements  Mapper<User, UserRequest, UserResponse> {

    @Override
    public User toEntity(UserRequest request) {
        return User.builder()
                .chatId(request.chatId())
                .completedQuiz(request.completedQuiz())
                .points(request.points())
                .build();
    }

    @Override
    public UserResponse toResponse(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getChatId(),
                entity.isCompletedQuiz(),
                entity.getPoints()
        );
    }
}
