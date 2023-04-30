package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observer;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ObservationDTO implements Serializable {
    private long id;
    private PhenomenonType phenomenonType;
    private Observer observer;
    private String date;
    private long simplePackageId;

    public ObservationDTO() {
    }
    public ObservationDTO(long id, PhenomenonType phenomenonType, Observer observer, String date, long simplePackageId) {
        this.id = id;
        this.phenomenonType = phenomenonType;
        this.observer = observer;
        this.date = date;
        this.simplePackageId = simplePackageId;
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

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getSimplePackageId() {
        return simplePackageId;
    }

    public void setSimplePackageId(long simplePackageId) {
        this.simplePackageId = simplePackageId;
    }

    public static ObservationDTO from(Observation observation) {
        return new ObservationDTO(
                observation.getId(),
                observation.getPhenomenonType(),
                observation.getObserver(),
                observation.getDate(),
                observation.getSimplePackage().getId()
        );
    }

    public static List<ObservationDTO> from(List<Observation> observations) {
        return observations.stream().map(ObservationDTO::from).collect(Collectors.toList());
    }
}
