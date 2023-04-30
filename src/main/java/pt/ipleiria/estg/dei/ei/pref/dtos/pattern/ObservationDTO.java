package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Author;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ObservationDTO implements Serializable {
    private long id;
    private PhenomenonType phenomenonType;
    private Author author;
    private String date;
    private long simplePackageId;

    public ObservationDTO() {
    }
    public ObservationDTO(long id, PhenomenonType phenomenonType, Author author, String date, long simplePackageId) {
        this.id = id;
        this.phenomenonType = phenomenonType;
        this.author = author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
                observation.getAuthor(),
                observation.getDate(),
                observation.getSimplePackage().getId()
        );
    }

    public static List<ObservationDTO> from(List<Observation> observations) {
        return observations.stream().map(ObservationDTO::from).collect(Collectors.toList());
    }
}
