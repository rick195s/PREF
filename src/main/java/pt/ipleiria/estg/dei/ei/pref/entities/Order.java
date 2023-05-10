package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "orders"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.date DESC " // JPQL
        )})
public class Order {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_number")
    private long trackingNumber;

    private String date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLines;

    private String source;

    private String destination;

    // in kg
    private float weight;

    private String carrier;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "shipping_methods", joinColumns = @JoinColumn(name = "tracking_number"))
    private List<String> shippingMethods;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderPackage> orderPackages;

    public Order() {
        this.orderLines = new LinkedList<>();
        this.shippingMethods = new LinkedList<>();
        this.orderPackages = new LinkedList<>();
    }

    public Order(String date, String source, String destination, float weight, String carrier, List<String> shippingMethods, OrderState state) {
        this();
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.carrier = carrier;
        this.state = state;
        this.shippingMethods = shippingMethods;
        this.orderPackages = new LinkedList<>();
    }

    public long getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(long trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        this.orderLines.add(orderLine);
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public List<OrderPackage> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackage> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public void addOrderPackage(OrderPackage orderPackage) {
        this.orderPackages.add(orderPackage);
    }

    public List<String> getShippingMethods() {
        return shippingMethods;
    }

    public void addShippingMethod(String shippingMethod) {
        this.shippingMethods.add(shippingMethod);
    }

    public void removeShippingMethod(String shippingMethod) {
        this.shippingMethods.remove(shippingMethod);
    }
}
