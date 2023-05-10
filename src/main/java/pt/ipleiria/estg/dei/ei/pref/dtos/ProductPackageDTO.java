package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageLevel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageDTO {

    private long id;
    private String name;

    // filled with the ProductPackageRelation.type
    private ProductPackageLevel packageType;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(long id, String name, ProductPackageLevel packageType) {
        this.id = id;
        this.name = name;
        this.packageType = packageType;
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

    public ProductPackageLevel getPackageType() {
        return packageType;
    }

    public void setPackageType(ProductPackageLevel packageType) {
        this.packageType = packageType;
    }

    public static ProductPackageDTO from(ProductPackageType productPackageType) {
        return new ProductPackageDTO(
                productPackageType.getId(),
                productPackageType.getName(),
                productPackageType.getType()
        );
    }

    public static List<ProductPackageDTO> from(List<ProductPackageType> productPackageTypes) {
        return productPackageTypes.stream().map(ProductPackageDTO::from).collect(Collectors.toList());
    }
}
