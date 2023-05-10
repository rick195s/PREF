package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "order_lines"
)
public class OrderLine {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;

    @Column(name = "product_price")
    private float productPrice;

    @OneToMany(mappedBy = "orderLine", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLineProductRelation> orderLineProductRelations;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderLine(int quantity, float productPrice,  Order order) {
        this();
        this.quantity = quantity;
        this.order = order;
        this.productPrice = productPrice;
    }

    public OrderLine() {
        this.orderLineProductRelations = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderLineProductRelation> getOrderLineProductRelations() {
        return orderLineProductRelations;
    }

    public void setOrderLineProductRelations(List<OrderLineProductRelation> orderLineProductRelations) {
        this.orderLineProductRelations = orderLineProductRelations;
    }

    public Product getProduct() {
        return orderLineProductRelations.get(0).getProduct();
    }
}
