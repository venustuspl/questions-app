package pl.stormit.ideas.questions.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.domain.QuestionRequest;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.domain.QuestionUpdatedRequest;
import pl.stormit.ideas.questions.service.QuestionService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionMapperTest {
    protected static final UUID ID = UUID.randomUUID();
    protected static final String ID_IN_STRING = ID.toString();
    protected static final UUID CATEGORY_ID = UUID.randomUUID();
    protected static final String CATEGORY_ID_IN_STRING = CATEGORY_ID.toString();
    protected static final OffsetDateTime DATE = OffsetDateTime.of(2020, 12, 11, 13, 52, 54, 0, ZoneOffset.UTC);
    protected static final String DATE_IN_STRING = "2020-12-11T13:52:54Z";
    protected static final String NAME = "QUESTION NAME";
    protected static final String CATEGORY_NAME = "CATEGORY NAME";
    protected static final String UPDATED_NAME = "updated name";
    protected QuestionService questionService = mock(QuestionService.class);
    protected CategoryService categoryService = mock(CategoryService.class);
    private QuestionMapper questionMapper;

    @BeforeEach
    void setup() {
        questionMapper = new QuestionMapper(questionService, categoryService);
    }

    @Test
    void mapQuestionToQuestionResponse() {
        //given
        when(categoryService.getCategoryById(any(UUID.class))).thenReturn(getCategory());
        Question question = getQuestion();

        //when
        QuestionResponse questionResponse = questionMapper.mapQuestionToQuestionResponse(question);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(ID.toString(), questionResponse.getId()),
                () -> Assertions.assertEquals(NAME, questionResponse.getName()),
                () -> Assertions.assertEquals(DATE_IN_STRING, questionResponse.getCreationDate()),
                () -> Assertions.assertNotNull(questionResponse.getCategoryName()));
    }

    @Test
    void mapQuestionRequestToQuestion() {
        //given
        when(categoryService.getCategoryById(any())).thenReturn(getCategory());
        QuestionRequest questionRequest = getQuestionRequest();

        //when
        Question question = questionMapper.mapQuestionRequestToQuestion(questionRequest);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(NAME, question.getName()),
                () -> Assertions.assertEquals(DATE_IN_STRING, question.getCreationDate().toString()),
                () -> Assertions.assertNotNull(question.getCategory()));
    }

    @Test
    void mapQuestionUpdatedRequestToQuestion() {
        //given
        when(questionService.getQuestionById(any())).thenReturn(getQuestion());
        when(categoryService.getCategoryById(any())).thenReturn(getCategory());
        QuestionUpdatedRequest questionUpdateRequest = getQuestionUpdateRequest();

        //when
        Question question = questionMapper.mapQuestionUpdatedRequestToQuestion(questionUpdateRequest);

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(UPDATED_NAME, question.getName()),
                () -> Assertions.assertEquals(DATE_IN_STRING, question.getCreationDate().toString()),
                () -> Assertions.assertNotNull(question.getCategory()));
    }


    protected Question getQuestion() {
        Question question = new Question();
        question.setId(ID);
        question.setName(NAME);
        question.setCreationDate(DATE);
        question.setCategory(getCategory());

        return question;
    }

    protected QuestionRequest getQuestionRequest() {
        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setName(NAME);
        questionRequest.setCreationDate(DATE);
        questionRequest.setCategoryId(CATEGORY_ID_IN_STRING);

        return questionRequest;
    }

    protected QuestionUpdatedRequest getQuestionUpdateRequest() {
        QuestionUpdatedRequest questionUpdatedRequest = new QuestionUpdatedRequest();
        questionUpdatedRequest.setId(ID_IN_STRING);
        questionUpdatedRequest.setName(UPDATED_NAME);
        questionUpdatedRequest.setCategoryId(CATEGORY_ID_IN_STRING);

        return questionUpdatedRequest;
    }

    protected Category getCategory() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);

        return category;
    }
}