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

    public AuthorDTO() {
    }

    public AuthorDTO(long id, String type) {
        this.id = id;
        this.type = type;
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

    public static AuthorDTO from(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getType()
        );
    }

    public static List<AuthorDTO> from(List<Author> authors) {
        return authors.stream().map(AuthorDTO::from).collect(Collectors.toList());
    }
}
