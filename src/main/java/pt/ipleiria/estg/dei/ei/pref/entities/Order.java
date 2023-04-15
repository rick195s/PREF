package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;

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
                query = "SELECT o FROM Order o ORDER BY o.trackingNumber" // JPQL
        )})
public class Order {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_number")
    protected long trackingNumber;

    protected String date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    protected List<OrderLine> orderLines;

    protected String source;

    protected String destination;

    // in kg
    private float weight;

    private String carrier;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "shipping_methods", joinColumns = @JoinColumn(name = "tracking_number"))
    private List<String> shippingMethods;

    @Enumerated(EnumType.STRING)
    protected OrderState state;

    @ManyToOne
    @JoinColumn(name = "simplePackage_id")
    private SimplePackage simplePackage;


    public Order(String date, String source, String destination, OrderState state, float weight, String carrier, List<String> shippingMethods) {
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.state = state;
        this.weight = weight;
        this.carrier = carrier;
        this.shippingMethods = shippingMethods;
        this.orderLines = new LinkedList<>();
    }

    public Order() {
        this.orderLines = new LinkedList<>();
        this.shippingMethods = new LinkedList<>();
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
