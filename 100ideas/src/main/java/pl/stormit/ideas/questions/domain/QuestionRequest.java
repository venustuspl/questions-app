package pl.stormit.ideas.questions.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public class QuestionRequest {
    private UUID questionId;
    private String name;
    private UUID categoryId;
    private OffsetDateTime creationDate;

    public String getName() {
        return name;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
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
}
