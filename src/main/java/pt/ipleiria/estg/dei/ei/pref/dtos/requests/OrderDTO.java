package pt.ipleiria.estg.dei.ei.pref.dtos.requests;

import java.io.Serializable;
import java.util.List;

public class OrderDTO implements Serializable {

    private List<ProductQuantityDTO> productsQuantities;
    private String carrier;
    private String shippingMethod;

    public OrderDTO() {
    }

    public OrderDTO(List<ProductQuantityDTO> productsQuantities, String source, String destination, String carrier, String shippingMethod) {
        this.productsQuantities = productsQuantities;
        this.carrier = carrier;
        this.shippingMethod = shippingMethod;
    }

    public List<ProductQuantityDTO> getProductsQuantities() {
        return productsQuantities;
    }

    public void setProductsQuantities(List<ProductQuantityDTO> productsQuantities) {
        this.productsQuantities = productsQuantities;
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
