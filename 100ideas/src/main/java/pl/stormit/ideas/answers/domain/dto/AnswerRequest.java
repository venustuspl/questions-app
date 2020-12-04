package pl.stormit.ideas.answers.domain.dto;

import java.util.UUID;

public class AnswerRequest {
    private String body;
    private UUID questionId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }
}
