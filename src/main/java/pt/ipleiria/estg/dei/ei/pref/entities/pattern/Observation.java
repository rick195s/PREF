package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Author;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "observations"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservations",
                query = "SELECT o FROM Observation o ORDER BY o.id" // JPQL
        )})
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Observation implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private PhenomenonType phenomenonType;

    @NotNull
    private Author author;

    @ManyToOne
    @JoinColumn(name = "simple_package_id")
    private SimplePackage simplePackage;

    public Observation() {

    }

    public Observation(PhenomenonType phenomenonType, Author author, SimplePackage simplePackage) {
        this();
        this.phenomenonType = phenomenonType;
        this.author = author;
        this.simplePackage = simplePackage;
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

    public SimplePackage getSimplePackage() {
        return simplePackage;
    }

    public void setSimplePackage(SimplePackage simplePackage) {
        this.simplePackage = simplePackage;
    }
}
