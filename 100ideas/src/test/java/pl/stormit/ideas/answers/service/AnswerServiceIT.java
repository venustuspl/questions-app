package pl.stormit.ideas.answers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
        assertEquals("Test body", savedAnswer.getBody());
        assertNotNull(savedAnswer.getId());
    }

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
        return answer;
    }
}