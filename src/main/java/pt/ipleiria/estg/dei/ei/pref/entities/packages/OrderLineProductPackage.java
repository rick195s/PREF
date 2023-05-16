package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "order_line_product_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderLineProductPackages",
                query = "SELECT o FROM OrderLineProductPackage o ORDER BY o.id" // JPQL
        ),
        @NamedQuery(
                name = "getAllSmartOrderLineProductPackages",
                query = "SELECT o FROM OrderLineProductPackage o WHERE o.simplePackageType.isSmart = true ORDER BY o.id" // JPQL
        ),
})
public class OrderLineProductPackage extends ObservablePackage<ProductPackageType> implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_line_product_relation_id")
    private OrderLineProductRelation orderLineProductRelation;

    public OrderLineProductPackage() {
    }

    public OrderLineProductPackage(ProductPackageType productPackage, OrderLineProductRelation orderLineProductRelation) {
        super(productPackage);
        this.orderLineProductRelation = orderLineProductRelation;
    }

    public OrderLineProductPackage(long id) {
        super(id);
    }

    public OrderLineProductRelation getOrderLineProductRelation() {
        return orderLineProductRelation;
    }

    public void setOrderLineProductRelation(OrderLineProductRelation orderLineProductRelation) {
        this.orderLineProductRelation = orderLineProductRelation;
    }
}
