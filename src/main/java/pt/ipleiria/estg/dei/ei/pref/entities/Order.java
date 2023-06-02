package pt.ipleiria.estg.dei.ei.pref.entities;

import org.hibernate.annotations.GenericGenerator;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
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
        ),
        @NamedQuery(
                name = "getCarriers",
                query = "SELECT DISTINCT o.carrier FROM Order o ORDER BY o.carrier ASC " // JPQL
        ),
        @NamedQuery(
                name = "getAllOrdersByCarrier",
                query = "SELECT o FROM Order o WHERE o.carrier = :carrier ORDER BY o.date DESC " // JPQL
        )
})
public class Order {

    @Id
    @NotNull
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name="id")
    private String id;

    private String date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderLine> orderLines;

    // in kg
    private float weight;

    private String carrier;

    @Column(name="shipping_method")
    private String shippingMethod;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderPackage> orderPackages;

    private String channel;

    private String store;

    private String distribution_center;

    private String cp_destiny;

    private String feedback;

    private String delivery_date_hour;

    private String prev_delivery_date_hour;

    private int volume_number;

    public Order() {
        this.orderLines = new LinkedList<>();
        this.orderPackages = new LinkedList<>();
    }

    public Order(String date, float weight, String carrier, String shippingMethod, String channel, String store, String distribution_center, String cp_destiny, String feedback, String delivery_date_hour, String prev_delivery_date_hour, int volume_number){
        this();
        this.date = date;
        this.weight = weight;
        this.carrier = carrier;
        this.shippingMethod = shippingMethod;
        this.channel = channel;
        this.store = store;
        this.distribution_center = distribution_center;
        this.cp_destiny = cp_destiny;
        this.feedback = feedback;
        this.delivery_date_hour = delivery_date_hour;
        this.prev_delivery_date_hour = prev_delivery_date_hour;
        this.volume_number = volume_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDistribution_center() {
        return distribution_center;
    }

    public void setDistribution_center(String distribution_center) {
        this.distribution_center = distribution_center;
    }

    public String getCp_destiny() {
        return cp_destiny;
    }

    public void setCp_destiny(String cp_destiny) {
        this.cp_destiny = cp_destiny;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDelivery_date_hour() {
        return delivery_date_hour;
    }

    public void setDelivery_date_hour(String delivery_date_hour) {
        this.delivery_date_hour = delivery_date_hour;
    }

    public String getPrev_delivery_date_hour() {
        return prev_delivery_date_hour;
    }

    public void setPrev_delivery_date_hour(String prev_delivery_date_hour) {
        this.prev_delivery_date_hour = prev_delivery_date_hour;
    }

    public int getVolume_number() {
        return volume_number;
    }

    public void setVolume_number(int volume_number) {
        this.volume_number = volume_number;
    }

    public List<OrderPackage> getOrderPackages() {
        return orderPackages;
    }

    public List<OrderPackageType> getOrderPackageTypes() {
        List<OrderPackageType> orderPackageTypes = new LinkedList<>();
        for (OrderPackage orderPackage : orderPackages) {
            orderPackageTypes.add(orderPackage.getSimplePackageType());
        }

        return orderPackageTypes;
    }


    public void setOrderPackages(List<OrderPackage> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public void addOrderPackage(OrderPackage orderPackage) {
        this.orderPackages.add(orderPackage);
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
