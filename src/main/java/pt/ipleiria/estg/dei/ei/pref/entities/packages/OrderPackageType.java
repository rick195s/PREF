package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "order_package_types"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderPackageTypes",
                query = "SELECT o FROM OrderPackageType o ORDER BY o.id" // JPQL
        )})
public class OrderPackageType extends SimplePackage implements Serializable {
    public OrderPackageType() {

    }

    public OrderPackageType(String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        super(name, cost, dimension, isSustainable, resistance, isSmart);
    }

}
