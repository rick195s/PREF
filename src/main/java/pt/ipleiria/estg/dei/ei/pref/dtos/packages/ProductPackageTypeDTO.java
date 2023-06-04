package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageTypeDTO extends SimplePackageTypeDTO implements Serializable {


    private String name;

    private String composition;

    private String discard;


    public ProductPackageTypeDTO() {
    }

    public ProductPackageTypeDTO(String id, double cost, boolean isSustainable, boolean isSmart, String name, String composition, String discard) {
        super(id, cost, isSustainable, isSmart);
        this.name = name;
        this.composition = composition;
        this.discard = discard;
    }

    public static ProductPackageTypeDTO from(ProductPackageType productPackageType) {
        return new ProductPackageTypeDTO(
                productPackageType.getId(),
                productPackageType.getCost(),
                productPackageType.isSustainable(),
                productPackageType.isSmart(),
                productPackageType.getName(),
                productPackageType.getComposition(),
                productPackageType.getDiscard()
        );
    }


    public static List<ProductPackageTypeDTO> fromProductPackageType(List<ProductPackageType> productPackageTypes) {
        return productPackageTypes.stream().map(ProductPackageTypeDTO::from).collect(Collectors.toList());
    }
}
