package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(
        name = "observations"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservations",
                query = "SELECT o FROM Observation o ORDER BY o.id" // JPQL
        )})
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Observation implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private PhenomenonType phenomenonType;

    public Observation() {
    }

    public Observation(long id, PhenomenonType phenomenonType) {
        this();
        this.id = id;
        this.phenomenonType = phenomenonType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PhenomenonType getPhenomenonType() {
        return phenomenonType;
    }

    public void setPhenomenonType(PhenomenonType phenomenonType) {
        this.phenomenonType = phenomenonType;
    }
}
