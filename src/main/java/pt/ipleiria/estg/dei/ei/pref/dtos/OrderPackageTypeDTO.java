package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPackageTypeDTO extends SimplePackageDTO implements Serializable {


    public OrderPackageTypeDTO() {
    }

    public OrderPackageTypeDTO(long id, String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        super(id, name, cost, dimension, isSustainable, resistance, isSmart);
    }

    public static OrderPackageTypeDTO from(OrderPackageType orderPackageType) {
        return new OrderPackageTypeDTO(
                orderPackageType.getId(),
                orderPackageType.getName(),
                orderPackageType.getCost(),
                orderPackageType.getDimension(),
                orderPackageType.isSustainable(),
                orderPackageType.getResistance(),
                orderPackageType.isSmart());
    }


    public static List<OrderPackageTypeDTO> fromOrderPackageTypeList(List<OrderPackageType> orderPackageTypes) {
        return orderPackageTypes.stream().map(OrderPackageTypeDTO::from).collect(Collectors.toList());
    }
}
