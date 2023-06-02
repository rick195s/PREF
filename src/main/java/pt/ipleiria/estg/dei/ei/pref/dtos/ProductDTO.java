package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.dtos.packages.ProductPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private String id;

    private String name;

    private String type;

    // dimensions of the product in mm
    private float length;
    private float width;
    private float height;

    private List<ProductPackageTypeDTO> productPackageTypes;

    public ProductDTO() {
    }

    public ProductDTO(String id, String name, float length, float width, float height, List<ProductPackageTypeDTO> productPackageTypes, String type) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
        this.productPackageTypes = productPackageTypes;
        this.type = type;
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

    public void addProductPackageTypes(ProductPackageTypeDTO productPackageType) {
        this.productPackageTypes.add(productPackageType);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProductPackageTypeDTO> getProductPackageTypes() {
        return productPackageTypes;
    }

    public static ProductDTO from(Product product, boolean detailed) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getLength(),
                product.getWidth(),
                product.getHeight(),
                detailed ? product.getProductPackages() != null ? ProductPackageTypeDTO.fromProductPackageType(product.getProductPackages()) : null : null,
                product.getType()
        );
    }

    public static List<ProductDTO> from(List<Product> products, boolean detailed) {
        return products.stream().map(product -> from(product, detailed)).collect(Collectors.toList());
    }
}
