package pl.stormit.ideas.questions.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class QuestionServiceIT {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    @AfterEach
    void clear() {
        questionRepository.deleteAll();
    }

    private Question getQuestionToSave() {
        Question question = questionRepository.save(new Question());
        question.setName("Test name");
        question.setCreationDate(OffsetDateTime.now());
        return question;
    }

    @Test
    void shouldAddQuestionIntoDB() {
        //given
        Question questionToSave = new Question();

        questionToSave.setName("Test name");

        //when
        Question savedQuestion = questionService.addQuestion(questionToSave);

        //then
        assertEquals("Test name", savedQuestion.getName());
        assertNotNull(savedQuestion.getId());
    }

    @Test
    void shouldUpdateQuestionInDB() {
        //given
        Question savedQuestion = questionRepository.save(getQuestionToSave());
        savedQuestion.setName("Updated name");

        //when
        questionService.updateQuestion(savedQuestion);

        //then
        Question updatedQuestion = questionRepository.findById(savedQuestion.getId()).orElseThrow();
        assertThat(updatedQuestion.getName()).isEqualTo("Updated name");
        assertThat(updatedQuestion.getId()).isEqualTo(savedQuestion.getId());
    }

    @Test
    void shouldDeleteQuestionFromDB() {
        //given
        Question savedQuestion = questionRepository.save(getQuestionToSave());
        List<Question> savedQuestions = (List<Question>) questionRepository.findAll();
        assertThat(savedQuestions.size()).isOne();

        //when
        questionService.deleteQuestion(savedQuestion);

        //then
        assertThat(((List<Question>) questionRepository.findAll()).size()).isZero();
    }

    @Test
    void shouldGetQuestionById() {
        //given
        Question savedQuestion = questionRepository.save(getQuestionToSave());
        List<Question> savedQuestions = (List<Question>) questionRepository.findAll();
        assertThat(savedQuestions.size()).isOne();

        //when
        Question question = questionService.getQuestionById(savedQuestion.getId());

        //then
        assertThat(question.getName()).isEqualTo("Test name");
        assertThat(question.getId()).isEqualTo(savedQuestion.getId());
    }

}

