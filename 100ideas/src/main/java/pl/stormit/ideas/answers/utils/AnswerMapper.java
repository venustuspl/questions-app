package pl.stormit.ideas.answers.utils;

import org.springframework.stereotype.Component;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerAddRequest;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;
import pl.stormit.ideas.answers.domain.dto.AnswerUpdatedRequest;
import pl.stormit.ideas.answers.service.AnswerService;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.service.QuestionService;

import java.time.format.DateTimeFormatter;
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
        answerResponse.setBody(answer.getBody());
        answerResponse.setId(answer.getId().toString());

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String creationDate = answer.getCreationDate().toLocalDateTime().format(dateFormat);
        answerResponse.setCreationDate(creationDate);

        return answerResponse;
    }

    public List<AnswerResponse> mapToAnswerResponseList(List<Answer> answers) {
        return answers.stream()
                .map(this::mapToAnswerResponse)
                .collect(Collectors.toList());
    }

    public Answer mapToAnswer(AnswerAddRequest answerAddRequest) {
        UUID questionId = UUID.fromString(answerAddRequest.getQuestionId());
        Question question = questionService.getQuestionById(questionId);
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setBody(answerAddRequest.getBody());
        return answer;
    }

    public Answer mapToAnswer(AnswerUpdatedRequest answerUpdatedRequest) {
        UUID answerId = UUID.fromString(answerUpdatedRequest.getId());
        Answer answer = answerService.getAnswerById(answerId);

        answer.setBody(answerUpdatedRequest.getBody());

        return answer;
    }
}
