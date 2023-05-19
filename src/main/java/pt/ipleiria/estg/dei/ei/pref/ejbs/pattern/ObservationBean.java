package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
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

    public List<Observation> getAllObservations(int offset, int limit, String sortField, boolean isAscending) {
        String queryString;
        if (!sortField.equals("date") && !sortField.equals("observer") && !sortField.equals("observablePackage")) {
            PhenomenonType phenomenonType = PhenomenonType.valueOf(sortField);
            queryString = "SELECT o FROM Observation o WHERE o.phenomenonType = :phenomenonType ORDER BY CAST(o.value as double)";

        } else {
            queryString ="SELECT o FROM Observation o ORDER BY o." + sortField;
        }
        if (!isAscending) {
            queryString += " DESC";
        }

        TypedQuery<Observation> query = entityManager.createQuery(queryString, Observation.class);
        if (!sortField.equals("date") && !sortField.equals("observer") && !sortField.equals("observablePackage")) {
            PhenomenonType phenomenonType = PhenomenonType.valueOf(sortField);
            query.setParameter("phenomenonType", phenomenonType);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<Observation> observations = query.getResultList();

        return observations;
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

        if (!observablePackage.getSimplePackageType().isSmart()) {
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

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Observation.class.getSimpleName(), Long.class).getSingleResult();
    }
}
