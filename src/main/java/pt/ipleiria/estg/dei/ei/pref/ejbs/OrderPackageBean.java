package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderPackage findOrFail(long id) {
        return entityManager.find(OrderPackage.class, id);
    }

    public List<OrderPackage> getAllOrderPackages() {
        return (List<OrderPackage>) entityManager.createNamedQuery("getAllOrderPackages").getResultList();
    }
}
