package pl.stormit.ideas.answers.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.answers.domain.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class AnswerServiceIT {

    @Autowired
    private AnswerService answerService;

    @Test
    void shouldSaveAnswerInDB() {
        //given
        Answer answerToSave = new Answer();
        answerToSave.setBody("Test body");

        //when
        Answer savedAnswer = answerService.addAnswer(answerToSave);

        //then
        assertEquals("Test body", savedAnswer.getBody());
        assertNotNull(savedAnswer.getId());
    }
}