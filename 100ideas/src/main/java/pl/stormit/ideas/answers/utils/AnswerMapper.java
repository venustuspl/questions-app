package pl.stormit.ideas.answers.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.service.QuestionService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AnswerMapper {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerMapper(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    public  AnswerResponse mapToAnswerResponse(Answer answer) {
        AnswerResponse answerResponse = new AnswerResponse();
        BeanUtils.copyProperties(answer, answerResponse);
        answerResponse.setId(answer.getId().toString());
        return answerResponse;
    }

    public List<AnswerResponse> mapToAnswerResponseList(List<Answer> answers) {
        return answers.stream()
                .map(this::mapToAnswerResponse)
                .collect(Collectors.toList());
    }

    public Answer mapToAnswer(AnswerRequest answerRequest) {
        UUID questionId = UUID.fromString(answerRequest.getQuestionId());
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setBody(answerRequest.getBody());
        return answer;
    }

    public Answer mapToAnswer(AnswerUpdatedRequest answerUpdatedRequest) {
        Answer answer = answerService.getAnswerById(answerUpdatedRequest.getId());
        answer.setBody(answerUpdatedRequest.getBody());
        return answer;
    }
}
