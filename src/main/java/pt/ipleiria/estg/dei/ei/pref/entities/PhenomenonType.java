package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "phenomenon_types"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllPhenomenonTypes",
                query = "SELECT p FROM PhenomenonType p ORDER BY p.id" // JPQL
        )})
public class PhenomenonType implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @ManyToMany(mappedBy = "phenomenonTypes")
    private List<SimplePackage> simplePackages;

    @OneToMany(mappedBy = "phenomenonType")
    private List<Observation> observations;


    public PhenomenonType() {
        this.simplePackages = new LinkedList<>();
        this.observations = new LinkedList<>();
    }

    public PhenomenonType(int id, String name) {
        this.id = id;
        this.name = name;
        this.simplePackages = new LinkedList<>();
        this.observations = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SimplePackage> getSimplePackages() {
        return simplePackages;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSimplePackages(List<SimplePackage> simplePackages) {
        this.simplePackages = simplePackages;
    }

    public void addSimplePackage(SimplePackage simplePackage) {
        if (!simplePackages.contains(simplePackage)) {
            simplePackages.add(simplePackage);
        }
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void addObservation(Observation observation) {
        this.observations.add(observation);
    }
}
