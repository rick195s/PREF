package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "categories"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllCategories",
                query = "SELECT c FROM Category c ORDER BY c.id" // JPQL
        ),
        @NamedQuery(
                name = "getCategoryByValue",
                query = "SELECT c FROM Category c WHERE c.value = :value" // JPQL
        )})
public class Category implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String value;
    @OneToMany(mappedBy = "category")
    private List<CategoryObservation> categoryObservations;

    public Category() {
        categoryObservations = new LinkedList<>();
    }
    public Category(String value) {
        this.value = value;
        categoryObservations = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<CategoryObservation> getCategoryObservations() {
        return categoryObservations;
    }

    public void addCategoryObservation(CategoryObservation categoryObservation) {
        categoryObservations.add(categoryObservation);
    }
}
