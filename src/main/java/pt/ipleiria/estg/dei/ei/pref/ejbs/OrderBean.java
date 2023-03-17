package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long trackingNumber, String orderDate, String client, String supplier, List<String> products, String source, String destination, OrderState state) {
        Order order = new Order(trackingNumber, orderDate, client, supplier, products, source, destination, state);

        entityManager.persist(order);
    }

    public Order find(long trackingNumber) {
        return entityManager.find(Order.class, trackingNumber);
    }

    public Order findOrFail(long trackingNumber) {
        Order order = entityManager.getReference(Order.class, trackingNumber);
        Hibernate.initialize(order);
        return order;
    }
}
