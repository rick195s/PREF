package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO implements Serializable {

    private long id;

    private String name;

    private String category;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
}
