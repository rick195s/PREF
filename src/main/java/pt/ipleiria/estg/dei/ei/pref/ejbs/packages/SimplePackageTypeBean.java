package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackageType;

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

    public SimplePackageType create(String id, double cost, boolean isSustainable, boolean isSmart, String shape, float width, float height, float length) {
        // create simple package and persist it
        SimplePackageType simplePackageType = new SimplePackageType(  id,  cost,  isSustainable,  isSmart,  shape,  width,  height,  length);

        entityManager.persist(simplePackageType);

        return simplePackageType;
    }

    public List<ProductPackageType> getAllProductPackageTypes() {
        return (List<ProductPackageType>) entityManager.createNamedQuery("getAllProductPackageTypes").getResultList();
    }
}
