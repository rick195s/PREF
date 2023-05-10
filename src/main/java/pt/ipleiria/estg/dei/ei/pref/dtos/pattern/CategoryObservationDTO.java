package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryObservationDTO extends ObservationDTO implements Serializable {

    private String category;

    public CategoryObservationDTO() {
    }

    public CategoryObservationDTO(long id, PhenomenonType phenomenonType, long observerId, String date, String details, long observablePackageId, String category) {
        super(id, phenomenonType, observerId, date, details, observablePackageId);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static CategoryObservationDTO from(CategoryObservation categoryObservation) {
        return new CategoryObservationDTO(
                categoryObservation.getId(),
                categoryObservation.getPhenomenonType(),
                categoryObservation.getObserver().getId(),
                categoryObservation.getDate(),
                categoryObservation.getDetails(),
                categoryObservation.getObservablePackage().getId(),
                categoryObservation.getCategory().getValue()
        );
    }

    public static List<CategoryObservationDTO> fromCategoryObservationList(List<CategoryObservation> categoryObservations) {
        return categoryObservations.stream().map(CategoryObservationDTO::from).collect(Collectors.toList());
    }
}
