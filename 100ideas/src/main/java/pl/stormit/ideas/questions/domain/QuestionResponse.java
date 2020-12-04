package pl.stormit.ideas.questions.domain;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class QuestionResponse {
    private UUID id;
    private String name;
    private UUID categoryId;
    private OffsetDateTime creationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
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
