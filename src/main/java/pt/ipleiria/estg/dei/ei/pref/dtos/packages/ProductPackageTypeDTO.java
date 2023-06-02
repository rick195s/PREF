package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageTypeDTO extends SimplePackageTypeDTO implements Serializable {


    public ProductPackageTypeDTO() {
    }

    public ProductPackageTypeDTO(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
        super(id, cost, isSustainable, isSmart, shape, width, height, length);
    }

    public static ProductPackageTypeDTO from(ProductPackageType productPackageType) {
        return new ProductPackageTypeDTO(
                productPackageType.getId(),
                productPackageType.getCost(),
                productPackageType.isSustainable(),
                productPackageType.isSmart(),
                productPackageType.getShape(),
                productPackageType.getWidth(),
                productPackageType.getHeight(),
                productPackageType.getLength()
        );
    }


    public static List<ProductPackageTypeDTO> fromProductPackageType(List<ProductPackageType> productPackageTypes) {
        return productPackageTypes.stream().map(ProductPackageTypeDTO::from).collect(Collectors.toList());
    }
}
