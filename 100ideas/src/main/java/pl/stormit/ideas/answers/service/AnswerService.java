package pl.stormit.ideas.answers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(AnswerService.class);
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Answer addAnswer(Answer answer) {
        if (answer.getId() != null) {
            logger.error("The Answer to add cannot contain an ID");
            throw new IllegalArgumentException();
        }
        if (answer.getQuestion() == null) {
            logger.error("The Answer to add must contain the Question object");
            throw new IllegalArgumentException();
        }
        UUID questionId = answer.getQuestion().getId();
        if (questionId == null) {
            logger.error("The Answer to add must contain the Question object with ID");
            throw new IllegalArgumentException();
        }
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    logger.error("The Question object with id {} does not exist in DB", questionId);
                    return new NoSuchElementException();
                });
        answer.setQuestion(question);
        if (answer.getCreationDate() == null) {
            answer.setCreationDate(OffsetDateTime.now());
        }
        return answerRepository.save(answer);
    }

}
