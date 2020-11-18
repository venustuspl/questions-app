package pl.stormit.ideas.answers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AnswerServiceIT {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void shouldSaveAnswerInDB() {
        //given
        Answer answerToSave = getAnswerToSave();

        //when
        Answer savedAnswer = answerService.addAnswer(answerToSave);

        //then
        assertThat(savedAnswer.getBody()).isEqualTo("Test body");
        assertThat(savedAnswer.getId()).isNotNull();
    }

    @Test
    void shouldUpdateAnswerInDB() {
        //given
        Answer savedAnswer = answerRepository.save(getAnswerToSave());
        savedAnswer.setBody("Updated body");

        //when
        answerService.updateAnswer(savedAnswer);

        //then
        Answer updatedAnswer = answerRepository.findById(savedAnswer.getId()).orElseThrow();
        assertThat(updatedAnswer.getBody()).isEqualTo("Updated body");
        assertThat(updatedAnswer.getId()).isEqualTo(savedAnswer.getId());
    }

    @Test
    void shouldDeleteAnswerFromDB() {
        //given
        Answer savedAnswer = answerRepository.save(getAnswerToSave());
        List<Answer> savedAnswers = (List<Answer>) answerRepository.findAll();
        assertThat(savedAnswers.size()).isOne();

        //when
        answerService.deleteAnswer(savedAnswer);

        //then
        savedAnswers = (List<Answer>) answerRepository.findAll();
        assertThat(savedAnswers.size()).isZero();
        List<Question> savedQuestions = (List<Question>) questionRepository.findAll();
        assertThat(savedQuestions.size()).isOne();
    }

    @Test
    void shouldGetAnswerById() {
        //given
        Answer savedAnswer = answerRepository.save(getAnswerToSave());
        List<Answer> savedAnswers = (List<Answer>) answerRepository.findAll();
        assertThat(savedAnswers.size()).isOne();

        //when
        Answer answer = answerService.getAnswerById(savedAnswer.getId());

        //then
        assertThat(answer.getBody()).isEqualTo("Test body");
        assertThat(answer.getId()).isEqualTo(savedAnswer.getId());
    }

    @Test
    void shouldAnswersByQuestionId() {
        //given
        List<Answer> answersToSave = getAnswersToSave();
        answerRepository.saveAll(answersToSave);
        Answer answerToSave = getAnswerToSave();
        answerRepository.save(answerToSave);
        List<Answer> savedAnswers = (List<Answer>) answerRepository.findAll();
        assertThat(savedAnswers.size()).isEqualTo(4);
        UUID questionId = answersToSave.get(0).getQuestion().getId();

        //when
        List<Answer> answers = answerService.getAllAnswersByQuestionId(questionId);

        //then
        assertThat(answers.size()).isEqualTo(3);
    }

    @BeforeEach
    @AfterEach
    void clear() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
    }

    private Answer getAnswerToSave() {
        Question question = questionRepository.save(new Question());
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setBody("Test body");
        answer.setCreationDate(OffsetDateTime.now());
        return answer;
    }

    private List<Answer> getAnswersToSave() {
        Question question = questionRepository.save(new Question());
        List<Answer> answers = new ArrayList<>();
        Answer answerOne = new Answer();
        answerOne.setQuestion(question);
        answers.add(answerOne);
        Answer answerTwo = new Answer();
        answerTwo.setQuestion(question);
        answers.add(answerTwo);
        Answer answerThree = new Answer();
        answerThree.setQuestion(question);
        answers.add(answerThree);
        return answers;
    }
}