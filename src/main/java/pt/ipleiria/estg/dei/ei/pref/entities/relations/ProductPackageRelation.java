package pt.ipleiria.estg.dei.ei.pref.entities.relations;

import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;

import javax.persistence.*;

@Entity
@Table(
        name = "product_package_relations"
)
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
    private ProductPackage productPackage;

    private ProductPackageType type;

    public ProductPackageRelation() {
    }

    public ProductPackageRelation(ProductPackageRelationPK id, Product product, ProductPackage productPackage, ProductPackageType type) {
        this.id = id;
        this.product = product;
        this.productPackage = productPackage;
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

    public ProductPackage getProductPackage() {
        return productPackage;
    }

    public void setProductPackage(ProductPackage productPackage) {
        this.productPackage = productPackage;
    }

    public ProductPackageType getType() {
        return type;
    }

    public void setType(ProductPackageType type) {
        this.type = type;
    }
}
