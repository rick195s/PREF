package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import java.io.Serializable;
import java.util.List;

public class OrderDTO implements Serializable {
    private long trackingNumber;
    private String orderDate;
    private String client;
    private String supplier;
    private List<String> products;
    private String source;
    private String destination;
    private OrderState state;

    public OrderDTO(long trackingNumber, String orderDate, String client, String supplier, List<String> products, String source, String destination, OrderState state) {
        this.trackingNumber = trackingNumber;
        this.orderDate = orderDate;
        this.client = client;
        this.supplier = supplier;
        this.products = products;
        this.source = source;
        this.destination = destination;
        this.state = state;
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

    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getTrackingNumber(),
                order.getOrderDate(),
                order.getClient(),
                order.getSupplier(),
                order.getProducts(),
                order.getSource(),
                order.getDestination(),
                order.getState()
        );
    }

}
