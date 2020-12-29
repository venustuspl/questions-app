package pl.stormit.ideas.answers.controller;

import org.springframework.stereotype.Component;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerAddRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.answers.utils.AnswerMapper;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.domain.QuestionResponse;
import pl.stormit.ideas.questions.mapper.QuestionMapper;
import pl.stormit.ideas.questions.service.QuestionService;

import java.util.List;
import java.util.UUID;

@Component
public class AnswerFacade {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final AnswerMapper answerMapper;
    private final QuestionMapper questionMapper;

    public AnswerFacade(
            AnswerService answerService,
            QuestionService questionService,
            AnswerMapper answerMapper,
            QuestionMapper questionMapper) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.answerMapper = answerMapper;
        this.questionMapper = questionMapper;
    }

    public QuestionResponse getQuestionById(String id) {
        Question question = questionService.getQuestionById(UUID.fromString(id));
        return questionMapper.mapQuestionToQuestionResponse(question);
    }

    public List<AnswerResponse> getAllAnswersByQuestionId(String id) {
        List<Answer> answers = answerService.getAllAnswersByQuestionId(UUID.fromString(id));
        return answerMapper.mapToAnswerResponseList(answers);
    }

    public void addAnswer(AnswerAddRequest answerAddRequest) {
        Answer answer = answerMapper.mapToAnswer(answerAddRequest);
        answerService.addAnswer(answer);
    }

    public void updateAnswer(AnswerUpdatedRequest answerUpdatedRequest) {
        Answer answer = answerMapper.mapToAnswer(answerUpdatedRequest);
        answerService.updateAnswer(answer);
    }

    public String getQuestionId(AnswerUpdatedRequest answerUpdatedRequest) {
        Answer answer = answerMapper.mapToAnswer(answerUpdatedRequest);
        UUID questionId = answer.getQuestion().getId();
        return questionId.toString();
    }

    public String getQuestionId(String answerId) {
        Answer answer = getAnswerById(answerId);
        UUID questionId = answer.getQuestion().getId();
        return questionId.toString();
    }

    public void deleteAnswer(String answerId) {
        Answer answer = getAnswerById(answerId);
        answerService.deleteAnswer(answer);
    }

    private Answer getAnswerById(String answerId) {
        UUID id = UUID.fromString(answerId);
        return answerService.getAnswerById(id);
    }
}
