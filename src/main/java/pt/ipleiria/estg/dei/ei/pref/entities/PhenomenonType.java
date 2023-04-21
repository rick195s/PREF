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
    private int id;
    @NotNull
    private String name;
    @ManyToMany(mappedBy = "phenomenonTypes")
    private List<SimplePackage> simplePackages;

    public PhenomenonType() {
    }

    public PhenomenonType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SimplePackage> getSimplePackages() {
        return simplePackages;
    }

    public void setId(int id) {
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
}
