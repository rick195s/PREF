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

        for (OrderLine orderLine : order.getOrderLines()) {
            Hibernate.initialize(orderLine.getProduct().getProductPackageRelations());
        }
        return order;
    }

    public List<Order> getAllOrders(int offset, int limit) {
        List<Order> orders=  (List<Order>) entityManager.createNamedQuery("getAllOrders")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        for (Order order : orders) {
            Hibernate.initialize(order.getOrderLines());
            Hibernate.initialize(order.getShippingMethods());
            for (OrderLine orderLine : order.getOrderLines()) {
                Hibernate.initialize(orderLine.getProduct().getProductPackageRelations());
            }
        }
        return orders;
    }

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Order.class.getSimpleName(), Long.class).getSingleResult();
    }


    public Order dispatchOrder(long id, long simplePackageId) {
          Order order = findOrFail(id);
          return order;
          /*
        if (order == null) {
            throw new MyEntityNotFoundException("OrderLine not found");
        }
        if (order.getState() != OrderState.PENDING) {
            throw new MyIllegalArgumentException("Order is not waiting for dispatch");
        }
        SimplePackage simplePackage = simplePackageBean.find(simplePackageId);
        if (simplePackage == null) {
            throw new MyEntityNotFoundException("Package not found");
        }
        order.setSimplePackage(simplePackage);
        // simplePackage.addOrderLine(orderLine);

        return order;*/

    }
}
