package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

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
    private Observer observer;

    @NotNull
    private String date;

    @ManyToOne
    @JoinColumn(name = "simple_package_id")
    private SimplePackage simplePackage;

    public Observation() {

    }

    public Observation(PhenomenonType phenomenonType, Observer observer, String date, SimplePackage simplePackage) {
        this();
        this.phenomenonType = phenomenonType;
        this.observer = observer;
        this.date = date;
        this.simplePackage = simplePackage;
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

    public SimplePackage getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackage simplePackage) {
        this.simplePackage = simplePackage;
    }
}
