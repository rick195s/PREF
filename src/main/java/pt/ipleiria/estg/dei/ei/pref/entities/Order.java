package pt.ipleiria.estg.dei.ei.pref.entities;

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
                query = "SELECT o FROM Order o ORDER BY o.trackingNumber" // JPQL
        )})
public class Order {

    @Id
    @NotNull
    @Column(name = "tracking_number")
    protected long trackingNumber;

    protected String date;

    @ManyToOne
    @JoinColumn(name = "simplePackage_id")
    protected SimplePackage simplePackage;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    protected List<OrderLine> orderLines;

    protected String source;

    protected String destination;

    protected OrderState state;

    public Order(long trackingNumber, String date, String source, String destination, OrderState state) {
        this.trackingNumber = trackingNumber;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.state = state;
        this.orderLines = new LinkedList<>();
    }

    public Order() {
        this.orderLines = new LinkedList<>();
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

    public SimplePackage getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackage simplePackage) {
        this.simplePackage = simplePackage;
    }
}
