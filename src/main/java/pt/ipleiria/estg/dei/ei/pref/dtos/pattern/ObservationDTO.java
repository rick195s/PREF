package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ObservationDTO implements Serializable {
    private long id;
    private PhenomenonType phenomenonType;
    private long observerId;
    private String date;
    private String details;
    private long observablePackageId;
    private String value;

    public ObservationDTO() {
    }
    public ObservationDTO(long id, PhenomenonType phenomenonType, long observerId, String date, String details, long observablePackageId, String value) {
        this.id = id;
        this.phenomenonType = phenomenonType;
        this.observerId = observerId;
        this.date = date;
        this.details = details;
        this.observablePackageId = observablePackageId;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PhenomenonType getPhenomenonType() {
        return phenomenonType;
    }

    public void setPhenomenonType(PhenomenonType phenomenonType) {
        this.phenomenonType = phenomenonType;
    }

    public long getObserverId() {
        return observerId;
    }

    public void setObserverId(long observerId) {
        this.observerId = observerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getObservablePackageId() {
        return observablePackageId;
    }

    public void setObservablePackageId(long observablePackageId) {
        this.observablePackageId = observablePackageId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ObservationDTO from(Observation observation) {
        return new ObservationDTO(
                observation.getId(),
                observation.getPhenomenonType(),
                observation.getObserver().getId(),
                observation.getDate(),
                observation.getDetails(),
                observation.getObservablePackage().getId(),
                observation.getValue()
        );
    }

    public static List<ObservationDTO> from(List<Observation> observations) {
        return observations.stream().map(ObservationDTO::from).collect(Collectors.toList());
    }
}
