package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLineDTO implements Serializable {

    private long id;
    private int quantity;
    private float productPrice;
    private ProductDTO product;

    public OrderLineDTO(long id, int quantity, float productPrice, ProductDTO product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.productPrice = productPrice;
    }

    public OrderLineDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public static OrderLineDTO from(OrderLine orderLine) {
        return new OrderLineDTO(
                orderLine.getId(),
                orderLine.getQuantity(),
                orderLine.getProductPrice(),
                ProductDTO.from(orderLine.getProduct())
        );
    }

    public static List<OrderLineDTO> from(List<OrderLine> orderLines) {
        return orderLines.stream().map(OrderLineDTO::from).collect(Collectors.toList());
    }
}