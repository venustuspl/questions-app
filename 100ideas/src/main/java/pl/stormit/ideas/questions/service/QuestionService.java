package pl.stormit.ideas.questions.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;
import pl.stormit.ideas.validation.ValidationManager;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class QuestionService {
    public static final long SECONDS_FOR_AN_UPDATE = 60L;
    private final QuestionRepository questionRepository;
    private final ValidationManager validationManager;
    private final QuestionStateValidator questionStateValidator;

    public QuestionService(QuestionRepository questionRepository, ValidationManager validationManager, QuestionStateValidator questionStateValidator) {
        this.questionRepository = questionRepository;
        this.validationManager = validationManager;
        this.questionStateValidator = questionStateValidator;
    }

    @Transactional
    public Question addQuestion(Question question) {
        questionStateValidator.validateForAdding(question);

        if (question.getCreationDate() == null) {
            question.setCreationDate(OffsetDateTime.now());
        }

        return questionRepository.save(question);
    }


    @Transactional
    public Question updateQuestion(Question questionToUpdate) {
        UUID questionToUpdatedId = questionToUpdate.getId();

        questionStateValidator.validateForUpdating(questionToUpdate);

        Question question = questionRepository.findById(questionToUpdatedId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionToUpdatedId + " does not exist in DB")
                );

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

        questionStateValidator.validateForDeleting(questionToDelete);

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

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Question getQuestionById(UUID questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Question object with id " + questionId + " does not exist in DB")
                );
    }

}