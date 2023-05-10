package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageDTO {

    private long id;
    private String name;

    // filled with the ProductPackageRelation.type
    private pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType packageType;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(long id, String name, pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType packageType) {
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

    public pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType packageType) {
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
