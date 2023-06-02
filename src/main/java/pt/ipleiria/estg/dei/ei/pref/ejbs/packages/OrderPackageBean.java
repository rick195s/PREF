package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
        return (List<OrderPackage>) entityManager.createNamedQuery("getAllOrderPackages")
                .getResultList();
    }

    public List<OrderPackage> getAllSmartOrderPackages() {
        return (List<OrderPackage>) entityManager.createNamedQuery("getAllSmartOrderPackages")
                .getResultList();
    }

    public OrderPackage findOrFail(long id) {
        return entityManager.find(OrderPackage.class, id);
    }

    public OrderPackage create(long orderPackageTypeId, String orderId) {
        OrderPackageType orderPackageType = orderPackageTypeBean.findOrFail(orderPackageTypeId);
        if (orderPackageType == null) {
            throw new EntityNotFoundException("Order package type not found");
        }

        Order order = orderBean.findOrFail(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }

        // create simple package and persist it
        OrderPackage orderPackage = new OrderPackage(order, orderPackageType);

        entityManager.persist(orderPackage);

        return orderPackage;
    }
}
