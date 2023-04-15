package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageDTO implements Serializable {
    private long id;
    private String name;
    private double cost;
    private String dimension;
    private boolean isSustainable;
    private ResistenceType resistance;
    private boolean isSmart;

    public SimplePackageDTO() {
    }

    public SimplePackageDTO(long id, String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        this.id = id;
        this.cost = cost;
        this.dimension = dimension;
        this.isSustainable = isSustainable;
        this.resistance = resistance;
        this.isSmart = isSmart;
        this.name = name;
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

    public static SimplePackageDTO from(SimplePackage simplePackage) {
        return new SimplePackageDTO(
                simplePackage.getId(),
                simplePackage.getName(),
                simplePackage.getCost(),
                simplePackage.getDimension(),
                simplePackage.isSustainable(),
                simplePackage.getResistance(),
                simplePackage.isSmart());
    }

    public static List<SimplePackageDTO> from(List<SimplePackage> simplePackages) {
        return simplePackages.stream().map(SimplePackageDTO::from).collect(Collectors.toList());
    }
}
