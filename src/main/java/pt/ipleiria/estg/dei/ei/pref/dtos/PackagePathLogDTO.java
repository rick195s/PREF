package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.PackagePathLog;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackagePathLogDTO implements Serializable {
    private long id;

    private String location;

    private String transportationType;

    private String dateOfArrival;

    private String dateOfDeparture;

    private long orderTrackingNumber;

    public PackagePathLogDTO() {
    }

    public PackagePathLogDTO(long id, String location, String transportationType, String dateOfArrival, String dateOfDeparture, long orderTrackingNumber) {
        this.id = id;
        this.location = location;
        this.transportationType = transportationType;
        this.dateOfArrival = dateOfArrival;
        this.dateOfDeparture = dateOfDeparture;
        this.orderTrackingNumber = orderTrackingNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public String getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(String dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public long getOrderTrackingNumber() {
        return orderTrackingNumber;
    }

    public void setOrderTrackingNumber(long orderTrackingNumber) {
        this.orderTrackingNumber = orderTrackingNumber;
    }

    public static PackagePathLogDTO from(PackagePathLog packagePathLog) {
        return new PackagePathLogDTO(
                packagePathLog.getId(),
                packagePathLog.getLocation(),
                packagePathLog.getTransportationType(),
                packagePathLog.getDateOfArrival(),
                packagePathLog.getDateOfDeparture(),
                packagePathLog.getOrder().getTrackingNumber()
        );
    }

    public static List<PackagePathLogDTO> from(List<PackagePathLog> packagePathLogs) {
        return packagePathLogs.stream().map(PackagePathLogDTO::from).collect(Collectors.toList());
    }
}
