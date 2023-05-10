package pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ProductPackageRelationPK implements Serializable {
    @Column(name = "product_package_id")
    private long productPackageId;

    @Column(name = "product_id")
    private long productId;

    public ProductPackageRelationPK() {
    }

    public ProductPackageRelationPK(long productPackageId, long productId) {
        this.productPackageId = productPackageId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPackageRelationPK that = (ProductPackageRelationPK) o;

        if (productPackageId != that.productPackageId) return false;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        int result = (int) (productPackageId ^ (productPackageId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
