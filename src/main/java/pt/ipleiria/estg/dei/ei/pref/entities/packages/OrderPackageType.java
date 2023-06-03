package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderPackageTypes",
                query = "SELECT o FROM OrderPackageType o ORDER BY o.id" // JPQL
        ),
        @NamedQuery(
                name = "getAllOrderPackageTypesWithId",
                query = "SELECT o FROM OrderPackageType o WHERE o.id IN :ids ORDER BY o.id" // JPQL
        )
})

public class OrderPackageType extends SimplePackageType implements Serializable {

    private float width;
    private float height;
    private float length;
    private String shape;


    public OrderPackageType() {

    }

    public OrderPackageType(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
        super(id, cost, isSustainable, isSmart);
        this.width = width;
        this.height = height;
        this.length = length;
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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
