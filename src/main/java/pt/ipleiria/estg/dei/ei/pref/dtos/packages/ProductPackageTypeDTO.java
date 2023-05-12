package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageTypeDTO extends SimplePackageTypeDTO implements Serializable {


    public ProductPackageTypeDTO() {
    }

    public ProductPackageTypeDTO(long id, String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        super(id, name, cost, dimension, isSustainable, resistance, isSmart);
    }

    public static ProductPackageTypeDTO from(ProductPackageType productPackageType) {
        return new ProductPackageTypeDTO(
                productPackageType.getId(),
                productPackageType.getName(),
                productPackageType.getCost(),
                productPackageType.getDimension(),
                productPackageType.isSustainable(),
                productPackageType.getResistance(),
                productPackageType.isSmart()
        );
    }


    public static List<ProductPackageTypeDTO> fromProductPackageType(List<ProductPackageType> productPackageTypes) {
        return productPackageTypes.stream().map(ProductPackageTypeDTO::from).collect(Collectors.toList());
    }
}
