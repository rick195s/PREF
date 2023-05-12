package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllMeasurementObservation",
                query = "SELECT m FROM MeasurementObservation m ORDER BY m.id" // JPQL
        )})
public class MeasurementObservation extends Observation implements Serializable {
    @ManyToOne
    @JoinColumn(name = "quantity_id")
    private Quantity quantity;

    public MeasurementObservation() {
    }
    public MeasurementObservation(PhenomenonType phenomenonType, Observer observer, String date, String details, ObservablePackage<SimplePackageType> observablePackage, Quantity quantity) {
        super(phenomenonType, observer, date, details, observablePackage);
        this.quantity = quantity;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
