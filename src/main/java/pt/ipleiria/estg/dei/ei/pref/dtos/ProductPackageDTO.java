package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageDTO {

    private long id;
    private String name;

    // filled with the ProductPackageRelation.type
    private ProductPackageType packageType;

    public ProductPackageDTO() {
    }

    public ProductPackageDTO(long id, String name, ProductPackageType packageType) {
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

    public ProductPackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(ProductPackageType packageType) {
        this.packageType = packageType;
    }

    public static ProductPackageDTO from(ProductPackage productPackage) {
        return new ProductPackageDTO(
                productPackage.getId(),
                productPackage.getName(),
                productPackage.getType()
        );
    }

    public static List<ProductPackageDTO> from(List<ProductPackage> productPackages) {
        return productPackages.stream().map(ProductPackageDTO::from).collect(Collectors.toList());
    }
}
