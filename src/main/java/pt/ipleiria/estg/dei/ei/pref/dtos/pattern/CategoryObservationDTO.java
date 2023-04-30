package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryObservationDTO extends ObservationDTO implements Serializable {

    private Category category;

    public CategoryObservationDTO() {
    }

    public CategoryObservationDTO(long id, PhenomenonType phenomenonType, Observer observer, String date, long simplePackageId, Category category) {
        super(id, phenomenonType, observer, date, simplePackageId);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static CategoryObservationDTO from(CategoryObservation categoryObservation) {
        return new CategoryObservationDTO(
                categoryObservation.getId(),
                categoryObservation.getPhenomenonType(),
                categoryObservation.getObserver(),
                categoryObservation.getDate(),
                categoryObservation.getSimplePackage().getId(),
                categoryObservation.getCategory()
        );
    }

    public static List<CategoryObservationDTO> fromCategoryObservationList(List<CategoryObservation> categoryObservations) {
        return categoryObservations.stream().map(CategoryObservationDTO::from).collect(Collectors.toList());
    }
}
