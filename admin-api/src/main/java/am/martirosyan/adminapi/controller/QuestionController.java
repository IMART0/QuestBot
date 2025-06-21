package am.martirosyan.adminapi.controller;

import am.martirosyan.adminapi.dto.request.QuestionRequest;
import am.martirosyan.adminapi.dto.response.QuestionResponse;
import am.martirosyan.adminapi.mapper.QuestionMapper;
import am.martirosyan.adminapi.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping()
    public List<QuestionResponse> getQuestion() {
        return questionService.findAll().stream().map(questionMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public QuestionResponse getQuestionById(@PathVariable Long id) {
        return questionMapper.toResponse(questionService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteQuestionById(@PathVariable Long id) {
        questionService.deleteById(id);
    }

    @PostMapping()
    public QuestionResponse createQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionMapper.toResponse(questionService.save(questionRequest));
    }

    @PutMapping("/{id}")
    public QuestionResponse updateQuestion(@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {
        return questionMapper.toResponse(questionService.update(id, questionRequest));
    }
}
