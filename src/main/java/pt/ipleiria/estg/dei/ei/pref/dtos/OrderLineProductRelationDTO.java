package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderLineProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLineProductRelationDTO implements Serializable {

    private long id;
    private ProductDTO product;
    private List<OrderLineProductPackageDTO> orderLineProductPackages;

    public OrderLineProductRelationDTO(long id, ProductDTO product, List<OrderLineProductPackageDTO> orderLineProductPackages) {
        this.id = id;
        this.product = product;
        this.orderLineProductPackages = orderLineProductPackages;
    }

    public OrderLineProductRelationDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<OrderLineProductPackageDTO> getOrderLineProductPackages() {
        return orderLineProductPackages;
    }

    public void setOrderLineProductPackages(List<OrderLineProductPackageDTO> orderLineProductPackages) {
        this.orderLineProductPackages = orderLineProductPackages;
    }

    public static OrderLineProductRelationDTO from(OrderLineProductRelation orderLineProductRelation) {
        return new OrderLineProductRelationDTO(
                orderLineProductRelation.getId(),
                ProductDTO.from(orderLineProductRelation.getProduct(), true),
                OrderLineProductPackageDTO.from(orderLineProductRelation.getProductPackage())
        );
    }

    public static List<OrderLineProductRelationDTO> from(List<OrderLineProductRelation> orderLineProductRelation) {
        return orderLineProductRelation.stream().map(OrderLineProductRelationDTO::from).collect(Collectors.toList());
    }
}
