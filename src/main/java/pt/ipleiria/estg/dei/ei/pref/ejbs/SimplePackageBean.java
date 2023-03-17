package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;

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

    public SimplePackage create(long id, String name, String dimension, List<PackageMaterialType> materialsType, PackageCategory packageCategory) {
        // create simple package and persist it
        SimplePackage simplePackage = new SimplePackage(id, name, dimension, materialsType, packageCategory);

        entityManager.persist(simplePackage);

        return simplePackage;
    }
}
