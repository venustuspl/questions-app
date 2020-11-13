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

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Answer addAnswer(Answer answer) {
        if (answer.getId() != null) {
            throw new IllegalStateException("The Answer to add cannot contain an ID");
        }
        if (answer.getQuestion() == null) {
            throw new IllegalStateException("The Answer to add must contain the Question object");
        }
        UUID questionId = answer.getQuestion().getId();
        if (questionId == null) {
            throw new IllegalStateException("The Answer to add must contain the Question object with ID");
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


    @Transactional
    public Answer updateAnswer(Answer answerToUpdate) {
        UUID answerToUpdatedId = answerToUpdate.getId();
        if (answerToUpdatedId == null) {
            throw new IllegalStateException("The Answer to add must contain an ID");
        }
        Answer answer = answerRepository.findById(answerToUpdatedId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Answer object with id " + answerToUpdatedId + " does not exist in DB")
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
        UUID answerToDeleteId = answerToDelete.getId();
        if (answerToDeleteId == null) {
            throw new IllegalStateException("The Answer to add must contain an ID");
        }
        Answer answer = answerRepository.findById(answerToDeleteId)
                .orElseThrow(() ->
                        new NoSuchElementException("The Answer object with id " + answerToDeleteId + " does not exist in DB")
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
