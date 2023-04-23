package pt.ipleiria.estg.dei.ei.pref.dtos;

import java.io.Serializable;
import java.util.List;

public class OrderDTO implements Serializable {

    private List<Long> productIds;
    private String source;
    private String destination;
    private String carrier;
    private List<String> shippingMethods;

    public OrderDTO() {
    }

    public OrderDTO(List<Long> productIds, String source, String destination, String carrier, List<String> shippingMethods) {
        this.productIds = productIds;
        this.source = source;
        this.destination = destination;
        this.carrier = carrier;
        this.shippingMethods = shippingMethods;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
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

    public List<String> getShippingMethods() {
        return shippingMethods;
    }

    public void setShippingMethods(List<String> shippingMethods) {
        this.shippingMethods = shippingMethods;
    }
}
