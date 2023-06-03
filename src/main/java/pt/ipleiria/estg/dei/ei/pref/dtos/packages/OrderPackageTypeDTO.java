package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPackageTypeDTO extends SimplePackageTypeDTO implements Serializable {

    private float width;
    private float height;
    private float length;
    private String shape;


    public OrderPackageTypeDTO() {
    }

    public OrderPackageTypeDTO(String id, double cost, boolean isSustainable, boolean isSmart, float width, float height, float length, String shape) {
        super(id, cost, isSustainable, isSmart);
        this.width = width;
        this.height = height;
        this.length = length;
        this.shape = shape;
    }

    public static OrderPackageTypeDTO from(OrderPackageType orderPackageType) {
        return new OrderPackageTypeDTO(
                orderPackageType.getId(),
                orderPackageType.getCost(),
                orderPackageType.isSustainable(),
                orderPackageType.isSmart(),
                orderPackageType.getWidth(),
                orderPackageType.getHeight(),
                orderPackageType.getLength(),
                orderPackageType.getShape()
                );
    }


    public static List<OrderPackageTypeDTO> fromOrderPackageType(List<OrderPackageType> orderPackageTypes) {
        return orderPackageTypes.stream().map(OrderPackageTypeDTO::from).collect(Collectors.toList());
    }
}
