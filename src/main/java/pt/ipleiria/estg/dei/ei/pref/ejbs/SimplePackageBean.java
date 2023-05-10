package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SimplePackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public SimplePackage findOrFail(long id) {
        return entityManager.find(SimplePackage.class, id);
    }

    public List<SimplePackage> getAllSimplePackages() {
        return (List<SimplePackage>) entityManager.createNamedQuery("getAllSimplePackages").getResultList();
    }

    public SimplePackage create( String name, double cost, String dimension, boolean isSustainable, ResistenceType resistence, boolean isSmart) {
        // create simple package and persist it
        SimplePackage simplePackage = new SimplePackage( name, cost, dimension, isSustainable, resistence, isSmart);

        entityManager.persist(simplePackage);

        return simplePackage;
    }

    public List<ProductPackageType> getAllProductPackageTypes() {
        return (List<ProductPackageType>) entityManager.createNamedQuery("getAllProductPackageTypes").getResultList();
    }
}
