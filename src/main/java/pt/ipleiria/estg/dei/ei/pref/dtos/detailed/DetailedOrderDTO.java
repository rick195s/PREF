package pt.ipleiria.estg.dei.ei.pref.dtos.detailed;

import pt.ipleiria.estg.dei.ei.pref.dtos.OrderLineDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class DetailedOrderDTO implements Serializable {
    private String id;
    private String orderDate;
    private List<OrderLineDTO> orderLines;
    private float weight;
    private String carrier;
    private String shippingMethod;
    private List<OrderPackageDTO> orderPackages;
    private List<OrderPackageTypeDTO> orderPackageTypes;

    public DetailedOrderDTO(String id, String orderDate, List<OrderLineDTO> orderLines, float weight, String carrier, String shippingMethod, List<OrderPackageDTO> orderPackages, List<OrderPackageTypeDTO> orderPackageTypes) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderLines = orderLines;
        this.weight = weight;
        this.carrier = carrier;
        this.shippingMethod = shippingMethod;
        this.orderPackages = orderPackages;
        this.orderPackageTypes = orderPackageTypes;
    }

    public DetailedOrderDTO() {
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

    public static DetailedOrderDTO from(Order order) {
        return new DetailedOrderDTO(
                order.getId(),
                order.getDate(),
                OrderLineDTO.from(order.getOrderLines()),
                order.getWeight(),
                order.getCarrier(),
                order.getShippingMethod(),
                order.getOrderPackages() != null ? OrderPackageDTO.fromOrderPackageList(order.getOrderPackages()) : null,
                order.getOrderPackageTypes() != null ? OrderPackageTypeDTO.fromOrderPackageType(order.getOrderPackageTypes()) : null
        );
    }

    public static List<DetailedOrderDTO> from(List<Order> orders) {
        return orders.stream().map(DetailedOrderDTO::from).collect(Collectors.toList());
    }
}
