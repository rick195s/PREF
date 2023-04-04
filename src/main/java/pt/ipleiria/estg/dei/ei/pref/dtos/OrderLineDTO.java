package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLineDTO implements Serializable {

    private long id;
    private int quantity;
    private double product_price;
    private ProductDTO product;

    private SimplePackageDTO simplePackage;

    public OrderLineDTO(long id, int quantity, double product_price, ProductDTO product, SimplePackageDTO simplePackage) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.simplePackage = simplePackage;
        this.product_price = product_price;
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

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public SimplePackageDTO getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackageDTO simplePackage) {
        this.simplePackage = simplePackage;
    }

    public static OrderLineDTO from(OrderLine orderLine) {
        return new OrderLineDTO(
                orderLine.getId(),
                orderLine.getQuantity(),
                orderLine.getProduct_price(),
                ProductDTO.from(orderLine.getProduct()),

                orderLine.getSimplePackage() != null ? SimplePackageDTO.from(orderLine.getSimplePackage()) : null
        );
    }

    public static List<OrderLineDTO> from(List<OrderLine> orderLines) {
        return orderLines.stream().map(OrderLineDTO::from).collect(Collectors.toList());
    }
}
