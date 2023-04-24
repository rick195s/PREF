package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "authors"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllAuthors",
                query = "SELECT a FROM Author a ORDER BY a.id" // JPQL
        )})
public class Author implements Serializable {

    @Id
    @NotNull
    private long id;
    @NotNull
    private String type;

    @OneToMany(mappedBy = "author")
    private List<Observation> observations;

    public Author() {
        observations = new LinkedList<>();
    }

    public Author(long id, String type) {
        this.id = id;
        this.type = type;
        observations = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void addObservation(Observation observation) {
        this.observations.add(observation);
    }
}
