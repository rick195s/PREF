package pt.ipleiria.estg.dei.ei.pref.dtos.requests;

import java.io.Serializable;
import java.util.List;

public class OrderDTO implements Serializable {

    private List<ProductQuantityDTO> productsQuantities;
    private String source;
    private String destination;
    private String carrier;
    private String shippingMethod;

    public OrderDTO() {
    }

    public OrderDTO(List<ProductQuantityDTO> productsQuantities, String source, String destination, String carrier, String shippingMethod) {
        this.productsQuantities = productsQuantities;
        this.source = source;
        this.destination = destination;
        this.carrier = carrier;
        this.shippingMethod = shippingMethod;
    }

    public List<ProductQuantityDTO> getProductsQuantities() {
        return productsQuantities;
    }

    public void setProductsQuantities(List<ProductQuantityDTO> productsQuantities) {
        this.productsQuantities = productsQuantities;
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

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethods(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
