package pl.stormit.ideas.answers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class AnswerService {
    private static final long TIME_IN_SECONDS_SINCE_CREATION_THAT_ALLOWS_AN_UPDATE = 60L;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerStateValidator answerStateValidator;

    public AnswerService(
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            AnswerStateValidator answerStateValidator) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.answerStateValidator = answerStateValidator;
    }

    @Transactional
    public Answer addAnswer(Answer answer) {
        answerStateValidator.validateForAdding(answer);
        if (answer.getCreationDate() == null) {
            answer.setCreationDate(OffsetDateTime.now());
        }
        return answerRepository.save(answer);
    }


    @Transactional
    public Answer updateAnswer(Answer answerToUpdate) {
        answerStateValidator.validateForUpdating(answerToUpdate);
        Answer answer = answerRepository.findById(answerToUpdate.getId())
                .orElseThrow(() ->
                        new NoSuchElementException("The Answer object with id " + answerToUpdate.getId() + " does not exist in DB")
                );
        OffsetDateTime creationDate = answer.getCreationDate();
        long secondsSinceCreation = Duration.between(creationDate, OffsetDateTime.now()).getSeconds();
        if (secondsSinceCreation > TIME_IN_SECONDS_SINCE_CREATION_THAT_ALLOWS_AN_UPDATE) {
            throw new IllegalStateException("The Answer is too old to be updated");
        }
        answer.setBody(answerToUpdate.getBody());
        return answer;
    }

    @Transactional
    public void deleteAnswer(Answer answerToDelete) {
        answerStateValidator.validateForDeleting(answerToDelete);
        Answer answer = answerRepository.findById(answerToDelete.getId())
                .orElseThrow(() ->
                        new NoSuchElementException("The Answer object with id " + answerToDelete.getId() + " does not exist in DB")
                );
        OffsetDateTime creationDate = answer.getCreationDate();
        long secondsSinceCreation = Duration.between(creationDate, OffsetDateTime.now()).getSeconds();
        if (secondsSinceCreation > TIME_IN_SECONDS_SINCE_CREATION_THAT_ALLOWS_AN_UPDATE) {
            throw new IllegalStateException("The Answer is too old to be deleted");
        }
        answerRepository.delete(answer);
    }

    public Answer getAnswerById(UUID answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Answer object with id " + answerId + " does not exist in DB")
                );
    }

    public List<Answer> getAllAnswersByQuestionId(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionId + " does not exist in DB")
                );
        return answerRepository.findAllByQuestionId(question.getId());
    }
}
