package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageDTO implements Serializable {
    private long id;
    private String dimension;
    private List<PackageMaterialType> materialsType;
    private PackageType type;

    public SimplePackageDTO() {
        materialsType = new LinkedList<>();
    }

    public SimplePackageDTO(long id, String dimension, List<PackageMaterialType> materialsType, PackageType type) {
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

    public void setMaterialType(List<PackageMaterialType> materialsType) {
        this.materialsType = materialsType;
    }

    public PackageType getPackageType() {
        return type;
    }

    public void setPackageType(PackageType type) {
        this.type = type;
    }

    public static SimplePackageDTO from(SimplePackage simplePackage) {
        return new SimplePackageDTO(
                simplePackage.getId(),
                simplePackage.getDimension(),
                simplePackage.getMaterialsType(),
                simplePackage.getPackageType());
    }

    public static List<SimplePackageDTO> from(List<SimplePackage> simplePackages) {
        return simplePackages.stream().map(SimplePackageDTO::from).collect(Collectors.toList());
    }
}
