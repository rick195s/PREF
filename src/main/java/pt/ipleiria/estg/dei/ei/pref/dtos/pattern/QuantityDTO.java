package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Quantity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityDTO implements Serializable {

    private long id;
    private double value;

    public QuantityDTO() {
    }

    public QuantityDTO(long id, double value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public static QuantityDTO from(Quantity quantity) {
        return new QuantityDTO(
                quantity.getId(),
                quantity.getValue()
        );
    }

    public static List<QuantityDTO> from(List<Quantity> quantities) {
        return quantities.stream().map(QuantityDTO::from).collect(Collectors.toList());
    }
}
