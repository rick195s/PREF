package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPackageDTO implements Serializable {

    private long id;
    private long simplePackageTypeId;

    public OrderPackageDTO() {
    }

    public OrderPackageDTO(long id, long simplePackageTypeId){
        this.id = id;
        this.simplePackageTypeId = simplePackageTypeId;
    }

    public static OrderPackageDTO from(OrderPackage orderPackage) {
        return new OrderPackageDTO(
                orderPackage.getId(),
                orderPackage.getSimplePackageType().getId()
        );
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

    public static List<OrderPackageDTO> fromOrderPackageList(List<OrderPackage> orderPackage) {
        return orderPackage.stream().map(OrderPackageDTO::from).collect(Collectors.toList());
    }
}
