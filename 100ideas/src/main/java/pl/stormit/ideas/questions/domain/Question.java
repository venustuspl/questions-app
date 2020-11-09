package pl.stormit.ideas.questions.domain;

import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.categories.domain.Category;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Questions")
public class Question {

    @Id
    @GeneratedValue
    private UUID id;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Category category;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
