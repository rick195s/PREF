package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageDTO implements Serializable {
    private long id;
    private double cost;
    private String dimension;
    private List<PackageMaterialType> materialsType;
    private boolean is_sustainable;
    private ResistenceType resistance;
    private boolean is_smart;
    public SimplePackageDTO() {
        materialsType = new LinkedList<>();
    }

    public SimplePackageDTO(long id, double cost, String dimension, List<PackageMaterialType> materialsType, boolean is_sustainable, ResistenceType resistance, boolean is_smart) {
        this.id = id;
        this.cost = cost;
        this.dimension = dimension;
        this.materialsType = new LinkedList<>();
        this.materialsType.addAll(materialsType);
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

    public void setMaterialType(List<PackageMaterialType> materialsType) {
        this.materialsType = materialsType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public static SimplePackageDTO from(SimplePackage simplePackage) {
        return new SimplePackageDTO(
                simplePackage.getId(),
                simplePackage.getCost(),
                simplePackage.getDimension(),
                simplePackage.getMaterialsType(),
                simplePackage.is_sustainable(),
                simplePackage.getResistance(),
                simplePackage.is_smart());
    }

    public static List<SimplePackageDTO> from(List<SimplePackage> simplePackages) {
        return simplePackages.stream().map(SimplePackageDTO::from).collect(Collectors.toList());
    }
}
