package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.OrderPackageBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.SimplePackageTypeBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
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

    public Order create(String date, Map<String, Integer> productsQuantities, String carrier, String shippingMethod) throws MyIllegalArgumentException, MyEntityNotFoundException {
        List<Product> products = productsQuantities.keySet().stream().map(productBean::findOrFail).collect(Collectors.toList());

        Order order = new Order(date, 0, carrier, shippingMethod, "", "", "","", "", "", "", 1 );

        List<OrderLine> orderLines = products.stream().map(product ->
                orderLineBean.create(productsQuantities.get(product.getId()), 1, product, order)).collect(Collectors.toList());

        orderLines.forEach(order::addOrderLine);

        entityManager.persist(order);

        for (OrderLine orderLine : orderLines) {
            entityManager.persist(orderLine);
        }

        return order;
    }


    public Order findOrFail(String id) {
        Order order = entityManager.getReference(Order.class, id);
        initializeOrder(order);

        return order;
    }

    public List<Order> getAllOrders(int offset, int limit, String carrier) {
        List<Order> orders = null;

        if (carrier != null && !carrier.isEmpty()) {
            orders = (List<Order>) entityManager.createNamedQuery("getAllOrdersByCarrier")
                    .setParameter("carrier", carrier)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        }else {
             orders = (List<Order>) entityManager.createNamedQuery("getAllOrders")
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        }

        return orders;
    }

    private void initializeOrder(Order order) {
        Hibernate.initialize(order.getOrderLines());
        Hibernate.initialize(order.getOrderPackages());

        for (OrderLine orderLine : order.getOrderLines()) {
            Hibernate.initialize(orderLine.getOrderLineProductRelations());
            for (OrderLineProductRelation orderLineProductRelation : orderLine.getOrderLineProductRelations()) {
                Hibernate.initialize(orderLineProductRelation.getProduct().getProductPackageRelations());
                Hibernate.initialize(orderLineProductRelation.getProductPackage());
            }
        }
    }

    public Long count(String carrier) {
        if (carrier != null && !carrier.isEmpty()) {
            return entityManager.createQuery("SELECT COUNT(*) FROM Order o WHERE o.carrier = :carrier", Long.class)
                .setParameter("carrier", carrier)
                .getSingleResult();

        }
            return entityManager.createQuery("SELECT COUNT(*) FROM " + Order.class.getSimpleName(), Long.class).getSingleResult();
    }

    public Order packOrder(String id) {
        Order order = findOrFail(id);

        if (order == null) {
            throw new EntityNotFoundException("Order not found");
        }

        if (order.getOrderPackages().size() == 0) {
            throw new IllegalArgumentException("Order has no packages associated");
        }

        return order;
    }

    public Object getCarriers() {
        return entityManager.createNamedQuery("getCarriers").getResultList();
    }
}
