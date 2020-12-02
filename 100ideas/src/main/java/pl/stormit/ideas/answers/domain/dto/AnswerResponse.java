package pl.stormit.ideas.answers.domain.dto;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AnswerResponse implements Comparable<AnswerResponse>{
    private UUID id;
    private String body;
    private OffsetDateTime creationDate;

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

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationTime() {
        return creationDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Override
    public int compareTo(AnswerResponse answerResponse) {
        return answerResponse.getCreationDate().compareTo(creationDate);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
