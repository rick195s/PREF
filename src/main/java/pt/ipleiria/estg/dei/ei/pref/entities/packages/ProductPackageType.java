package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

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
public class ProductPackageType extends SimplePackage implements Serializable {
    @Transient
    // Aux property, real type is in ProductPackageRelation
    private pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType type;

    @OneToMany(mappedBy = "productPackage")
    private List<ProductPackageRelation> productPackageRelations;

    public ProductPackageType() {
    }

    public ProductPackageType(String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        super(name, cost, dimension, isSustainable, resistance, isSmart);
    }

    public pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType getType() {
        return type;
    }

    public void setType(pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType type) {
        this.type = type;
    }
}
