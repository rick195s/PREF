package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderPackageTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderPackageType findOrFail(String id) {
        return entityManager.find(OrderPackageType.class, id);
    }

    public List<OrderPackageType> getAllOrderPackageTypes() {
        return (List<OrderPackageType>) entityManager.createNamedQuery("getAllOrderPackageTypes").getResultList();
    }

    public List<OrderPackageType> getAllOrderPackageTypesWithId(List<Long> ids) {
        return (List<OrderPackageType>) entityManager.createNamedQuery("getAllOrderPackageTypesWithId")
                .setParameter("ids", ids)
                .getResultList();
    }
}
