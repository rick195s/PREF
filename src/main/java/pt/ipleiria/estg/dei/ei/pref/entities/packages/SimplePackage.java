package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.PackageLog;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "simple_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllSimplePackages",
                query = "SELECT s FROM SimplePackage s ORDER BY s.id" // JPQL
        )})
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class SimplePackage implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "simplePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PackageLog> packageLogs;

    public SimplePackage() {
        packageLogs = new LinkedList<>();
    }

    public SimplePackage( String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
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

    public List<PackageLog> getPackageLogs() {
        return packageLogs;
    }

    public void addPackageLog(PackageLog packageLog) {
        this.packageLogs.add(packageLog);
    }
}
