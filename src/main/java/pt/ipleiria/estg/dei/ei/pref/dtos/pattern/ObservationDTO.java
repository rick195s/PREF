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
    private List<SimplePackage> simplePackages;

    public ObservationDTO() {
    }

    public ObservationDTO(long id, PhenomenonType phenomenonType, Author author) {
        this.id = id;
        this.phenomenonType = phenomenonType;
        this.author = author;
    }

    public ObservationDTO(long id, PhenomenonType phenomenonType, Author author, List<SimplePackage> simplePackages) {
        this.id = id;
        this.phenomenonType = phenomenonType;
        this.author = author;
        this.simplePackages = simplePackages;
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

    public List<SimplePackage> getSimplePackages() {
        return simplePackages;
    }

    public void setSimplePackages(List<SimplePackage> simplePackages) {
        this.simplePackages = simplePackages;
    }

    public void addSimplePackage(SimplePackage simplePackage) {
        this.simplePackages.add(simplePackage);
    }

    public static ObservationDTO from(Observation observation) {
        return new ObservationDTO(
                observation.getId(),
                observation.getPhenomenonType(),
                observation.getAuthor(),
                observation.getSimplePackages()
        );
    }

    public static List<ObservationDTO> from(List<Observation> observations) {
        return observations.stream().map(ObservationDTO::from).collect(Collectors.toList());
    }
}
