package pt.ipleiria.estg.dei.ei.pref.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(
        name = "products"
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLine;

    public Product(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public void addOrderLine(OrderLine orderLine) {
        this.orderLine.add(orderLine);
    }

    public void removeOrderLine(OrderLine orderLine) {
        this.orderLine.remove(orderLine);
    }
}
