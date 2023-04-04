package pt.ipleiria.estg.dei.ei.pref.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "package_logs"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllPackageLogs",
                query = "SELECT pl FROM PackageLog pl ORDER BY pl.date" // JPQL
        )})
public class PackageLog {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String date;

    // location where log was created
    private String location;

    private String state;

    private float temperature;

    private float humidity;

    @ManyToOne
    @JoinColumn(name = "simple_package_id")
    private SimplePackage simplePackage;

    public PackageLog(String date, String location, String state, float temperature, float humidity) {
        this.date = date;
        this.location = location;
        this.state = state;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public PackageLog() {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public SimplePackage getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackage simplePackage) {
        this.simplePackage = simplePackage;
    }
}

