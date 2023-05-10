package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPackageDTO extends SimplePackageDTO implements Serializable {


    public OrderPackageDTO() {
    }

    public OrderPackageDTO(long id, String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        super(id, name, cost, dimension, isSustainable, resistance, isSmart);
    }

    public static OrderPackageDTO from(OrderPackageType orderPackage) {
        return new OrderPackageDTO(
                orderPackage.getId(),
                orderPackage.getName(),
                orderPackage.getCost(),
                orderPackage.getDimension(),
                orderPackage.isSustainable(),
                orderPackage.getResistance(),
                orderPackage.isSmart());
    }


    public static List<OrderPackageDTO> fromOrderPackageList(List<OrderPackageType> orderPackage) {
        return orderPackage.stream().map(OrderPackageDTO::from).collect(Collectors.toList());
    }
}
