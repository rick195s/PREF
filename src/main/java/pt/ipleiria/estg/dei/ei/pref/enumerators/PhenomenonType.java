package pt.ipleiria.estg.dei.ei.pref.enumerators;

import java.io.Serializable;

public enum PhenomenonType {
    TEMPERATURE(true),
    HUMIDITY(true),
    QR_CODE(false),
    LOCATION(false);

    private final boolean isMeasurement;

    PhenomenonType(boolean isMeasurement) {
        this.isMeasurement = isMeasurement;
    }

    public boolean isMeasurement() {
        return isMeasurement;
    }

    public boolean isCategoryObservation() {
        return !isMeasurement;
    }
}
