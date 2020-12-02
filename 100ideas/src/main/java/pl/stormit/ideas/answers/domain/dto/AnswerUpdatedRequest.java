package pl.stormit.ideas.answers.domain.dto;

import java.util.UUID;

public class AnswerUpdatedRequest {
    private UUID id;
    private String body;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
