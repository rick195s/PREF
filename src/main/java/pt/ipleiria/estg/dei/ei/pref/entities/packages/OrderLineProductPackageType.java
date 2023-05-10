package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

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
        )})
public class OrderLineProductPackageType extends ObservablePackage<ProductPackageType> implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_line_product_relation_id")
    private OrderLineProductRelation orderLineProductRelation;

    public OrderLineProductPackageType() {
    }

    public OrderLineProductRelation getOrderLineProductRelation() {
        return orderLineProductRelation;
    }

    public void setOrderLineProductRelation(OrderLineProductRelation orderLineProductRelation) {
        this.orderLineProductRelation = orderLineProductRelation;
    }
}
