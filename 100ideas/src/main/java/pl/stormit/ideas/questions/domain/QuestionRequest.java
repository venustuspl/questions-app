package pl.stormit.ideas.questions.domain;

import java.time.OffsetDateTime;

public class QuestionRequest {
    private String questionId;
    private String name;
    private String categoryId;
    private OffsetDateTime creationDate;

    public String getName() {
        return name;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
