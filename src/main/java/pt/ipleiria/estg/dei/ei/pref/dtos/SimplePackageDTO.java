package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageDTO implements Serializable {
    private long id;
    private String name;
    private String dimension;
    private List<PackageMaterialType> materialsType;
    private PackageCategory packageCategory;

    public SimplePackageDTO() {
        materialsType = new LinkedList<>();
    }

    public SimplePackageDTO(long id, String name, String dimension, List<PackageMaterialType> materialsType, PackageCategory packageCategory) {
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

    public void setMaterialType(List<PackageMaterialType> materialsType) {
        this.materialsType = materialsType;
    }

    public PackageCategory getPackageCategory() {
        return packageCategory;
    }

    public void setPackageCategory(PackageCategory packageCategory) {
        this.packageCategory = packageCategory;
    }

    public static SimplePackageDTO from(SimplePackage simplePackage) {
        return new SimplePackageDTO(
                simplePackage.getId(),
                simplePackage.getName(),
                simplePackage.getDimension(),
                simplePackage.getMaterialsType(),
                simplePackage.getPackageCategory());
    }

    public static List<SimplePackageDTO> from(List<SimplePackage> simplePackages) {
        return simplePackages.stream().map(SimplePackageDTO::from).collect(Collectors.toList());
    }
}
