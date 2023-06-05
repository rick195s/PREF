package pt.ipleiria.estg.dei.ei.pref.entities;

import org.hibernate.annotations.GenericGenerator;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

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

    @Column(name="distribution_center")
    private String distributionCenter;

    @Column(name="cp_destiny")
    private String cpDestiny;

    private String feedback;

    @Column(name="delivery_date_hour")
    private String deliveryDateHour;

    @Column(name="prev_delivery_date_hour")
    private String prevDeliveryDateHour;

    @Column(name="volume_number")
    private int volumeNumber;

    public Order() {
        this.orderLines = new LinkedList<>();
        this.orderPackages = new LinkedList<>();
    }

    public Order(String date, float weight, String carrier, String shippingMethod, String channel, String store, String distributionCenter, String cpDestiny, String feedback, String deliveryDateHour, String prevDeliveryDateHour, int volumeNumber){
        this();
        this.date = date;
        this.weight = weight;
        this.carrier = carrier;
        this.shippingMethod = shippingMethod;
        this.channel = channel;
        this.store = store;
        this.distributionCenter = distributionCenter;
        this.cpDestiny = cpDestiny;
        this.feedback = feedback;
        this.deliveryDateHour = deliveryDateHour;
        this.prevDeliveryDateHour = prevDeliveryDateHour;
        this.volumeNumber = volumeNumber;
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

    public String getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(String distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getCpDestiny() {
        return cpDestiny;
    }

    public void setCpDestiny(String cpDestiny) {
        this.cpDestiny = cpDestiny;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDeliveryDateHour() {
        return deliveryDateHour;
    }

    public void setDeliveryDateHour(String deliveryDateHour) {
        this.deliveryDateHour = deliveryDateHour;
    }

    public String getPrevDeliveryDateHour() {
        return prevDeliveryDateHour;
    }

    public void setPrevDeliveryDateHour(String prevDeliveryDateHour) {
        this.prevDeliveryDateHour = prevDeliveryDateHour;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(int volumeNumber) {
        this.volumeNumber = volumeNumber;
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
