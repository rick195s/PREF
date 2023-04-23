package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SimplePackageBean simplePackageBean;

    @EJB
    private ProductBean productBean;

    public Order create(String date, List<Long> productIds, String source, String destination, String carrier, List<String> shippingMethods) throws MyIllegalArgumentException, MyEntityNotFoundException {
        List<Product> products = productIds.stream().map(productBean::findOrFail).collect(Collectors.toList());

        float weight = (float) products.stream().mapToDouble(Product::getWeight).sum();

        Order order = new Order(date, source, destination, weight, carrier, shippingMethods, OrderState.PENDING);

        List<OrderLine> orderLines = products.stream().map(product ->
                new OrderLine(1, product.getPrice(),product, order)).collect(Collectors.toList());

        orderLines.forEach(order::addOrderLine);

        entityManager.persist(order);

        for (OrderLine orderLine : orderLines) {
            entityManager.persist(orderLine);
        }

        return order;
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


    public Order dispatchOrder(long id, long orderPackageId) {
          Order order = findOrFail(id);

        if (order == null) {
            throw new MyEntityNotFoundException("OrderLine not found");
        }
        if (order.getState() != OrderState.PENDING) {
            throw new MyIllegalArgumentException("Order is not waiting for dispatch");
        }
        OrderPackage orderPackage = entityManager.find(OrderPackage.class, orderPackageId);
        if (orderPackage == null) {
            throw new MyEntityNotFoundException("Package not found");
        }
        order.setOrderPackage(orderPackage);
        order.setState(OrderState.PACKED);

        return order;

    }
}
