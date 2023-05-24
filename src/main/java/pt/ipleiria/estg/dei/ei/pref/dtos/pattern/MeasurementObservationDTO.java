package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.MeasurementObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Quantity;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class MeasurementObservationDTO extends ObservationDTO implements Serializable {

    private double quantity;

    public MeasurementObservationDTO() {
    }

    public MeasurementObservationDTO(long id, PhenomenonType phenomenonType, long observerId, String date, String details, long observablePackageId, double quantity) {
        super(id, phenomenonType, observerId, date, details, observablePackageId, String.valueOf(quantity));
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public static MeasurementObservationDTO from(MeasurementObservation measurementObservation) {
        return new MeasurementObservationDTO(
                measurementObservation.getId(),
                measurementObservation.getPhenomenonType(),
                measurementObservation.getObserver().getId(),
                measurementObservation.getDate(),
                measurementObservation.getDetails(),
                measurementObservation.getObservablePackage().getId(),
                measurementObservation.getQuantity().getValue()
        );
    }

    public static List<MeasurementObservationDTO> fromMeasurementList(List<MeasurementObservation> measurementObservations) {
        return measurementObservations.stream().map(MeasurementObservationDTO::from).collect(Collectors.toList());
    }
}
