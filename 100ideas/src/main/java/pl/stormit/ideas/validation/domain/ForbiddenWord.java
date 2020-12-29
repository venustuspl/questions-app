package pl.stormit.ideas.validation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "forbidden_words")
public class ForbiddenWord {

    @Id
    @GeneratedValue
    private UUID id;
    private String word;

    public ForbiddenWord() {

    }

    public ForbiddenWord(String word) {
        this.word = word;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean containsForbiddenWord(String input) {
        return input.toLowerCase().contains(this.word);
    }
}