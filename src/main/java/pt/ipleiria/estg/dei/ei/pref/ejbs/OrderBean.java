package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long trackingNumber, String orderDate, String client, String supplier, List<String> products, String source, String destination, String state) {
        Order order = new Order(trackingNumber, orderDate, client, supplier, products, source, destination, state);

        entityManager.persist(order);
    }

    
}
