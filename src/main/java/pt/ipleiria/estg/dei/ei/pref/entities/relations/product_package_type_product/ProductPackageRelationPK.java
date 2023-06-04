package pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductPackageRelationPK implements Serializable {
    @Column(name = "product_package_type_id")
    private String productPackageTypeId;

    @Column(name = "product_id")
    private String productId;

    public ProductPackageRelationPK() {
    }

    public ProductPackageRelationPK(String productPackageTypeId, String productId) {
        this.productPackageTypeId = productPackageTypeId;
        this.productId = productId;
    }

    public String getProductPackageTypeId() {
        return productPackageTypeId;
    }

    public void setProductPackageTypeId(String productPackageTypeId) {
        this.productPackageTypeId = productPackageTypeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductPackageRelationPK that = (ProductPackageRelationPK) o;

        if (!Objects.equals(productPackageTypeId, that.productPackageTypeId))
            return false;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        int result = productPackageTypeId != null ? productPackageTypeId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
