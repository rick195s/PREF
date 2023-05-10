package pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;

import javax.persistence.*;

@Entity
@Table(
        name = "order_line_product_relations"
)
// ManyToMany relation between products and order lines.
// Needed because of extra column "order_line_product_package" in the relation.
public class OrderLineProductRelation {

    @EmbeddedId
    private OrderLineProductRelationPK id;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("product_package_id")
    @JoinColumn(name = "product_package_id")
    private OrderLine orderLine;


    public OrderLineProductRelation() {
    }

    public OrderLineProductRelation(OrderLineProductRelationPK id, Product product, OrderLine orderLine) {
        this.id = id;
        this.product = product;
        this.orderLine = orderLine;
    }

    public OrderLineProductRelationPK getId() {
        return id;
    }

    public void setId(OrderLineProductRelationPK id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }
}
