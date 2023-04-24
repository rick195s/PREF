package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Author;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Category;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.CategoryObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO implements Serializable {

    private long id;
    private String value;
    private List<CategoryObservation> categoryObservations;

    public CategoryDTO() {
    }

    public CategoryDTO(long id, String value, List<CategoryObservation> categoryObservations) {
        this.id = id;
        this.value = value;
        this.categoryObservations = categoryObservations;
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

    public static CategoryDTO from(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getValue(),
                category.getCategoryObservations()
        );
    }

    public static List<CategoryDTO> from(List<Category> categories) {
        return categories.stream().map(CategoryDTO::from).collect(Collectors.toList());
    }
}
