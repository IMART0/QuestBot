package am.martirosyan.adminapi.service;

import am.martirosyan.adminapi.dto.request.QuestionRequest;
import am.martirosyan.adminapi.mapper.QuestionMapper;
import am.martirosyan.adminapi.model.Question;
import am.martirosyan.adminapi.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public Question save(QuestionRequest questionRequest) {
        return questionRepository.save(questionMapper.toEntity(questionRequest));
    }

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void deleteById(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }

    public Question update(Long id, QuestionRequest questionRequest) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found with id: " + id);
        }
        Question question = questionMapper.toEntity(questionRequest);
        question.setId(id);
        return questionRepository.save(question);
    }
}
