package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderPackage findOrFail(long code) {
        return entityManager.find(OrderPackage.class, code);
    }

    public List<OrderPackage> getAllOrderPackages() {
        return (List<OrderPackage>) entityManager.createNamedQuery("getAllOrderPackages").getResultList();
    }
}
