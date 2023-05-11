package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "observable_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservablePackages",
                query = "SELECT o FROM ObservablePackage o ORDER BY o.id" // JPQL
        )})
public class ObservablePackage<PackageType extends SimplePackageType> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "observablePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Observation> observations;

    @ManyToOne(targetEntity = SimplePackageType.class)
    @JoinColumn(name = "simple_package_type_id")
    private PackageType simplePackageType;

    public ObservablePackage() {
        this.observations = new LinkedList<>();
    }

    public ObservablePackage(PackageType simplePackageType) {
        this();
        this.simplePackageType = simplePackageType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public PackageType getSimplePackageType() {
        return simplePackageType;
    }

    public void setSimplePackageType(PackageType simplePackageType) {
        this.simplePackageType = simplePackageType;
    }
}
