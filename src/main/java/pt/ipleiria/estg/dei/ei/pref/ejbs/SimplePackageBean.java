package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SimplePackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public SimplePackage find(long id) {
        return entityManager.find(SimplePackage.class, id);
    }

    public SimplePackage findOrFail(long code) {
        SimplePackage simplePackage = entityManager.getReference(SimplePackage.class, code);
        Hibernate.initialize(simplePackage);
        return simplePackage;
    }

    public List<SimplePackage> getAllSimplePackages() {
        return (List<SimplePackage>) entityManager.createNamedQuery("getAllSimplePackages").getResultList();
    }

    public SimplePackage create(long id, double cost, String dimension, List<PackageMaterialType> materialsType, PackageType type, PackageCategory category, boolean is_sustainable, ResistenceType resistence, boolean is_smart) {
        // create simple package and persist it
        SimplePackage simplePackage = new SimplePackage(id, cost,dimension, materialsType, type, category, is_sustainable, resistence, is_smart);

        entityManager.persist(simplePackage);

        return simplePackage;
    }
}
