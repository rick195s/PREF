package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "observable_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservablePackages",
                query = "SELECT o FROM ObservablePackage o ORDER BY o.id" // JPQL
        )})
public class ObservablePackage implements Serializable {

    @OneToMany(mappedBy = "observablePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Observation> observations;

    public ObservablePackage() {
        this.observations = new LinkedList<>();

    }

}
