package pt.ipleiria.estg.dei.ei.pref.entities;

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
    private String category;

    public CategoryObservation() {
    }
    public CategoryObservation(String category) {
        super();
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
