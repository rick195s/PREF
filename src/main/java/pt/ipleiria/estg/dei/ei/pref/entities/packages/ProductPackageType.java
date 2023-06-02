package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageLevel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProductPackageTypes",
                query = "SELECT p FROM ProductPackageType p ORDER BY p.id" // JPQL
        )})
public class ProductPackageType extends SimplePackageType implements Serializable {
    @Transient
    // Aux property, real type is in ProductPackageRelation
    private ProductPackageLevel type;

    @OneToMany(mappedBy = "productPackageType")
    private List<ProductPackageRelation> productPackageRelations;

    public ProductPackageType() {
    }

    public ProductPackageType(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length, ProductPackageLevel type) {
        super(id, cost, isSustainable, isSmart, shape, width, height, length);
    }

    public ProductPackageLevel getType() {
        return type;
    }

    public void setType(ProductPackageLevel type) {
        this.type = type;
    }
}
