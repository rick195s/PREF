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

    private String shape;

    private float width;
    private float height;
    private float length;

    public SimplePackageType() {
        observablePackages = new LinkedList<>();
    }

    public SimplePackageType(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
        this();
        this.id = id;
        this.cost = cost;
        this.isSustainable = isSustainable;
        this.isSmart = isSmart;
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.length = length;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
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
