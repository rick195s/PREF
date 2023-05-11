package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "simple_package_types"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllSimplePackageTypes",
                query = "SELECT s FROM SimplePackageType s ORDER BY s.id" // JPQL
        )})
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class SimplePackageType implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private double cost;
    @NotNull
    private String dimension;

    @NotNull
    @Column(name = "is_sustainable")
    private boolean isSustainable;

    @NotNull
    private ResistenceType resistance;

    @NotNull
    @Column(name = "is_smart")
    private boolean isSmart;

    @OneToMany(mappedBy = "simplePackageType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ObservablePackage> observablePackages;

    public SimplePackageType() {
        observablePackages = new LinkedList<>();
    }

    public SimplePackageType(String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        this();
        this.name = name;
        this.cost = cost;
        this.dimension = dimension;
        this.isSustainable = isSustainable;
        this.resistance = resistance;
        this.isSmart = isSmart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSustainable() {
        return isSustainable;
    }

    public void setSustainable(boolean sustainable) {
        isSustainable = sustainable;
    }

    public boolean isSmart() {
        return isSmart;
    }

    public void setSmart(boolean smart) {
        isSmart = smart;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ResistenceType getResistance() {
        return resistance;
    }

    public void setResistance(ResistenceType resistance) {
        this.resistance = resistance;
    }

    public List<ObservablePackage> getObservablePackages() {
        return observablePackages;
    }

    public void setObservablePackages(List<ObservablePackage> observablePackages) {
        this.observablePackages = observablePackages;
    }
}
