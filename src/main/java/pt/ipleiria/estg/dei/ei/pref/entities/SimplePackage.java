package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageType;
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
public class SimplePackage implements Serializable {
    @Id
    @NotNull
    private long id;
    @NotNull
    private double cost;
    @NotNull
    private String dimension;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PackageMaterialType.class, fetch = FetchType.EAGER)
    private List<PackageMaterialType> materialsType;
    @NotNull
    private PackageType type;
    @NotNull
    private PackageCategory category;
    @NotNull
    private boolean is_sustainable;
    @NotNull
    private ResistenceType resistance;
    @NotNull
    private boolean is_smart;

    @OneToMany(mappedBy = "simplePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLine;

    @OneToMany(mappedBy = "simplePackage", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PackageLog> packageLogs;

    public SimplePackage() {
        materialsType = new LinkedList<>();
    }

    public SimplePackage(long id, double cost, String dimension, List<PackageMaterialType> materialsType, PackageType type, PackageCategory category, boolean is_sustainable, ResistenceType resistance, boolean is_smart) {
        this.id = id;
        this.cost = cost;
        this.dimension = dimension;
        this.materialsType = new LinkedList<>();
        this.materialsType.addAll(materialsType);
        this.type = type;
        this.category = category;
        this.is_sustainable = is_sustainable;
        this.resistance = resistance;
        this.is_smart = is_smart;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public PackageCategory getCategory() {
        return category;
    }

    public void setCategory(PackageCategory category) {
        this.category = category;
    }

    public boolean is_sustainable() {
        return is_sustainable;
    }

    public void set_is_sustainable(boolean is_sustainable) {
        this.is_sustainable = is_sustainable;
    }

    public ResistenceType getResistance() {
        return resistance;
    }

    public void setResistance(ResistenceType resistance) {
        this.resistance = resistance;
    }

    public boolean is_smart() {
        return is_smart;
    }

    public void set_is_smart(boolean is_smart) {
        this.is_smart = is_smart;
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
