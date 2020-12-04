package pl.stormit.ideas.questions.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.domain.QuestionRequest;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.domain.QuestionUpdatedRequest;
import pl.stormit.ideas.questions.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    private final QuestionService questionService;
    private final CategoryService categoryService;

    public QuestionMapper(QuestionService questionService, CategoryService categoryService) {
        this.questionService = questionService;
        this.categoryService = categoryService;
    }


    public QuestionResponse mapToQuestionResponse(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        BeanUtils.copyProperties(question, questionResponse);
        return questionResponse;
    }

    public List<QuestionResponse> mapToQuestionResponseList(List<Question> questions) {
        return questions.stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
    }

    public Question mapToQuestion(QuestionRequest questionRequest) {
        Question question = questionService.getQuestionById(questionRequest.getQuestionId());
        question.setName(questionRequest.getName());
        return question;
    }

    public Question mapToQuestion(QuestionUpdatedRequest questionUpdatedRequest) {
        Question question = questionService.getQuestionById(questionUpdatedRequest.getId());
        question.setName(questionUpdatedRequest.getName());
        return question;
    }
}
