package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderPackageTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderPackageType findOrFail(long code) {
        return entityManager.find(OrderPackageType.class, code);
    }

    public List<OrderPackageType> getAllOrderPackageTypes() {
        return (List<OrderPackageType>) entityManager.createNamedQuery("getAllOrderPackageTypes").getResultList();
    }
}