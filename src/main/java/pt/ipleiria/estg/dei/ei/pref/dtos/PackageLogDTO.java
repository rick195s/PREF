package pt.ipleiria.estg.dei.ei.pref.dtos;

import pt.ipleiria.estg.dei.ei.pref.entities.PackageLog;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PackageLogDTO implements Serializable {

    private long id;

    private String date;

    private String location;


    private float temperature;

    private float humidity;

    private SimplePackageDTO simplePackage;

    private long simplePackageId;

    public PackageLogDTO(long id, String date, String location, float temperature, float humidity, SimplePackageDTO simplePackage) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.simplePackage = simplePackage;
    }

    public PackageLogDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public SimplePackageDTO getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackageDTO simplePackage) {
        this.simplePackage = simplePackage;
    }

    public long getSimplePackageId() {
        return simplePackageId;
    }

    public void setSimplePackageId(long simplePackageId) {
        this.simplePackageId = simplePackageId;
    }

    public static PackageLogDTO from(PackageLog packageLog) {
        return new PackageLogDTO(
                packageLog.getId(),
                packageLog.getDate(),
                packageLog.getLocation(),
                packageLog.getTemperature(),
                packageLog.getHumidity(),
                SimplePackageDTO.from(packageLog.getSimplePackage())
        );
    }

    public static List<PackageLogDTO> from(List<PackageLog> packageLogs) {
        return packageLogs.stream().map(PackageLogDTO::from).collect(Collectors.toList());
    }
}
