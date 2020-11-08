package pl.stormit.ideas.answers.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer addAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

}
