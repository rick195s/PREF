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
    private long simplePackageId;

    public OrderDTO(long trackingNumber, String orderDate, List<OrderLineDTO> orderLines, String source, String destination, OrderState state, long simplePackageId) {
        this.trackingNumber = trackingNumber;
        this.orderDate = orderDate;
        this.orderLines = orderLines;
        this.source = source;
        this.destination = destination;
        this.state = state;
        this.simplePackageId = simplePackageId;
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

    public long getSimplePackageId() {
        return simplePackageId;
    }

    public void setSimplePackageId(long simplePackageId) {
        this.simplePackageId = simplePackageId;
    }

    public static OrderDTO from(Order order) {
        return new OrderDTO(
                order.getTrackingNumber(),
                order.getDate(),
                OrderLineDTO.from(order.getOrderLines()),
                order.getSource(),
                order.getDestination(),
                order.getState(),
                order.getSimplePackage() != null ? order.getSimplePackage().getId() : 0
        );
    }

    public static List<OrderDTO> from(List<Order> orders) {
        return orders.stream().map(OrderDTO::from).collect(Collectors.toList());
    }
}
