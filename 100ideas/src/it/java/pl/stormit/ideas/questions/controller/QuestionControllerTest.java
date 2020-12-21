package pl.stormit.ideas.questions.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.domain.QuestionUpdatedRequest;
import pl.stormit.ideas.questions.mapper.QuestionMapper;
import pl.stormit.ideas.questions.service.QuestionService;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {
    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    public static final UUID ID = UUID.randomUUID();
    public static final String ID_IN_STRING = ID.toString();

    public static List<QuestionResponse> getQuestionResponseList() {
        return new ArrayList<>();
    }

    public static List<Category> getCategories() {
        return new ArrayList<>();
    }

    @Test
    void shouldReturnMainQuestionView() throws Exception {
        //given
        when(questionMapper.mapQuestionsToQuestionResponseList(questionService.getAllQuestions())).thenReturn(getQuestionResponseList());
        when(categoryService.getAllCategories()).thenReturn(getCategories());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/questions");

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("question/questions"))
                .andExpect(model().attribute("questions", emptyCollectionOf(QuestionResponse.class)))
                .andExpect(model().attribute("categories", emptyCollectionOf(Category.class)))
                .andExpect(model().attribute("offsetDateTime", Matchers.any(OffsetDateTime.class)));
    }

    @Test
    void shouldReturnQuestionAddView() throws Exception {
        //given
        when(categoryService.getAllCategories()).thenReturn(getCategories());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/questions/add");

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("question/questionadd"))
                .andExpect(model().attribute("categories", emptyCollectionOf(Category.class)));
    }

    @Test
    void shouldReturnSingleQuestionView() throws Exception {
        //given
        when(questionMapper.mapQuestionToQuestionResponse(questionService.getQuestionById(ID))).thenReturn(new QuestionResponse());
        when(categoryService.getAllCategories()).thenReturn(getCategories());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/questions/" + ID_IN_STRING);

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("question/questionupdate"))
                .andExpect(model().attribute("question", Matchers.any(QuestionResponse.class)))
                .andExpect(model().attribute("questionToUpdate", Matchers.any(QuestionUpdatedRequest.class)))
                .andExpect(model().attribute("categories", emptyCollectionOf(Category.class)))
                .andExpect(model().attribute("exception", false))
                .andExpect(model().attribute("exceptionEdit", false));
    }
}