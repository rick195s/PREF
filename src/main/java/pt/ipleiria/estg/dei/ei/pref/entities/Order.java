package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(
        name = "orders"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.trackingNumber" // JPQL
        )})
public class Order {

    @Id
    @NotNull
    @Column(name = "tracking_number")
    protected long trackingNumber;

    protected String orderDate;

    protected String client;

    protected String supplier;

    @ManyToOne
    @JoinColumn(name = "simple_package_id")
    protected SimplePackage simplePackage;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @OrderColumn(name = "product_index")
    @CollectionTable(name = "products")
    protected List<String> products;

    protected String source;

    protected String destination;

    protected OrderState state;

    public Order(long trackingNumber, String orderDate, String client, String supplier, List<String> products, String source, String destination, OrderState state) {
        this.trackingNumber = trackingNumber;
        this.orderDate = orderDate;
        this.client = client;
        this.supplier = supplier;
        this.products = products;
        this.source = source;
        this.destination = destination;
        this.state = state;
    }

    public Order() {
    }

    public long getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(long trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public SimplePackage getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackage simplePackage) {
        this.simplePackage = simplePackage;
    }

}
