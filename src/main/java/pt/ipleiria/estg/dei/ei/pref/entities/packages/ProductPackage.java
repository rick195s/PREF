package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
        name = "product_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllProductPackages",
                query = "SELECT p FROM ProductPackage p ORDER BY p.id" // JPQL
        )})
public class ProductPackage extends SimplePackage implements Serializable {
    @Transient
    // Aux property, real type is in ProductPackageRelation
    private ProductPackageType type;

    @OneToMany(mappedBy = "productPackage")
    private List<ProductPackageRelation> productPackageRelations;

    public ProductPackage() {
    }

    public ProductPackageType getType() {
        return type;
    }

    public void setType(ProductPackageType type) {
        this.type = type;
    }
}
