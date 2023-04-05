package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String date, List<Product> products, String source, String destination, OrderState state, float weight, String carrier, List<String> shippingMethods) throws MyIllegalArgumentException, MyEntityNotFoundException {
        Order order = new Order(date, source, destination, state, weight, carrier, shippingMethods);

        List<OrderLine> orderLines = products.stream().map(product ->
                new OrderLine(1, product.getPrice(),product, order)).collect(Collectors.toList());

        orderLines.forEach(order::addOrderLine);

        entityManager.persist(order);

        for (OrderLine orderLine : orderLines) {
            entityManager.persist(orderLine);
        }
    }


    public Order findOrFail(long trackingNumber) {
        Order order = entityManager.getReference(Order.class, trackingNumber);
        Hibernate.initialize(order);
        Hibernate.initialize(order.getOrderLines());
        Hibernate.initialize(order.getShippingMethods());
        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orders=  (List<Order>) entityManager.createNamedQuery("getAllOrders").getResultList();
        for (Order order : orders) {
            Hibernate.initialize(order.getOrderLines());
            Hibernate.initialize(order.getShippingMethods());
        }
        return orders;
    }
}
