package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderPackageTypeBean orderPackageTypeBean;

    @EJB
    private OrderBean orderBean;

    public List<OrderPackage> getAllOrderPackages() {
        return (List<OrderPackage>) entityManager.createNamedQuery("getAllOrderPackages").getResultList();
    }

    public OrderPackage findOrFail(long id) {
        return entityManager.find(OrderPackage.class, id);
    }

    public OrderPackage create(long orderPackageTypeId, long orderTrackingNumber) {
        OrderPackageType orderPackageType = orderPackageTypeBean.findOrFail(orderPackageTypeId);

        Order order = orderBean.findOrFail(orderTrackingNumber);

        // create simple package and persist it
        OrderPackage orderPackage = new OrderPackage(order, orderPackageType);

        entityManager.persist(orderPackage);

        return orderPackage;
    }
}
