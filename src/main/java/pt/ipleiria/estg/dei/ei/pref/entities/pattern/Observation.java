package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
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
        ),
        @NamedQuery(
                name = "getAllPackageObservations",
                query = "SELECT o FROM Observation o JOIN o.simplePackage sp WHERE sp.id = :simplePackageId"
        )})
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Observation implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private PhenomenonType phenomenonType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "observer_id")
    private Observer observer;

    @NotNull
    private String date;

    private String details;

    @ManyToOne
    @JoinColumn(name = "observable_package_id")
    private ObservablePackage observablePackage;

    public Observation() {

    }

    public Observation(PhenomenonType phenomenonType, Observer observer, String date, String details, ObservablePackage observablePackage) {
        this();
        this.phenomenonType = phenomenonType;
        this.observer = observer;
        this.date = date;
        this.details = details;
        this.observablePackage = observablePackage;
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

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ObservablePackage getObservablePackage() {
        return observablePackage;
    }

    public void setObservablePackage(ObservablePackage observablePackage) {
        this.observablePackage = observablePackage;
    }
}
