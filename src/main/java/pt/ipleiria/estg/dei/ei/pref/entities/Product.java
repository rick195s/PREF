package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductCategory;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private float price;

    // in kg
    private float weight;

    // in days
    private int validityRange;

    // dimensions of the product in cm
    private float length;
    private float width;
    private float height;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLine;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductPackageRelation> productPackageRelations;

    public Product(String name, ProductCategory category, float price, float weight, int validityRange, float length, float width, float height) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.validityRange = validityRange;
        this.length = length;
        this.width = width;
        this.height = height;
        orderLine = new LinkedList<>();
    }

    public Product() {
        orderLine = new LinkedList<>();
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getValidityRange() {
        return validityRange;
    }

    public void setValidityRange(int validityRange) {
        this.validityRange = validityRange;
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

    public List<ProductPackageRelation> getProductPackageRelations() {
        return productPackageRelations;
    }

    public List<ProductPackage> getProductPackages(){
        List<ProductPackage> packages = new LinkedList<>();

        for (ProductPackageRelation relation : productPackageRelations) {
            ProductPackage productPackage = relation.getProductPackage();
            productPackage.setType(relation.getType());
            packages.add(productPackage);
        }
        return packages;
    }
}
