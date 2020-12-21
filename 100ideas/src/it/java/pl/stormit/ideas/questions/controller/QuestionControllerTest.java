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
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.categories.domain.Category;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.mapper.QuestionMapper;
import pl.stormit.ideas.questions.service.QuestionService;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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
                .andExpect(model().attribute("categories", emptyCollectionOf(AnswerResponse.class)))
                .andExpect(model().attribute("offsetDateTime", Matchers.any(OffsetDateTime.class)));

    }
}