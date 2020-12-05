package pl.stormit.ideas.questions.mapper;

import org.springframework.stereotype.Component;
import pl.stormit.ideas.categories.repository.CategoryRepository;
import pl.stormit.ideas.categories.service.CategoryService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.domain.QuestionRequest;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.domain.QuestionUpdatedRequest;
import pl.stormit.ideas.questions.service.QuestionService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    private final QuestionService questionService;
    private final CategoryService categoryService;

    public QuestionMapper(QuestionService questionService, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.questionService = questionService;
        this.categoryService = categoryService;
    }


    public QuestionResponse mapQuestionToQuestionResponse(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setName(question.getName());
        questionResponse.setId(question.getId().toString());
        return questionResponse;
    }

    public List<QuestionResponse> mapQuestionsToQuestionResponseList(List<Question> questions) {
        return questions.stream()
                .map(this::mapQuestionToQuestionResponse)
                .collect(Collectors.toList());
    }

    public Question mapQuestionRequestToQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setName(questionRequest.getName());
        question.setCategory(categoryService.getCategoryById(UUID.fromString(questionRequest.getCategoryId())));
        return question;
    }

    public Question mapQuestionUpdatedRequestToQuestion(QuestionUpdatedRequest questionUpdatedRequest) {
        Question question = questionService.getQuestionById(UUID.fromString(questionUpdatedRequest.getId()));
        question.setName(questionUpdatedRequest.getName());
        return question;
    }
}
