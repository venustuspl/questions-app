package pl.stormit.ideas.questions.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Question {

    @Id
    @GeneratedValue
    @OneToMany(mappedBy = "?")
    private UUID id;
    private String body;
    private Integer categoryId;
    private OffsetDateTime creationDate;

    public Question() {
    }

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) &&
                Objects.equals(body, question.body) &&
                Objects.equals(categoryId, question.categoryId) &&
                Objects.equals(creationDate, question.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, categoryId, creationDate);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", categoryId=" + categoryId +
                ", creationDate=" + creationDate +
                '}';
    }
}
