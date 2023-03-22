package pt.ipleiria.estg.dei.ei.pref.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    private String product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderLine(int quantity, String product, Order order) {
        this.quantity = quantity;
        this.product = product;
        this.order = order;
    }

    public OrderLine() {
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
