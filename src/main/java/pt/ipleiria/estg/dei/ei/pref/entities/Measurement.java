package pt.ipleiria.estg.dei.ei.pref.entities;

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
    private double measure;

    public Measurement() {
    }
    public Measurement(double measure) {
        super();
        this.measure = measure;
    }

    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }
}
