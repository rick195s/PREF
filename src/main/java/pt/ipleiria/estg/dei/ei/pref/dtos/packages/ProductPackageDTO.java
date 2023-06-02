package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageLevel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageDTO {

    private String id;
    // filled with the ProductPackageRelation.type
    private ProductPackageLevel packageType;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(String id, ProductPackageLevel packageType) {
        this.id = id;
        this.packageType = packageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductPackageLevel getPackageType() {
        return packageType;
    }

    public void setPackageType(ProductPackageLevel packageType) {
        this.packageType = packageType;
    }

    public static ProductPackageDTO from(ProductPackageType productPackageType) {
        return new ProductPackageDTO(
                productPackageType.getId(),
                productPackageType.getType()
        );
    }

    public static List<ProductPackageDTO> from(List<ProductPackageType> productPackageTypes) {
        return productPackageTypes.stream().map(ProductPackageDTO::from).collect(Collectors.toList());
    }
}
