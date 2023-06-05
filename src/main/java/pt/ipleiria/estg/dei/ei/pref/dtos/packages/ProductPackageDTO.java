package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageLevel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageDTO {

    private String id;
    // filled with the ProductPackageRelation.productPackageLevel
    private ProductPackageLevel productPackageLevel;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(String id, ProductPackageLevel productPackageLevel) {
        this.id = id;
        this.productPackageLevel = productPackageLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductPackageLevel getProductPackageLevel() {
        return productPackageLevel;
    }

    public void setProductPackageLevel(ProductPackageLevel productPackageLevel) {
        this.productPackageLevel = productPackageLevel;
    }

    public static ProductPackageDTO from(ProductPackageType productPackageType) {
        return new ProductPackageDTO(
                productPackageType.getId(),
                productPackageType.getProductPackageLevel()
        );
    }

    public static List<ProductPackageDTO> from(List<ProductPackageType> productPackageTypes) {
        return productPackageTypes.stream().map(ProductPackageDTO::from).collect(Collectors.toList());
    }
}
