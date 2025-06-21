package am.martirosyan.adminapi.service;

import am.martirosyan.adminapi.dto.request.UserRequest;
import am.martirosyan.adminapi.dto.response.UserResponse;
import am.martirosyan.adminapi.mapper.UserMapper;
import am.martirosyan.adminapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public void update(Long id, UserRequest request) {
        var user = userMapper.toEntity(request);
        user.setId(id);
        userRepository.save(user);
    }
}
