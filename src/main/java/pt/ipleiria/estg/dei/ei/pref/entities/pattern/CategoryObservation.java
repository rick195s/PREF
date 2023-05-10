package pt.ipleiria.estg.dei.ei.pref.entities.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public CategoryObservation() {
    }
    public CategoryObservation(PhenomenonType phenomenonType, Observer observer, String date, String details, SimplePackage simplePackage, Category category) {
        super(phenomenonType, observer, date, details, simplePackage);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}