package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ObservationBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private ObserverBean observerBean;

    public Observation findOrFail(long id) {
        return entityManager.find(Observation.class, id);
    }

    public List<Observation> getAllObservations() {
        return (List<Observation>) entityManager.createNamedQuery("getAllObservations").getResultList();
    }

    @Transactional
    public void createMultipleObservations(List<Observation> observations) {
        for (Observation observation : observations) {
            create(observation.getPhenomenonType(), observation.getObserver().getId(), observation.getDate(), observation.getDetails(), observation.getObservablePackage().getId(), observation.getValue());
        }
    }

    public Observation create(PhenomenonType phenomenonType, long observerId, String date, String details, long observablePackageId, String value) {

        Observer observer = observerBean.findOrFail(observerId);

        ObservablePackage observablePackage = entityManager.find(ObservablePackage.class, observablePackageId);
        if (observablePackage == null) {
            throw new EntityNotFoundException("Observable Package not found");
        }

        if(!observablePackage.getSimplePackageType().isSmart()){
            throw new IllegalArgumentException("Package is not smart");
        }

        // validate json
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(details);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON details");
        }

        if (phenomenonType.isMeasurement()) {
            return createMeasurementObservation(phenomenonType, observer, date, details, observablePackage, value);
        }

        return createCategoryObservation(phenomenonType, observer, date, details, observablePackage, value);
    }

    private MeasurementObservation createMeasurementObservation(PhenomenonType phenomenonType, Observer observer, String date, String details, ObservablePackage observablePackage, String value) {
        double measurementValue = Double.parseDouble(value);
        Quantity quantity = null;
        try {
            quantity = (Quantity) entityManager.createNamedQuery("getQuantityByValue").setParameter("value", measurementValue).getSingleResult();
        } catch (NoResultException e) {
            quantity = new Quantity(measurementValue);
            entityManager.persist(quantity);
        }
        MeasurementObservation measurementObservation = new MeasurementObservation(phenomenonType, observer, date, details, observablePackage, quantity);
        entityManager.persist(measurementObservation);
        return measurementObservation;
    }

    private CategoryObservation createCategoryObservation(PhenomenonType phenomenonType, Observer observer, String date, String details, ObservablePackage observablePackage, String value) {
        Category category = null;
        try {
            category = (Category) entityManager.createNamedQuery("getCategoryByValue").setParameter("value", value).getSingleResult();
        } catch (NoResultException e) {
            category = new Category(value);
            entityManager.persist(category);
        }
        CategoryObservation categoryObservation = new CategoryObservation(phenomenonType, observer, date, details, observablePackage, category);
        entityManager.persist(categoryObservation);
        return categoryObservation;
    }

    public List<Observation> getAllPackagesObservations(List<Long> observablePackagesIds) {
        return (List<Observation>) entityManager.createNamedQuery("getAllPackagesObservations")
                .setParameter("observablePackageIds", observablePackagesIds)
                .getResultList();
    }
}
