package pl.stormit.ideas.answers.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.BODY;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.DATE_IN_STRING;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.ID_IN_STRING;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.getAnswerToUpdate;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.getQuestion;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.getAnswer;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.getAnswerAddRequest;
import static pl.stormit.ideas.answers.AnswerSubdomainBaseTest.getAnswerUpdatedRequest;
import static pl.stormit.ideas.answers.assertions.AnswerAssertions.assertThat;

@SpringBootTest
class AnswerFacadeIT {

    @Autowired
    private AnswerFacade answerFacade;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private AnswerRepository answerRepository;

    @Test
    void shouldReturnQuestionResponse() {
        //given
        when(questionRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getQuestion()));

        //when
        QuestionResponse question = answerFacade.getQuestionById(ID_IN_STRING);

        //then
        assertThat(question.getName()).isEqualTo(BODY);
        assertThat(question.getId()).isEqualTo(ID_IN_STRING);
    }

    @Test
    void shouldReturnAnswerResponseList() {
        //given
        when(questionRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getQuestion()));
        when(answerRepository.findAllByQuestionIdOrderByCreationDateDesc(any(UUID.class)))
                .thenReturn(Collections.singletonList(getAnswer()));

        //when
        List<AnswerResponse> answers = answerFacade.getAllAnswersByQuestionId(ID_IN_STRING);

        //then
        AnswerResponse answerResponse = answers.get(0);
        assertThat(answerResponse)
                .hasId(ID_IN_STRING)
                .hasBody(BODY)
                .hasDate(DATE_IN_STRING);
    }

    @Test
    void shouldAddAnswer() {
        //given
        when(questionRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getQuestion()));

        //when
        answerFacade.addAnswer(getAnswerAddRequest());

        //then
        verify(answerRepository, times(1)).save(any(Answer.class));
    }

    @Test
    void shouldUpdateAnswer() {
        //given
        when(answerRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getAnswerToUpdate()));

        //when
        answerFacade.updateAnswer(getAnswerUpdatedRequest());

        //then
        verify(answerRepository, times(1)).save(any(Answer.class));

    }

    @Test
    void shouldReturnQuestionIdInStringWhenYouPassAnswerUpdateRequest() {
        //given
        when(answerRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getAnswer()));

        //when
        String questionId = answerFacade.getQuestionId(getAnswerUpdatedRequest());

        //then
        assertThat(questionId).isEqualTo(ID_IN_STRING);
    }

    @Test
    void shouldReturnQuestionIdInStringWhenYouPassAnswerId() {
        //given
        when(answerRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getAnswer()));

        //when
        String questionId = answerFacade.getQuestionId(ID_IN_STRING);

        //then
        assertThat(questionId).isEqualTo(ID_IN_STRING);
    }

    @Test
    void shouldDeleteAnswer() {
        //given
        when(answerRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(getAnswerToUpdate()));

        //when
        answerFacade.deleteAnswer(ID_IN_STRING);

        //then
        verify(answerRepository, times(1)).delete(any(Answer.class));
    }
}