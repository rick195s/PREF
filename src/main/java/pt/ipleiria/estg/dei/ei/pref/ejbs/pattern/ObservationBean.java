package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import pt.ipleiria.estg.dei.ei.pref.ejbs.SimplePackageBean;
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
    private SimplePackageBean simplePackageBean;

    public Observation findOrFail(long id) {
        return entityManager.find(Observation.class, id);
    }

    public List<Observation> getAllObservations() {
        return (List<Observation>) entityManager.createNamedQuery("getAllObservations").getResultList();
    }

    public Observation create(PhenomenonType phenomenonType, String observerString, String date, long simplePackageId, String value) {

        Observer observer = new Observer(observerString);

        entityManager.persist(observer);

        SimplePackage simplePackage = simplePackageBean.findOrFail(simplePackageId);

        if (phenomenonType.isMeasurement()) {
            double measurementValue = Double.parseDouble(value);
            Quantity quantity = new Quantity(measurementValue);
            entityManager.persist(quantity);
            Measurement measurement = new Measurement(phenomenonType, observer, date, simplePackage, quantity);
            entityManager.persist(measurement);
            return measurement;
        }
            Category category = new Category(value);
            entityManager.persist(category);
            CategoryObservation categoryObservation = new CategoryObservation(phenomenonType, observer, date, simplePackage, category);
            entityManager.persist(categoryObservation);
            return categoryObservation;
    }

    public List<Observation> getAllPackageObservations(long simplePackageId) {
        return (List<Observation>) entityManager.createNamedQuery("getAllPackageObservations").setParameter("simplePackageId", simplePackageId).getResultList();
    }
}
