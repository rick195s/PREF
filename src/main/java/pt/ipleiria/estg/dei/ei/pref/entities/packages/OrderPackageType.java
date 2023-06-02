package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderPackageTypes",
                query = "SELECT o FROM OrderPackageType o ORDER BY o.id" // JPQL
        ),
        @NamedQuery(
                name = "getAllOrderPackageTypesWithId",
                query = "SELECT o FROM OrderPackageType o WHERE o.id IN :ids ORDER BY o.id" // JPQL
        )
})

public class OrderPackageType extends SimplePackageType implements Serializable {
    public OrderPackageType() {

    }

    public OrderPackageType(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
        super(id, cost, isSustainable, isSmart, shape, width, height, length);
    }
}
