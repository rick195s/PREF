package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(
        name = "category_observations"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllCategoryObservations",
                query = "SELECT c FROM CategoryObservation c ORDER BY c.id" // JPQL
        )})
public class CategoryObservation extends Observation implements Serializable {
    private Category category;

    public CategoryObservation() {
    }
    public CategoryObservation(PhenomenonType phenomenonType, Author author, Category category) {
        super(phenomenonType, author);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
