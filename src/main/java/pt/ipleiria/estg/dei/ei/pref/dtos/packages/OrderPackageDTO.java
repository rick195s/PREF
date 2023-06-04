package pt.ipleiria.estg.dei.ei.pref.dtos.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OrderPackageDTO implements Serializable {

    private long id;
    private String simplePackageTypeId;

    public OrderPackageDTO() {
    }

    public OrderPackageDTO(long id, String simplePackageTypeId){
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

    public String getSimplePackageTypeId() {
        return simplePackageTypeId;
    }

    public void setSimplePackageTypeId(String simplePackageTypeId) {
        this.simplePackageTypeId = simplePackageTypeId;
    }

    public static List<OrderPackageDTO> fromOrderPackageList(List<OrderPackage> orderPackage) {
        return orderPackage.stream().map(OrderPackageDTO::from).collect(Collectors.toList());
    }
}
