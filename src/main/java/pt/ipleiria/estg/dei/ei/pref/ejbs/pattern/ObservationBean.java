package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.ObservablePackageBean;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ObservationBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ObserverBean observerBean;

    @EJB
    private ObservablePackageBean observablePackageBean;

    public Observation findOrFail(long id) {
        return entityManager.find(Observation.class, id);
    }

    public List<Observation> getAllObservations() {
        return (List<Observation>) entityManager.createNamedQuery("getAllObservations").getResultList();
    }

    public Observation create(PhenomenonType phenomenonType, long observerId, String date, String details, long observablePackageId, String value) {

        Observer observer = observerBean.findOrFail(observerId);

        ObservablePackage observablePackage = observablePackageBean.findOrFail(observablePackageId);

        // validate json
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(details);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON details");
        }

        if (phenomenonType.isMeasurement()) {
            double measurementValue = Double.parseDouble(value);
            Quantity quantity = new Quantity(measurementValue);
            entityManager.persist(quantity);
            MeasurementObservation measurementObservation = new MeasurementObservation(phenomenonType, observer, date, details, observablePackage, quantity);
            entityManager.persist(measurementObservation);
            return measurementObservation;
        }
            Category category = new Category(value);
            entityManager.persist(category);
            CategoryObservation categoryObservation = new CategoryObservation(phenomenonType, observer, date, details, observablePackage, category);
            entityManager.persist(categoryObservation);
            return categoryObservation;
    }

    public List<Observation> getAllPackageObservations(long observablePackageId) {
        return (List<Observation>) entityManager.createNamedQuery("getAllPackageObservations").setParameter("observablePackageId", observablePackageId).getResultList();
    }
}
