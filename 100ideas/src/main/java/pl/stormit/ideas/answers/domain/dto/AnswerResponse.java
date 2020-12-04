package pl.stormit.ideas.answers.domain.dto;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class AnswerResponse {
    private String id;
    private String body;
    private OffsetDateTime creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationTime() {
        return creationDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
}
