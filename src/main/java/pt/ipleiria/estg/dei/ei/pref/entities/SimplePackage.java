package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageType;

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
public class SimplePackage implements Serializable {
    @Id
    @NotNull
    private long id;

    @NotNull
    private String dimension;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PackageMaterialType.class, fetch = FetchType.EAGER)
    private List<PackageMaterialType> materialsType;
    @NotNull
    private PackageType type;

    @OneToMany(mappedBy = "simplePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLine;

    @OneToMany(mappedBy = "simplePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PackageLog> packageLogs;

    public SimplePackage() {
        materialsType = new LinkedList<>();
    }

    public SimplePackage(long id, String dimension, List<PackageMaterialType> materialsType, PackageType type) {
        this.id = id;
        this.dimension = dimension;
        this.materialsType = new LinkedList<>();
        this.materialsType.addAll(materialsType);
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public List<PackageMaterialType> getMaterialsType() {
        return materialsType;
    }

    public void setMaterialsType(List<PackageMaterialType> materialsType) {
        this.materialsType = materialsType;
    }

    public PackageType getPackageType() {
        return type;
    }

    public void setPackageType(PackageType type) {
        this.type = type;
    }

    public void addMaterialType(PackageMaterialType materialType) {
        this.materialsType.add(materialType);
    }

    public void removeMaterialType(PackageMaterialType materialType) {
        this.materialsType.remove(materialType);
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public void addOrderLine(OrderLine orderLine) {
        this.orderLine.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        this.orderLine.remove(orderLine);
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public List<PackageLog> getPackageLogs() {
        return packageLogs;
    }

    public void addPackageLog(PackageLog packageLog) {
        this.packageLogs.add(packageLog);
    }
}
