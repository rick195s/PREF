package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "observers"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservers",
                query = "SELECT o FROM Observer o ORDER BY o.id" // JPQL
        )})
public class Observer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String type;

    @OneToMany(mappedBy = "observer")
    private List<Observation> observations;

    public Observer() {
        observations = new LinkedList<>();
    }

    public Observer(String type) {
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
