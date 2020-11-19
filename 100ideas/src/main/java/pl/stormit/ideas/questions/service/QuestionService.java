package pl.stormit.ideas.questions.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;
import pl.stormit.ideas.validation.ValidationManager;
import pl.stormit.ideas.validation.ValidationResult;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    public static final long SECONDS_FOR_AN_UPDATE = 60L;
    private final QuestionRepository questionRepository;
    private final ValidationManager validationManager;

    public QuestionService(QuestionRepository questionRepository, ValidationManager validationManager) {
        this.questionRepository = questionRepository;
        this.validationManager = validationManager;
    }


    @Transactional
    public Question addQuestion(Question question) {
        ValidationResult validationResult = validationManager.validate(question);
        if (question.getId() != null) {
            throw new IllegalStateException("The Question cannot contain an ID");
        }
        if (!validationResult.isValid()) {
            throw new IllegalStateException(validationResult.getError().stream()
                    .collect(Collectors.joining(",", "Qustion is not valid because of: ", " .")));
        }
        if (questionRepository.countByName(question.getName()) > 0) {
            throw new IllegalStateException("The Question with this body: " + question.getName() + " exist in DB ");
        }
        if (question.getCreationDate() == null) {
            question.setCreationDate(OffsetDateTime.now());
        }
        return questionRepository.save(question);
    }


    @Transactional
    public Question updateQuestion(Question questionToUpdate) {
        ValidationResult validationResult = validationManager.validate(questionToUpdate);
        UUID questionToUpdatedId = questionToUpdate.getId();
        if (questionToUpdatedId == null) {
            throw new IllegalStateException("The Question must contain an ID");
        }
        Question question = questionRepository.findById(questionToUpdatedId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionToUpdatedId + " does not exist in DB")
                );
        if (!validationResult.isValid()) {
            throw new IllegalStateException(validationResult.getError().stream()
                    .collect(Collectors.joining(",", "Qustion is not valid because of: ", " .")));
        }
        OffsetDateTime creationDate = question.getCreationDate();
        long secondsSinceCreation = Duration.between(creationDate, OffsetDateTime.now()).getSeconds();
        if (secondsSinceCreation > SECONDS_FOR_AN_UPDATE) {
            throw new IllegalStateException("The Question is too old to be updated");
        }
        question.setName(questionToUpdate.getName());
        return question;
    }

    @Transactional
    public void deleteQuestion(Question questionToDelete) {
        UUID questionToDeleteId = questionToDelete.getId();
        if (questionToDeleteId == null) {
            throw new IllegalStateException("The Question to delete must contain an ID");
        }
        Question question = questionRepository.findById(questionToDeleteId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionToDeleteId + " does not exist in DB")
                );
        OffsetDateTime creationDate = question.getCreationDate();
        long secondsSinceCreation = Duration.between(creationDate, OffsetDateTime.now()).getSeconds();
        if (secondsSinceCreation > SECONDS_FOR_AN_UPDATE) {
            throw new IllegalStateException("The Question is too old to be deleted");
        }
        questionRepository.delete(question);
    }

    public Question getQuestionById(UUID QuestionId) {
        return questionRepository.findById(QuestionId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + QuestionId + " does not exist in DB")
                );
    }

    public List<Question> getAllQuestionsByQuestionId(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionId + " does not exist in DB")
                );
        return questionRepository.findAllById(question.getId());
    }
}