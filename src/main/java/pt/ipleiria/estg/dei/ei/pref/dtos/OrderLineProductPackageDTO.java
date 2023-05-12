package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderLineProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLineProductPackageDTO implements Serializable {

    private long id;
    private long simplePackageTypeId;

    public OrderLineProductPackageDTO(long id, long simplePackageTypeId) {
        this.id = id;
        this.simplePackageTypeId = simplePackageTypeId;
    }

    public OrderLineProductPackageDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSimplePackageTypeId() {
        return simplePackageTypeId;
    }

    public void setSimplePackageTypeId(long simplePackageTypeId) {
        this.simplePackageTypeId = simplePackageTypeId;
    }

    public static OrderLineProductPackageDTO from(OrderLineProductPackage orderLineProductPackage) {
        return new OrderLineProductPackageDTO(
                orderLineProductPackage.getId(),
                orderLineProductPackage.getSimplePackageType().getId()
        );
    }

    public static List<OrderLineProductPackageDTO> from(List<OrderLineProductPackage> orderLineProductPackage) {
        return orderLineProductPackage.stream().map(OrderLineProductPackageDTO::from).collect(Collectors.toList());
    }
}
