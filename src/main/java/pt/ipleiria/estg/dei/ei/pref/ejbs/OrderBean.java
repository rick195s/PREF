package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.OrderPackageBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.SimplePackageTypeBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SimplePackageTypeBean simplePackageTypeBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private OrderLineBean orderLineBean;
    @EJB
    private OrderPackageBean orderPackageBean;

    public Order create(String date, Map<Long, Integer> productsQuantities, String source, String destination, String carrier, List<String> shippingMethods) throws MyIllegalArgumentException, MyEntityNotFoundException {
        List<Product> products = productsQuantities.keySet().stream().map(productBean::findOrFail).collect(Collectors.toList());

        float weight = 0;
        for (Product product : products) {
            weight += product.getWeight() * productsQuantities.get(product.getId());
        }

        Order order = new Order(date, source, destination, weight, carrier, shippingMethods, OrderState.PENDING);

        List<OrderLine> orderLines = products.stream().map(product ->
                orderLineBean.create(productsQuantities.get(product.getId()), product.getPrice(), product, order)).collect(Collectors.toList());

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
        List<Order> orders = (List<Order>) entityManager.createNamedQuery("getAllOrders")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        for (Order order : orders) {
            initializeOrder(order);
        }
        return orders;
    }

    private void initializeOrder(Order order) {
        Hibernate.initialize(order.getOrderLines());
        Hibernate.initialize(order.getShippingMethods());
        Hibernate.initialize(order.getOrderPackages());

        for (OrderLine orderLine : order.getOrderLines()) {
            Hibernate.initialize(orderLine.getOrderLineProductRelations());
            for (OrderLineProductRelation orderLineProductRelation : orderLine.getOrderLineProductRelations()) {
                Hibernate.initialize(orderLineProductRelation.getProduct().getProductPackageRelations());
                Hibernate.initialize(orderLineProductRelation.getProductPackage());
            }
        }
    }

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Order.class.getSimpleName(), Long.class).getSingleResult();
    }

    public Order packOrder(long trackingNumber) {
        Order order = findOrFail(trackingNumber);

        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }
        if (order.getState() == OrderState.PACKED) {
            throw new IllegalArgumentException("Order is already packed");
        }
        if (order.getOrderPackages().size() == 0) {
            throw new IllegalArgumentException("Order has no packages associated");
        }
        order.setState(OrderState.PACKED);

        return order;
    }
}
