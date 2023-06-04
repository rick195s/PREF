package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.requests.ProductQuantityDTO;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {
    private String id;
    private String orderDate;
    private List<OrderLineDTO> orderLines;
    private float weight;
    private String carrier;
    private String shippingMethod;
    private List<OrderPackageDTO> orderPackages;
    private List<OrderPackageTypeDTO> orderPackageTypes;
    private String channel;
    private String store;
    private String distribution_center;
    private String cp_destiny;
    private String feedback;
    private String delivery_date_hour;
    private String prev_delivery_date_hour;
    private int volume_number;
    private List<ProductQuantityDTO> productsQuantities;

    public OrderDTO() {
        this.orderLines = new LinkedList<>();
        this.orderPackages = new LinkedList<>();
        this.orderPackageTypes = new LinkedList<>();
    }

    public OrderDTO(String id, String orderDate, List<OrderLineDTO> orderLines, float weight, String carrier, String shippingMethod, List<OrderPackageDTO> orderPackages, List<OrderPackageTypeDTO> orderPackageTypes, String channel, String store, String distribution_center, String cp_destiny, String feedback, String delivery_date_hour, String prev_delivery_date_hour, int volume_number) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderLines = orderLines;
        this.weight = weight;
        this.carrier = carrier;
        this.shippingMethod = shippingMethod;
        this.orderPackages = orderPackages;
        this.orderPackageTypes = orderPackageTypes;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDTO> orderLines) {
        this.orderLines = orderLines;
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

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public List<OrderPackageDTO> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackageDTO> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public List<OrderPackageTypeDTO> getOrderPackageTypes() {
        return orderPackageTypes;
    }

    public void setOrderPackageTypes(List<OrderPackageTypeDTO> orderPackageTypes) {
        this.orderPackageTypes = orderPackageTypes;
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

    public List<ProductQuantityDTO> getProductsQuantities() {
        return productsQuantities;
    }

    public void setProductsQuantities(List<ProductQuantityDTO> productsQuantities) {
        this.productsQuantities = productsQuantities;
    }

    public static OrderDTO from(Order order, Boolean detailed) {
        return new OrderDTO(
                order.getId(),
                order.getDate(),
                detailed ? OrderLineDTO.from(order.getOrderLines()) : new LinkedList<>(),
                order.getWeight(),
                order.getCarrier(),
                order.getShippingMethod(),
                detailed ? order.getOrderPackages() != null ? OrderPackageDTO.fromOrderPackageList(order.getOrderPackages()) : null: new LinkedList<>(),
                detailed ? order.getOrderPackageTypes() != null ? OrderPackageTypeDTO.fromOrderPackageType(order.getOrderPackageTypes()) : null: new LinkedList<>(),
                order.getChannel(),
                order.getStore(),
                order.getDistribution_center(),
                order.getCp_destiny(),
                order.getFeedback(),
                order.getDelivery_date_hour(),
                order.getPrev_delivery_date_hour(),
                order.getVolume_number()
        );
    }

    public static List<OrderDTO> from(List<Order> orders, Boolean detailed) {
        return orders.stream().map(order -> from(order, detailed)).collect(Collectors.toList());
    }
}
