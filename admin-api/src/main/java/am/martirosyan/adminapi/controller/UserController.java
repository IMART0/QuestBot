package am.martirosyan.adminapi.controller;

import am.martirosyan.adminapi.dto.request.UserRequest;
import am.martirosyan.adminapi.dto.response.UserResponse;
import am.martirosyan.adminapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        userService.update(id, userRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }
}
