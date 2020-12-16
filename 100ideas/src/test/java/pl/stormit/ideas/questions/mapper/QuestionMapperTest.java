package pl.stormit.ideas.questions.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.service.QuestionService;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.mockito.Mockito.mock;

class QuestionMapperTest {
    protected static final UUID ID = UUID.randomUUID();
    protected static final String ID_IN_STRING = ID.toString();
    protected static final OffsetDateTime DATE = OffsetDateTime.of(2020, 12, 11, 13, 52, 54, 0, ZoneOffset.UTC);
    protected static final String DATE_IN_STRING = "11.12.2020 13:52";
    protected static final String NAME = "QUESTION NAME";
    protected static final String UPDATED_NAME = "updated name";
    protected QuestionService questionService = mock(QuestionService.class);
    protected CategoryService categoryService = mock(CategoryService.class);
    QuestionMapper questionMapper;

    @BeforeEach
    void setup() {
        questionMapper = new QuestionMapper(questionService, categoryService);
    }

    @Test
    void mapQuestionToQuestionResponse() {
        //given
        Question question = getQuestion();

        //when


        //then

    }

    protected Question getQuestion() {
        Question question = new Question();
        question.setId(ID);
        question.setName(NAME);
        question.setCreationDate(DATE);

        return question;
    }


}