package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class SimplePackageTypeDTO implements Serializable {
    private String id;
    private double cost;
    private boolean isSustainable;
    private boolean isSmart;
    private String shape;
    private float width;
    private float height;
    private float length;

    public SimplePackageTypeDTO() {
    }

    public SimplePackageTypeDTO(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
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

    public static SimplePackageTypeDTO from(SimplePackageType simplePackageType) {
        return new SimplePackageTypeDTO(
                simplePackageType.getId(),
                simplePackageType.getCost(),
                simplePackageType.isSustainable(),
                simplePackageType.isSmart(),
                simplePackageType.getShape(),
                simplePackageType.getWidth(),
                simplePackageType.getHeight(),
                simplePackageType.getLength()
                );
    }

    public static List<SimplePackageTypeDTO> from(List<SimplePackageType> simplePackageTypes) {
        return simplePackageTypes.stream().map(SimplePackageTypeDTO::from).collect(Collectors.toList());
    }
}
