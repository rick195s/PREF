package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLineDTO implements Serializable {

    private long id;
    private int quantity;
    private String product;

    public OrderLineDTO(long id, int quantity, String product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public OrderLineDTO() {
    }

    public static OrderLineDTO from(OrderLine orderLine) {
        return new OrderLineDTO(
                orderLine.getId(),
                orderLine.getQuantity(),
                orderLine.getProduct()
        );
    }

    public static List<OrderLineDTO> from(List<OrderLine> orderLines) {
        return orderLines.stream().map(OrderLineDTO::from).collect(Collectors.toList());
    }
}
