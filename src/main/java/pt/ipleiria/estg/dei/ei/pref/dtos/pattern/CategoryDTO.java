package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Category;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO implements Serializable {

    private long id;
    private String value;

    public CategoryDTO() {
    }

    public CategoryDTO(long id, String value) {
        this.id = id;
        this.value = value;
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

    public static CategoryDTO from(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getValue()
        );
    }

    public static List<CategoryDTO> from(List<Category> categories) {
        return categories.stream().map(CategoryDTO::from).collect(Collectors.toList());
    }
}
