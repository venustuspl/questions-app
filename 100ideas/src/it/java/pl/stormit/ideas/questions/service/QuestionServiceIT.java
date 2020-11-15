package pl.stormit.ideas.questions.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class QuestionServiceIT {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void shouldSaveQuestionInDB() {
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

    @BeforeEach
    @AfterEach
    void clear() {
        questionRepository.deleteAll();
    }

    private Question getQuestionToSave() {
        Question question = questionRepository.save(new Question());
        question.setCreationDate(OffsetDateTime.now());
        return question;
    }
}

