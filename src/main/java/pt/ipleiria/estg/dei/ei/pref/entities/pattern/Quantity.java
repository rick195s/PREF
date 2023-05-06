package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private double value;
    @OneToMany(mappedBy = "quantity")
    private List<MeasurementObservation> measurementObservations;

    public Quantity() {
        measurementObservations = new LinkedList<>();
    }
    public Quantity(double value) {
        this.value = value;
        measurementObservations = new LinkedList<>();
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

    public List<MeasurementObservation> getMeasurements() {
        return measurementObservations;
    }

    public void addMeasurement(MeasurementObservation measurementObservation) {
        measurementObservations.add(measurementObservation);
    }
}
