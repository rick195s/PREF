package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import javax.persistence.*;
import java.io.Serializable;

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

    public ObservablePackage() {

    }

}
