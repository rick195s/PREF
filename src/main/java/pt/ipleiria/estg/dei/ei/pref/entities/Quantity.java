package pt.ipleiria.estg.dei.ei.pref.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "quantities"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllQuantities",
                query = "SELECT q FROM Quantity q ORDER BY q.id" // JPQL
        )})
public class Quantity implements Serializable {
    @Id
    @NotNull
    private long id;
    @NotNull
    private double value;
    @OneToMany(mappedBy = "quantity")
    private List<Measurement> measurements;

    public Quantity() {
        measurements = new LinkedList<>();
    }
    public Quantity(long id, double value) {
        this.id = id;
        this.value = value;
        measurements = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
    }
}
