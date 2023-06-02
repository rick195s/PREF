package pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductPackageRelationPK implements Serializable {
    @Column(name = "product_package_id")
    private String productPackageId;

    @Column(name = "product_id")
    private String productId;

    public ProductPackageRelationPK() {
    }

    public ProductPackageRelationPK(String productPackageId, String productId) {
        this.productPackageId = productPackageId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPackageRelationPK that = (ProductPackageRelationPK) o;

        if (!Objects.equals(productPackageId, that.productPackageId))
            return false;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        int result = productPackageId != null ? productPackageId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
