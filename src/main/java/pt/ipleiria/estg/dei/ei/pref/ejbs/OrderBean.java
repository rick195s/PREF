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

    @EJB
    private SimplePackageBean simplePackageBean;

    public void create(long trackingNumber, String date, List<Product> products, String source, String destination, OrderState state) {
        Order order = new Order(trackingNumber, date, source, destination, state);

        List<OrderLine> orderLines = products.stream().map(product ->
                new OrderLine(1, product, order)).collect(Collectors.toList());

        orderLines.forEach(order::addOrderLine);

        entityManager.persist(order);

        for (OrderLine orderLine : orderLines) {
            entityManager.persist(orderLine);
        }
    }

    public Order find(long trackingNumber) {
        return entityManager.find(Order.class, trackingNumber);
    }

    public Order findOrFail(long trackingNumber) {
        Order order = entityManager.getReference(Order.class, trackingNumber);
        Hibernate.initialize(order);
        Hibernate.initialize(order.getOrderLines());
        return order;
    }

    public Order dispatchOrder(long trackingNumber, long simplePackageId) {
        Order order = find(trackingNumber);
        if (order == null) {
            throw new MyEntityNotFoundException("Order not found");
        }
        if (order.getState() != OrderState.PENDING) {
            throw new MyIllegalArgumentException("Order is not waiting for dispatch");
        }
        SimplePackage simplePackage = simplePackageBean.find(simplePackageId);
        if (simplePackage == null) {
            throw new MyEntityNotFoundException("Package not found");
        }
        order.setSimplePackage(simplePackage);
        simplePackage.addOrder(order);
        order.setState(OrderState.IN_TRANSIT);

        Hibernate.initialize(order.getOrderLines());

        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orders=  (List<Order>) entityManager.createNamedQuery("getAllOrders").getResultList();
        for (Order order : orders) {
            Hibernate.initialize(order.getOrderLines());
        }
        return orders;
    }
}
