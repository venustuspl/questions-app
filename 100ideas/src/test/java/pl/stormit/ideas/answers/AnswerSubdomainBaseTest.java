package pl.stormit.ideas.answers;

import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerAddRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.service.QuestionService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

public abstract class AnswerSubdomainBaseTest {
    public static final UUID ID = UUID.randomUUID();
    public static final String ID_IN_STRING = ID.toString();
    public static final OffsetDateTime DATE = OffsetDateTime.of(2020, 12, 11, 13, 52, 54, 0, ZoneOffset.UTC);
    public static final String DATE_IN_STRING = "11.12.2020 13:52";
    public static final String BODY = "answer body";
    public static final String UPDATED_BODY = "updated body";

    protected AnswerService answerService = mock(AnswerService.class);
    protected QuestionService questionService = mock(QuestionService.class);

    public static Answer getAnswer() {
        Answer answer = new Answer();
        answer.setBody(BODY);
        answer.setId(ID);
        answer.setCreationDate(DATE);
        answer.setQuestion(getQuestion());
        return answer;
    }

    public static Answer getAnswerToUpdate() {
        Answer answer = getAnswer();
        answer.setCreationDate(OffsetDateTime.now());
        return answer;
    }

    public static Question getQuestion() {
        Question question = new Question();
        question.setId(ID);
        question.setName(BODY);
        question.setCreationDate(DATE);
        return question;
    }

    public static AnswerAddRequest getAnswerAddRequest() {
        AnswerAddRequest answerAddRequest = new AnswerAddRequest();
        answerAddRequest.setBody(BODY);
        answerAddRequest.setQuestionId(ID_IN_STRING);
        return answerAddRequest;
    }

    public static AnswerUpdatedRequest getAnswerUpdatedRequest() {
        AnswerUpdatedRequest answerUpdatedRequest = new AnswerUpdatedRequest();
        answerUpdatedRequest.setId(ID_IN_STRING);
        answerUpdatedRequest.setBody(UPDATED_BODY);
        return answerUpdatedRequest;
    }

    public static List<AnswerResponse> getAnswerResponseList() {
        return new ArrayList<>();
    }
}
