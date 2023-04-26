package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderPackageBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.SimplePackageBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.*;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelationPK;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

@Stateless
public class ObservationBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SimplePackageBean simplePackageBean;

    public Observation findOrFail(long id) {

        Observation observation = entityManager.find(Observation.class, id);

        Hibernate.initialize(observation.getPhenomenonType());
        Hibernate.initialize(observation.getAuthor());
        Hibernate.initialize(observation.getSimplePackage());

        return observation;
    }

    public List<Observation> getAllObservations() {
        return (List<Observation>) entityManager.createNamedQuery("getAllObservations").getResultList();
    }

    public Observation create(PhenomenonType phenomenonType, String authorString, long simplePackageId, String value) {

        Author author = new Author(authorString);

        entityManager.persist(author);

        SimplePackage simplePackage = simplePackageBean.findOrFail(simplePackageId);

        if (phenomenonType.isMeasurement()) {
            double measurementValue = Double.parseDouble(value);
            Quantity quantity = new Quantity(measurementValue);
            entityManager.persist(quantity);
            Measurement measurement = new Measurement(phenomenonType, author, simplePackage, quantity);
            entityManager.persist(measurement);
            return measurement;
        }
            Category category = new Category(value);
            entityManager.persist(category);
            CategoryObservation categoryObservation = new CategoryObservation(phenomenonType, author, simplePackage, category);
            entityManager.persist(categoryObservation);
            return categoryObservation;
    }
}
