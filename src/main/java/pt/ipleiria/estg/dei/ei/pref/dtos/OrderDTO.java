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
    private String distributionCenter;
    private String cpDestiny;
    private String feedback;
    private String deliveryDateHour;
    private String prevDeliveryDateHour;
    private int volumeNumber;
    private List<ProductQuantityDTO> productsQuantities;

    public OrderDTO() {
        this.orderLines = new LinkedList<>();
        this.orderPackages = new LinkedList<>();
        this.orderPackageTypes = new LinkedList<>();
    }

    public OrderDTO(String id, String orderDate, List<OrderLineDTO> orderLines, float weight, String carrier, String shippingMethod, List<OrderPackageDTO> orderPackages, List<OrderPackageTypeDTO> orderPackageTypes, String channel, String store, String distributionCenter, String cpDestiny, String feedback, String deliveryDateHour, String prevDeliveryDateHour, int volumeNumber) {
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
                order.getDistributionCenter(),
                order.getCpDestiny(),
                order.getFeedback(),
                order.getDeliveryDateHour(),
                order.getPrevDeliveryDateHour(),
                order.getVolumeNumber()
        );
    }

    public static List<OrderDTO> from(List<Order> orders, Boolean detailed) {
        return orders.stream().map(order -> from(order, detailed)).collect(Collectors.toList());
    }
}
