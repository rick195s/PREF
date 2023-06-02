package pt.ipleiria.estg.dei.ei.pref.dtos.requests;

import java.io.Serializable;
import java.util.List;

public class ProductQuantityDTO implements Serializable {

    private String productId;
    private Integer quantity;

    public ProductQuantityDTO() {
    }

    public ProductQuantityDTO(String productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
