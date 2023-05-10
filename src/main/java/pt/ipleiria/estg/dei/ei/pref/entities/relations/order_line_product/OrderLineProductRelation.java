package pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;

import javax.persistence.*;

@Entity
@Table(
        name = "order_line_product_relations"
)
public class OrderLineProductRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_line_id")
    private OrderLine orderLine;


    public OrderLineProductRelation() {
    }

    public OrderLineProductRelation(Product product, OrderLine orderLine) {
        this.product = product;
        this.orderLine = orderLine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
