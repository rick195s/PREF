package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(
        name = "measurements"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllMeasurements",
                query = "SELECT m FROM Measurement m ORDER BY m.id" // JPQL
        )})
public class Measurement extends Observation implements Serializable {
    private Quantity quantity;

    public Measurement() {
    }
    public Measurement(Quantity quantity) {
        super();
        this.quantity = quantity;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
