package pl.stormit.ideas.questions.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Iterable<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(UUID id) {
        return questionRepository.findById(id);
    }

    public List<Question> saveQuestionList(List<Question> QuestionList) {
        return QuestionList.stream()
                .map(d -> questionRepository.save(d))
                .collect(Collectors.toList());

    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);

    }

    public void deleteAllQuestion() {
        questionRepository.deleteAll();

    }
}