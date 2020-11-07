package pl.stormit.ideas.categories.domain;

import pl.stormit.ideas.questions.domain.Question;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private UUID parent;
    @ManyToOne
    private List<Question> questions;


    public Category() {
    }

    public Category(UUID id, String name, UUID parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

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

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parent) {
        this.parent = parent;
    }


}
