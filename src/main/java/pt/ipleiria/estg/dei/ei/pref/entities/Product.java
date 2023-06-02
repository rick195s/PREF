package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelation;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "products"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.id" // JPQL
        )})
public class Product {
    @Id
    private String id;

    private String name;

    private String type;

    // dimensions of the product in mm
    private float length;
    private float width;
    private float height;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLineProductRelation> orderLineProductRelations;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductPackageRelation> productPackageRelations;

    public Product(String name, float length, float width, float height, String type) {
        this();
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public Product() {
        this.orderLineProductRelations = new LinkedList<>();
        this.productPackageRelations = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProductPackageRelation> getProductPackageRelations() {
        return productPackageRelations;
    }

    public List<ProductPackageType> getProductPackages(){
        List<ProductPackageType> packages = new LinkedList<>();

        for (ProductPackageRelation relation : productPackageRelations) {
            ProductPackageType productPackageType = relation.getProductPackage();
            productPackageType.setType(relation.getType());
            packages.add(productPackageType);
        }
        return packages;
    }

    public List<OrderLineProductRelation> getOrderLineProductRelations() {
        return orderLineProductRelations;
    }

    public void setOrderLineProductRelations(List<OrderLineProductRelation> orderLineProductRelations) {
        this.orderLineProductRelations = orderLineProductRelations;
    }

    public void setProductPackageRelations(List<ProductPackageRelation> productPackageRelations) {
        this.productPackageRelations = productPackageRelations;
    }
}
