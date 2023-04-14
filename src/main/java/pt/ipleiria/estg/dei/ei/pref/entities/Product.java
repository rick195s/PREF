package pt.ipleiria.estg.dei.ei.pref.entities;

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

    @OneToMany(mappedBy = "product")
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

    public static String getProductsJson(){return "[\n{\"name\": \"brownies\",\"category\": \"FOOD\",\"price\": 1.16,\"weight\": 0.060,\"validityRange\": 7,\"length\": 1,\"width\": 1,\"height\": 1},{\"name\": \"Protein Bar\",\"category\": \"FOOD\",\"price\": 2.99,\"weight\": 0.06,\"validityRange\": 90,\"length\": 10,\"width\": 5,\"height\": 2},{\"name\": \"Granola\",\"category\": \"FOOD\",\"price\": 5.99,\"weight\": 0.5,\"validityRange\": 180,\"length\": 15,\"width\": 10,\"height\": 5},{\"name\": \"Multigrain Snack\",\"category\": \"FOOD\",\"price\": 1.99,\"weight\": 0.1,\"validityRange\": 60,\"length\": 5,\"width\": 5,\"height\": 5},{\"name\": \"Blueberry Muffin\",\"category\": \"FOOD\",\"price\": 3.49,\"weight\": 0.1,\"validityRange\": 7,\"length\": 8,\"width\": 5,\"height\": 5},{\"name\": \"Whole Wheat Bread\",\"category\": \"FOOD\",\"price\": 2.99,\"weight\": 0.5,\"validityRange\": 10,\"length\": 20,\"width\": 10,\"height\": 5},{\"name\": \"BCAA Supplements\",\"category\": \"SPORTS_NUTRITION\",\"price\": 24.99,\"weight\": 0.500,\"validityRange\": 24,\"length\": 10,\"width\": 10,\"height\": 15},{\"name\": \"Pre-Workout Supplements\",\"category\": \"SPORTS_NUTRITION\",\"price\": 34.99,\"weight\": 0.250,\"validityRange\": 12,\"length\": 7,\"width\": 7,\"height\": 10},{\"name\": \"Creatine Monohydrate\",\"category\": \"SPORTS_NUTRITION\",\"price\": 19.99,\"weight\": 0.300,\"validityRange\": 36,\"length\": 5,\"width\": 5,\"height\": 10},{\"name\": \"Whey Protein\",\"category\": \"SPORTS_NUTRITION\",\"price\": 29.99,\"weight\": 1,\"validityRange\": 365,\"length\": 10,\"width\": 10,\"height\": 20}]";}
}
