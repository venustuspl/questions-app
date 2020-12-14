package pl.stormit.ideas.answers.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.stormit.ideas.answers.AnswerSubdomainBaseTest;
import pl.stormit.ideas.answers.domain.dto.AnswerAddRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;
import pl.stormit.ideas.questions.domain.QuestionResponse;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AnswerController.class)
class AnswerControllerIT extends AnswerSubdomainBaseTest {
    @MockBean
    private AnswerFacade answerFacade;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnMainAnswerView() throws Exception {
        //given
        when(answerFacade.getQuestionById(ID_IN_STRING)).thenReturn(new QuestionResponse());
        when(answerFacade.getAllAnswersByQuestionId(ID_IN_STRING)).thenReturn(getAnswerResponseList());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/answers/" + ID_IN_STRING);

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().isOk())
                .andExpect(view().name("answer/answers"))
                .andExpect(model().attribute("question", Matchers.any(QuestionResponse.class)))
                .andExpect(model().attribute("answers", emptyCollectionOf(AnswerResponse.class)))
                .andExpect(model().attribute("answerToAdd", Matchers.any(AnswerAddRequest.class)))
                .andExpect(model().attribute("answerToUpdate", Matchers.any(AnswerUpdatedRequest.class)))
                .andExpect(model().attribute("exception", false))
                .andExpect(model().attribute("exceptionEdit", false));
    }

    @Test
    void shouldReturnMainViewAfterAddingNewAnswer() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/answers")
                .param("body", BODY)
                .param("questionId", ID_IN_STRING);

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/answers/" + ID_IN_STRING))
                .andExpect(flash().attributeCount(0));
    }

    @Test
    void shouldReturnMainViewWithAttributesWhenAddingNewAnswerFailed() throws Exception {
        //given
        doThrow(new RuntimeException("Test message")).when(answerFacade).addAnswer(any(AnswerAddRequest.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/answers")
                .param("body", BODY)
                .param("questionId", ID_IN_STRING);

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/answers/" + ID_IN_STRING))
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attribute("exception", true))
                .andExpect(flash().attribute("message", "Test message"));
    }

    @Test
    void shouldReturnMainViewAfterUpdatingAnswer() throws Exception {
        //given
        when(answerFacade.getQuestionId(any(AnswerUpdatedRequest.class))).thenReturn(ID_IN_STRING);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/answers/update")
                .param("body", BODY)
                .param("id", ID_IN_STRING);

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/answers/" + ID_IN_STRING))
                .andExpect(flash().attributeCount(0));
    }

    @Test
    void shouldReturnMainViewWithAttributesWhenUpdatingAnswerFailed() throws Exception {
        //given
        when(answerFacade.getQuestionId(any(AnswerUpdatedRequest.class))).thenReturn(ID_IN_STRING);
        doThrow(new RuntimeException("Test message")).when(answerFacade).updateAnswer(any(AnswerUpdatedRequest.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/answers/update")
                .param("body", BODY)
                .param("id", ID_IN_STRING);

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/answers/" + ID_IN_STRING))
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attribute("exceptionEdit", true))
                .andExpect(flash().attribute("message", "Test message"));
    }

    @Test
    void shouldReturnMainViewAfterDeletingAnswer() throws Exception {
        //given
        when(answerFacade.getQuestionId(ID_IN_STRING)).thenReturn(ID_IN_STRING);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/answers/" + ID_IN_STRING + "/delete");

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/answers/" + ID_IN_STRING))
                .andExpect(flash().attributeCount(0));
    }

    @Test
    void shouldReturnMainViewWithAttributesWhenDeletingAnswerFailed() throws Exception {
        //given
        when(answerFacade.getQuestionId(ID_IN_STRING)).thenReturn(ID_IN_STRING);
        doThrow(new RuntimeException("Test message")).when(answerFacade).deleteAnswer(ID_IN_STRING);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/answers/" + ID_IN_STRING + "/delete");

        //when
        ResultActions result = mockMvc.perform(request);

        //then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/answers/" + ID_IN_STRING))
                .andExpect(flash().attributeCount(2))
                .andExpect(flash().attribute("exceptionEdit", true))
                .andExpect(flash().attribute("message", "Test message"));
    }
}