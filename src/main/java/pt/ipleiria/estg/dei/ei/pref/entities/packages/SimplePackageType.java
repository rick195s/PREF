package pt.ipleiria.estg.dei.ei.pref.entities.packages;

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
    private String id;

    private double cost;

    @NotNull
    @Column(name = "is_sustainable")
    private boolean isSustainable;

    @NotNull
    @Column(name = "is_smart")
    private boolean isSmart;

    @OneToMany(mappedBy = "simplePackageType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ObservablePackage> observablePackages;

    public SimplePackageType() {
        observablePackages = new LinkedList<>();
    }

    public SimplePackageType(String id, double cost, boolean isSustainable, boolean isSmart) {
        this();
        this.id = id;
        this.cost = cost;
        this.isSustainable = isSustainable;
        this.isSmart = isSmart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<ObservablePackage> getObservablePackages() {
        return observablePackages;
    }

    public void setObservablePackages(List<ObservablePackage> observablePackages) {
        this.observablePackages = observablePackages;
    }
}
