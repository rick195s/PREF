package pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrderLineProductRelationPK implements Serializable {
    @Column(name = "order_line_id")
    private long orderLineId;

    @Column(name = "product_id")
    private long productId;

    public OrderLineProductRelationPK() {
    }

    public OrderLineProductRelationPK(long orderLineId, long productId) {
        this.orderLineId = orderLineId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLineProductRelationPK that = (OrderLineProductRelationPK) o;

        if (orderLineId != that.orderLineId) return false;
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderLineId ^ (orderLineId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }
}
