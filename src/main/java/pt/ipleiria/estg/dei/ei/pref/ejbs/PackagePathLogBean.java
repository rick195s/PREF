package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.PackagePathLog;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PackagePathLogBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    public PackagePathLog create(long id, String location, String transportationType, String dateOfArrival, String dateOfDeparture, long orderTrackingNumber ) {
        Order order = orderBean.find(orderTrackingNumber);
        if (order == null) {
            throw new MyEntityNotFoundException("Order not found");
        }

        PackagePathLog packagePathLog = new PackagePathLog(id, location, transportationType, dateOfArrival, dateOfDeparture, order);
        entityManager.persist(packagePathLog);

        return packagePathLog;
    }
}
