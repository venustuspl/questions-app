package pl.stormit.ideas.answers.domain.dto;

public class AnswerAddRequest {
    private String body;
    private String questionId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
