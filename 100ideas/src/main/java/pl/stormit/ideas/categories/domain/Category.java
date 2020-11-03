package pl.stormit.ideas.categories.domain;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue
    @OneToMany(mappedBy = "id")
    private UUID id;
    private String name;
    private UUID parent;


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
