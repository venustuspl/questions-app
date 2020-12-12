package pl.stormit.ideas.answers.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.answers.AnswerSubdomainBaseTest;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerAddRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pl.stormit.ideas.answers.assertions.AnswerAssertions.assertThat;

class AnswerMapperTest extends AnswerSubdomainBaseTest {
    private AnswerMapper answerMapper;

    @BeforeEach
    void setup() {
        answerMapper = new AnswerMapper(answerService, questionService);
    }

    @Test
    void shouldMapAnswerToAnswerResponse() {
        //given
        Answer answer = getAnswer();

        //when
        AnswerResponse answerResponse = answerMapper.mapToAnswerResponse(answer);

        //then
        assertThat(answerResponse)
                .hasBody(BODY)
                .hasId(ID_IN_STRING)
                .hasDate(DATE_IN_STRING);
    }

    @Test
    void shouldMapAnswerAddRequestToAnswer() {
        //given
        when(questionService.getQuestionById(any(UUID.class))).thenReturn(getQuestion());
        AnswerAddRequest answerAddRequest = getAnswerAddRequest();

        //when
        Answer answer = answerMapper.mapToAnswer(answerAddRequest);

        //then
        assertThat(answer)
                .hasBody(BODY)
                .hasQuestion()
                .hasNoId()
                .hasNoDate();
    }

    @Test
    void shouldMapAnswerUpdateRequestToAnswer() {
        //given
        when(answerService.getAnswerById(any(UUID.class))).thenReturn(getAnswer());
        AnswerUpdatedRequest answerUpdatedRequest = getAnswerUpdatedRequest();

        //when
        Answer answer = answerMapper.mapToAnswer(answerUpdatedRequest);

        //then
        assertThat(answer)
                .hasBody(UPDATED_BODY)
                .hasQuestion()
                .hasId(ID)
                .hasDate(DATE);
    }
}