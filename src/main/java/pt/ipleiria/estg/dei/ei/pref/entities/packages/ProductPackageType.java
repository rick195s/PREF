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
    private ProductPackageLevel productPackageLevel;

    private String name;

    private String composition;

    private String discard;

    @OneToMany(mappedBy = "productPackageType")
    private List<ProductPackageRelation> productPackageRelations;

    public ProductPackageType() {
    }

    public ProductPackageType(String id, double cost, boolean isSustainable, boolean isSmart, String name, String composition, String discard) {
        super(id, cost, isSustainable, isSmart);
        this.name = name;
        this.composition = composition;
        this.discard = discard;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDiscard() {
        return discard;
    }

    public void setDiscard(String discard) {
        this.discard = discard;
    }

    public ProductPackageLevel getProductPackageLevel() {
        return productPackageLevel;
    }

    public void setProductPackageLevel(ProductPackageLevel productPackageLevel) {
        this.productPackageLevel = productPackageLevel;
    }
}
