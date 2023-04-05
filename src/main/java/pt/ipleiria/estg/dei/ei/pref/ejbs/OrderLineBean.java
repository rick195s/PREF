package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderLineBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private SimplePackageBean simplePackageBean;

    public OrderLine find(long id) {
        return entityManager.find(OrderLine.class, id);
    }

    public OrderLine dispatchOrder(long id, long simplePackageId) {
        OrderLine orderLine = find(id);
        if (orderLine == null) {
            throw new MyEntityNotFoundException("OrderLine not found");
        }
        if (orderLine.getOrder().getState() != OrderState.PENDING) {
            throw new MyIllegalArgumentException("Order is not waiting for dispatch");
        }
        SimplePackage simplePackage = simplePackageBean.find(simplePackageId);
        if (simplePackage == null) {
            throw new MyEntityNotFoundException("Package not found");
        }
        orderLine.setSimplePackage(simplePackage);
        // simplePackage.addOrderLine(orderLine);

        return orderLine;
    }
}
