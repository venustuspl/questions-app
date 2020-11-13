package pl.stormit.ideas.questions.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.questions.domain.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class QuestionServiceIT {

    @Autowired
    private QuestionService questionService;

    @Test
    void shouldSaveQuestionInDB() {
        //given
        Question questionToSave = new Question();

        questionToSave.setName("Test name");

        //when
        Question savedQuestion = questionService.addQuestion(questionToSave);

        //then
        assertEquals("Test body", savedQuestion.getName());
        assertNotNull(savedQuestion.getId());
    }
}

