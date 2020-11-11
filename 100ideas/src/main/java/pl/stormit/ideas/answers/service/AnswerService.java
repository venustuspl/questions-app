package pl.stormit.ideas.answers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Answer addAnswer(Answer answer) {
        if (answer.getId() != null) {
            throw new IllegalArgumentException("The Answer to add cannot contain an ID");
        }
        if (answer.getQuestion() == null) {
            throw new IllegalArgumentException("The Answer to add must contain the Question object");
        }
        UUID questionId = answer.getQuestion().getId();
        if (questionId == null) {
            throw new IllegalArgumentException("The Answer to add must contain the Question object with ID");
        }
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionId + " does not exist in DB")
                );
        answer.setQuestion(question);
        if (answer.getCreationDate() == null) {
            answer.setCreationDate(OffsetDateTime.now());
        }
        return answerRepository.save(answer);
    }

}
