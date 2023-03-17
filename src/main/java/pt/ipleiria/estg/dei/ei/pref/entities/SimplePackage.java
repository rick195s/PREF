package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class SimplePackage {
    @Id
    @NotNull
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String dimension;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PackageMaterialType.class, fetch = FetchType.EAGER)
    private List<PackageMaterialType> materialsType;
    @NotNull
    private PackageCategory packageCategory;

    public SimplePackage() {
        materialsType = new LinkedList<>();
    }

    public SimplePackage(long id, String name, String dimension, List<PackageMaterialType> materialsType, PackageCategory packageCategory) {
        this.id = id;
        this.name = name;
        this.dimension = dimension;
        this.materialsType = new LinkedList<>();
        this.materialsType.addAll(materialsType);
        this.packageCategory = packageCategory;
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

    public PackageCategory getPackageCategory() {
        return packageCategory;
    }

    public void setPackageCategory(PackageCategory packageCategory) {
        this.packageCategory = packageCategory;
    }

    public void addMaterialType(PackageMaterialType materialType) {
        this.materialsType.add(materialType);
    }

    public void removeMaterialType(PackageMaterialType materialType) {
        this.materialsType.remove(materialType);
    }
}
