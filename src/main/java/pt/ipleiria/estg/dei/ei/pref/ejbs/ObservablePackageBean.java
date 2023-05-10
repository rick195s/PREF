package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ObservablePackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ObservablePackage<SimplePackage> findOrFail(long id) {
        return entityManager.find(ObservablePackage.class, id);
    }

    public List<ObservablePackage> getAllObservablePackages() {
        return (List<ObservablePackage>) entityManager.createNamedQuery("getAllObservablePackages").getResultList();
    }

    public SimplePackage create( String name, double cost, String dimension, boolean isSustainable, ResistenceType resistence, boolean isSmart) {
        // create simple package and persist it
        SimplePackage simplePackage = new SimplePackage( name, cost, dimension, isSustainable, resistence, isSmart);

        entityManager.persist(simplePackage);

        return simplePackage;
    }
}
