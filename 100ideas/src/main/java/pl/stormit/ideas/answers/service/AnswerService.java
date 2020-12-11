package pl.stormit.ideas.answers.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.answers.service.validator.AnswerValidator;
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
    private final AnswerValidator answerValidator;

    public AnswerService(
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            AnswerValidator answerValidator) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.answerValidator = answerValidator;
    }

    @Transactional
    public Answer addAnswer(Answer answer) {
        answerValidator.validateForAdding(answer);
        if (answer.getCreationDate() == null) {
            answer.setCreationDate(OffsetDateTime.now());
        }
        return answerRepository.save(answer);
    }


    @Transactional
    public Answer updateAnswer(Answer answerToUpdate) {
        answerValidator.validateForUpdating(answerToUpdate);
        Answer answer = getAnswerById(answerToUpdate.getId());
        checkIfAnswerIsNotTooOld(answer, "The Answer is too old to be updated");
        answer.setBody(answerToUpdate.getBody());
        return answer;
    }

    @Transactional
    public void deleteAnswer(Answer answerToDelete) {
        answerValidator.validateForDeleting(answerToDelete);
        Answer answer = getAnswerById(answerToDelete.getId());
        checkIfAnswerIsNotTooOld(answer, "The Answer is too old to be deleted");
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
        return answerRepository.findAllByQuestionIdOrderByCreationDateDesc(question.getId());
    }

    private void checkIfAnswerIsNotTooOld(Answer answer, String message) {
        OffsetDateTime creationDate = answer.getCreationDate();
        long secondsSinceCreation = Duration.between(creationDate, OffsetDateTime.now()).getSeconds();
        if (secondsSinceCreation > TIME_IN_SECONDS_SINCE_CREATION_THAT_ALLOWS_AN_UPDATE) {
            throw new IllegalArgumentException(message);
        }
    }
}
