package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<Observation> getAllObservations(int offset, int limit/*, String sortField, boolean isAscending, String searchDate, String searchObservablePackage, String searchObserver, String searchPhenomenon, String searchValue*/) {
       /* String queryString = "SELECT o FROM Observation o";
        boolean hasWhereClause = false;
        Long observerId = null;
        Long observablePackageId = null;

        if (isPhenomenonType(sortField)) {
            PhenomenonType phenomenonType = PhenomenonType.valueOf(sortField);
            queryString += " WHERE o.phenomenonType = :phenomenonType";
            hasWhereClause = true;
        }

        if (searchDate != null && !searchDate.isEmpty() && isDateValid(searchDate)) {
            queryString += hasWhereClause ? " AND DATE(o.date) = DATE(:searchDate)" : " WHERE DATE(o.date) = DATE(:searchDate)";
            hasWhereClause = true;
        }

        if (searchObservablePackage != null && !searchObservablePackage.isEmpty()) {
            try {
                observablePackageId = Long.parseLong(searchObservablePackage);
                queryString += hasWhereClause ? " AND o.observablePackage.id = :searchObservablePackageId" : " WHERE o.observablePackage.id = :searchObservablePackageId";
                hasWhereClause = true;
            } catch (NumberFormatException e) {
                // Trate aqui a exceção se o valor não puder ser convertido para Long
            }
        }

        if (searchObserver != null && !searchObserver.isEmpty()) {
            try {
                observerId = Long.parseLong(searchObserver);
                queryString += hasWhereClause ? " AND o.observer.id = :searchObserverId" : " WHERE o.observer.id = :searchObserverId";
                hasWhereClause = true;
            } catch (NumberFormatException e) {
                // Trate aqui a exceção se o valor não puder ser convertido para Long
            }
        }

        if (searchPhenomenon != null && !searchPhenomenon.isEmpty()) {
            PhenomenonType searchPhenomenonEnum = PhenomenonType.valueOf(searchPhenomenon);
            queryString += hasWhereClause ? " AND o.phenomenonType = :searchPhenomenonEnum" : " WHERE o.phenomenonType = :searchPhenomenonEnum";
            hasWhereClause = true;

            if (searchValue != null && !searchValue.isEmpty()) {
                try {
                    if (searchPhenomenonEnum.isMeasurement()) {
                        double value;
                        try {
                            value = Double.parseDouble(searchValue);
                        } catch (NumberFormatException e) {
                            // Lida com a exceção se o valor não puder ser convertido para double
                            // Por exemplo, você pode definir um valor padrão ou lançar uma exceção personalizada
                            value = 0.0; // Valor padrão
                        }
                        queryString += " AND CAST(o.value AS double) = :value";
                    } else {
                        queryString += " AND LOWER(o.value) = LOWER(:value)";
                    }
                } catch (Exception e) {
                    // Trate outras exceções aqui, se necessário
                }
            }
        }

        if (isPhenomenonType(sortField)) {
            queryString += " ORDER BY CAST(o.value AS double)" + (isAscending ? " ASC" : " DESC");
        } else {
            queryString += " ORDER BY o." + sortField + (isAscending ? " ASC" : " DESC");
        }

        TypedQuery<Observation> query = entityManager.createQuery(queryString, Observation.class);

        if (isPhenomenonType(sortField)) {
            PhenomenonType phenomenonType = PhenomenonType.valueOf(sortField);
            query.setParameter("phenomenonType", phenomenonType);
        }

        if (searchDate != null && !searchDate.isEmpty() && isDateValid(searchDate)) {
            query.setParameter("searchDate", searchDate);
        }
        if (searchObservablePackage != null && !searchObservablePackage.isEmpty()) {
            query.setParameter("searchObservablePackageId", observablePackageId);
        }
        if (searchObserver != null && !searchObserver.isEmpty()) {
            query.setParameter("searchObserverId", observerId);
        }
        if (searchPhenomenon != null && !searchPhenomenon.isEmpty()) {
            PhenomenonType searchPhenomenonEnum = PhenomenonType.valueOf(searchPhenomenon);
            query.setParameter("searchPhenomenonEnum", searchPhenomenonEnum);
            if (searchValue != null && !searchValue.isEmpty()) {
                if (searchPhenomenonEnum.isMeasurement()) {
                    double value = Double.parseDouble(searchValue);
                    query.setParameter("value", value);
                } else {
                    query.setParameter("value", searchValue);
                }
            }
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<Observation> observations = query.getResultList();

        return observations;*/
        List<Observation> observations = (List<Observation>) entityManager.createNamedQuery("getAllObservations")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        return observations;
    }


    public boolean isDateValid(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false); // Define a análise estrita das datas

        try {
            Date parsedDate = dateFormat.parse(date);
            // Se a data for analisada com sucesso, significa que é uma data válida
            return true;
        } catch (Exception e) {
            // A data não é válida ou não pode ser analisada
            return false;
        }
    }

    private boolean isPhenomenonType(String sortField) {
        return !(sortField.equals("date") || sortField.equals("observer") || sortField.equals("observablePackage"));
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
