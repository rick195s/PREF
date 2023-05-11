package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SimplePackageTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public SimplePackageType findOrFail(long id) {
        return entityManager.find(SimplePackageType.class, id);
    }

    public List<SimplePackageType> getAllSimplePackageTypes() {
        return (List<SimplePackageType>) entityManager.createNamedQuery("getAllSimplePackageTypes").getResultList();
    }

    public SimplePackageType create(String name, double cost, String dimension, boolean isSustainable, ResistenceType resistence, boolean isSmart) {
        // create simple package and persist it
        SimplePackageType simplePackageType = new SimplePackageType( name, cost, dimension, isSustainable, resistence, isSmart);

        entityManager.persist(simplePackageType);

        return simplePackageType;
    }

    public List<ProductPackageType> getAllProductPackageTypes() {
        return (List<ProductPackageType>) entityManager.createNamedQuery("getAllProductPackageTypes").getResultList();
    }
}
