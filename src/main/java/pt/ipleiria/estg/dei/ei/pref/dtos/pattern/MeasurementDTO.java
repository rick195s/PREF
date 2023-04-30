package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observer;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Measurement;
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

    public static MeasurementDTO from(Measurement measurement) {
        return new MeasurementDTO(
                measurement.getId(),
                measurement.getPhenomenonType(),
                measurement.getObserver().getId(),
                measurement.getDate(),
                measurement.getDetails(),
                measurement.getSimplePackage().getId(),
                measurement.getQuantity()
        );
    }

    public static List<MeasurementDTO> fromMeasurementList(List<Measurement> measurements) {
        return measurements.stream().map(MeasurementDTO::from).collect(Collectors.toList());
    }
}
