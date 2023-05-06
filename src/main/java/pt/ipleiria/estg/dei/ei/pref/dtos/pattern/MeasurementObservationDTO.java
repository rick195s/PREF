package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.MeasurementObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Quantity;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class MeasurementDTO extends ObservationDTO implements Serializable {

    private Quantity quantity;

    public MeasurementDTO() {
    }

    public MeasurementDTO(long id, PhenomenonType phenomenonType, long observerId, String date, String details, long simplePackageId, Quantity quantity) {
        super(id, phenomenonType, observerId, date, details, simplePackageId);
        this.quantity = quantity;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public static MeasurementDTO from(MeasurementObservation measurementObservation) {
        return new MeasurementDTO(
                measurementObservation.getId(),
                measurementObservation.getPhenomenonType(),
                measurementObservation.getObserver().getId(),
                measurementObservation.getDate(),
                measurementObservation.getDetails(),
                measurementObservation.getSimplePackage().getId(),
                measurementObservation.getQuantity()
        );
    }

    public static List<MeasurementDTO> fromMeasurementList(List<MeasurementObservation> measurementObservations) {
        return measurementObservations.stream().map(MeasurementDTO::from).collect(Collectors.toList());
    }
}
