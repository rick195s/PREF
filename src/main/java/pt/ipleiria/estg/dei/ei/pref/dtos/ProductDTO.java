package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.dtos.packages.ProductPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.ProductPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductCategory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private long id;

    private String name;

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

    private List<ProductPackageTypeDTO> productPackageTypes;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, ProductCategory category, float price, float weight, int validityRange, float length, float width, float height, List<ProductPackageTypeDTO> productPackageTypes) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.validityRange = validityRange;
        this.length = length;
        this.width = width;
        this.height = height;
        this.productPackageTypes = productPackageTypes;
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

    public void addProductPackageTypes(ProductPackageTypeDTO productPackageType) {
        this.productPackageTypes.add(productPackageType);
    }

    public List<ProductPackageTypeDTO> getProductPackageTypes() {
        return productPackageTypes;
    }

    public static ProductDTO from(Product product, boolean detailed) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getWeight(),
                product.getValidityRange(),
                product.getLength(),
                product.getWidth(),
                product.getHeight(),
                detailed ? product.getProductPackages() != null ? ProductPackageTypeDTO.fromProductPackageType(product.getProductPackages()) : null : null
        );
    }

    public static List<ProductDTO> from(List<Product> products, boolean detailed) {
        return products.stream().map(product -> from(product, detailed)).collect(Collectors.toList());
    }
}
