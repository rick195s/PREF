package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductPackageTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ProductPackageType findOrFail(long id) {
        return entityManager.find(ProductPackageType.class, id);
    }

    public List<ProductPackageType> getAllOrderPackageTypes() {
        return (List<ProductPackageType>) entityManager.createNamedQuery("getAllProductPackageTypes").getResultList();
    }
}
