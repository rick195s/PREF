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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SimplePackageBean simplePackageBean;

    @EJB
    private ProductBean productBean;

    public Order create(String date, Map<Long, Integer> productsQuantities, String source, String destination, String carrier, List<String> shippingMethods) throws MyIllegalArgumentException, MyEntityNotFoundException {
        List<Product> products = productsQuantities.keySet().stream().map(productBean::findOrFail).collect(Collectors.toList());

        float weight = 0;
        for (Product product : products) {
            weight += product.getWeight() * productsQuantities.get(product.getId());
        }

        Order order = new Order(date, source, destination, weight, carrier, shippingMethods, OrderState.PENDING);

        List<OrderLine> orderLines = products.stream().map(product ->
                new OrderLine(productsQuantities.get(product.getId()), productsQuantities.get(product.getId())*product.getPrice(),product, order)).collect(Collectors.toList());

        orderLines.forEach(order::addOrderLine);

        entityManager.persist(order);

        for (OrderLine orderLine : orderLines) {
            entityManager.persist(orderLine);
        }

        return order;
    }


    public Order findOrFail(long trackingNumber) {
        Order order = entityManager.getReference(Order.class, trackingNumber);
        initializeOrder(order);

        return order;
    }

    public List<Order> getAllOrders(int offset, int limit) {
        List<Order> orders=  (List<Order>) entityManager.createNamedQuery("getAllOrders")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        for (Order order : orders) {
           initializeOrder(order);
        }
        return orders;
    }

    private void initializeOrder(Order order){
        Hibernate.initialize(order.getOrderLines());
        Hibernate.initialize(order.getShippingMethods());
        Hibernate.initialize(order.getOrderPackages());

        for (OrderLine orderLine : order.getOrderLines()) {
            Hibernate.initialize(orderLine.getProduct().getProductPackageRelations());
        }
    }

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Order.class.getSimpleName(), Long.class).getSingleResult();
    }


    public Order dispatchOrder(long id) {
          Order order = findOrFail(id);

        if (order == null) {
            throw new MyEntityNotFoundException("OrderLine not found");
        }
        if (order.getState() != OrderState.PENDING) {
            throw new MyIllegalArgumentException("Order is not waiting for dispatch");
        }

        order.setState(OrderState.PACKED);

        return order;

    }
}
