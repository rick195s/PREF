package pt.ipleiria.estg.dei.ei.pref.dtos.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observer;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ObserverDTO implements Serializable {

    private long id;
    private String type;

    public ObserverDTO() {
    }

    public ObserverDTO(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ObserverDTO from(Observer observer) {
        return new ObserverDTO(
                observer.getId(),
                observer.getType()
        );
    }

    public static List<ObserverDTO> from(List<Observer> observers) {
        return observers.stream().map(ObserverDTO::from).collect(Collectors.toList());
    }
}
