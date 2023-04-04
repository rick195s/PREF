package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO implements Serializable {
    private long trackingNumber;
    private String orderDate;
    private List<OrderLineDTO> orderLines;
    private String source;
    private String destination;
    private OrderState state;
    private float weight;
    private String carrier;
    private List<String> shippingMethods;

    public OrderDTO(long trackingNumber, String orderDate, List<OrderLineDTO> orderLines, String source, String destination, OrderState state, float weight, String carrier, List<String> shippingMethods) {
        this.trackingNumber = trackingNumber;
        this.orderDate = orderDate;
        this.orderLines = orderLines;
        this.source = source;
        this.destination = destination;
        this.state = state;
        this.weight = weight;
        this.carrier = carrier;
        this.shippingMethods = shippingMethods;
    }

    public OrderDTO() {
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

    public List<OrderLineDTO> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDTO> orderLines) {
        this.orderLines = orderLines;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public void setShippingMethods(List<String> shippingMethods) {
        this.shippingMethods = shippingMethods;
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
        OrderDTO orderDTO = new OrderDTO();
        return new OrderDTO(
                order.getTrackingNumber(),
                order.getDate(),
                OrderLineDTO.from(order.getOrderLines()),
                order.getSource(),
                order.getDestination(),
                order.getState(),
                order.getWeight(),
                order.getCarrier(),
                order.getShippingMethods()
        );
    }

    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }
}