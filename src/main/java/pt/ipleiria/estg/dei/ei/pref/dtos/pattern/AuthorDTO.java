package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Author;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorDTO implements Serializable {

    private long id;
    private String type;
    private List<Observation> observations;

    public AuthorDTO() {
    }

    public AuthorDTO(long id, String type, List<Observation> observations) {
        this.id = id;
        this.type = type;
        this.observations = observations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public void addObservation(Observation observation) {
        this.observations.add(observation);
    }

    public static AuthorDTO from(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getType(),
                author.getObservations()
        );
    }

    public static List<AuthorDTO> from(List<Author> authors) {
        return authors.stream().map(AuthorDTO::from).collect(Collectors.toList());
    }
}
