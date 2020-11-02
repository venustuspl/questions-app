package pl.stormit.ideas.categories.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Category {

    @Id
    @GeneratedValue

    private uuid id;
    private String name;
    private uuid parent;

    @ManyToOne (mappedBy ="id");

    public Category(uuid id, String name, uuid parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public uuid getId() {
        return id;
    }

    public void setId(uuid id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public uuid getParent() {
        return parent;
    }

    public void setParent(uuid parent) {
        this.parent = parent;
    }


}
