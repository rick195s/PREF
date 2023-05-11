package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageTypeDTO implements Serializable {
    private long id;
    private String name;
    private double cost;
    private String dimension;
    private boolean isSustainable;
    private ResistenceType resistance;
    private boolean isSmart;

    public SimplePackageTypeDTO() {
    }

    public SimplePackageTypeDTO(long id, String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
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

    public static SimplePackageTypeDTO from(SimplePackageType simplePackageType) {
        return new SimplePackageTypeDTO(
                simplePackageType.getId(),
                simplePackageType.getName(),
                simplePackageType.getCost(),
                simplePackageType.getDimension(),
                simplePackageType.isSustainable(),
                simplePackageType.getResistance(),
                simplePackageType.isSmart());
    }

    public static List<SimplePackageTypeDTO> from(List<SimplePackageType> simplePackageTypes) {
        return simplePackageTypes.stream().map(SimplePackageTypeDTO::from).collect(Collectors.toList());
    }
}
