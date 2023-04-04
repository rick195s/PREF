package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductCategory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private long id;

    private String name;

    private ProductCategory category;

    // in kg
    private float weight;

    // in days
    private int validityRange;

    // dimensions of the product in cm
    private float length;
    private float width;
    private float height;


    public ProductDTO() {
    }

    public ProductDTO(long id, String name, ProductCategory category, float weight, int validityRange, float length, float width, float height) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.weight = weight;
        this.validityRange = validityRange;
        this.length = length;
        this.width = width;
        this.height = height;
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

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getWeight(),
                product.getValidityRange(),
                product.getLength(),
                product.getWidth(),
                product.getHeight()

        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
}
