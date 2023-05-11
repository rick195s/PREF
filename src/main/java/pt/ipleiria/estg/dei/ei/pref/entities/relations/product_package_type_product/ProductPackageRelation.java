package pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product;

import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageLevel;

import javax.persistence.*;

@Entity
@Table(
        name = "product_package_relations"
)
@NamedQueries({
        @NamedQuery(
                name = "getPackageTypeOfProduct",
                query = "SELECT ppr.productPackageType FROM ProductPackageRelation ppr WHERE ppr.product.id = :productId AND ppr.type = :level"
        )})
// ManyToMany relation between products and product packages.
// Needed because of extra column "type" in the relation.
public class ProductPackageRelation {

    @EmbeddedId
    private ProductPackageRelationPK id;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("product_package_id")
    @JoinColumn(name = "product_package_id")
    private ProductPackageType productPackageType;

    private ProductPackageLevel type;

    public ProductPackageRelation() {
    }

    public ProductPackageRelation(ProductPackageRelationPK id, Product product, ProductPackageType productPackageType, ProductPackageLevel type) {
        this.id = id;
        this.product = product;
        this.productPackageType = productPackageType;
        this.type = type;
    }

    public ProductPackageRelationPK getId() {
        return id;
    }

    public void setId(ProductPackageRelationPK id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductPackageType getProductPackage() {
        return productPackageType;
    }

    public void setProductPackage(ProductPackageType productPackageType) {
        this.productPackageType = productPackageType;
    }

    public ProductPackageLevel getType() {
        return type;
    }

    public void setType(ProductPackageLevel type) {
        this.type = type;
    }
}
