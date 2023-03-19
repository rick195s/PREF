package pt.ipleiria.estg.dei.ei.pref.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "package_path_logs"
)
public class PackagePathLog {
    @Id
    @NotNull
    private long id;

    private String location;

    @Column(name = "transportation_type")
    private String transportationType;

    private String dateOfArrival;

    private String dateOfDeparture;

    private SimplePackage simplePackage;

    public PackagePathLog() {
    }

    public PackagePathLog(long id, String location, String transportationType, String dateOfArrival, String dateOfDeparture, SimplePackage simplePackage) {
        this.id = id;
        this.location = location;
        this.transportationType = transportationType;
        this.dateOfArrival = dateOfArrival;
        this.dateOfDeparture = dateOfDeparture;
        this.simplePackage = simplePackage;
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

    public SimplePackage getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackage simplePackage) {
        this.simplePackage = simplePackage;
    }
}
