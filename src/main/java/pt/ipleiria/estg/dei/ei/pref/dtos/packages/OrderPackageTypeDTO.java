package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPackageTypeDTO extends SimplePackageTypeDTO implements Serializable {


    public OrderPackageTypeDTO() {
    }

    public OrderPackageTypeDTO(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
        super(id, cost, isSustainable, isSmart, shape, width, height, length);
    }

    public static OrderPackageTypeDTO from(OrderPackageType orderPackageType) {
        return new OrderPackageTypeDTO(
                orderPackageType.getId(),
                orderPackageType.getCost(),
                orderPackageType.isSustainable(),
                orderPackageType.isSmart(),
                orderPackageType.getShape(),
                orderPackageType.getWidth(),
                orderPackageType.getHeight(),
                orderPackageType.getLength()
                );
    }


    public static List<OrderPackageTypeDTO> fromOrderPackageType(List<OrderPackageType> orderPackageTypes) {
        return orderPackageTypes.stream().map(OrderPackageTypeDTO::from).collect(Collectors.toList());
    }
}
