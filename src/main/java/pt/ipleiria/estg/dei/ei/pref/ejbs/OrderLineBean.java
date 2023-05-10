package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderLineBean {
    @PersistenceContext
    private EntityManager entityManager;

    public OrderLine create(int quantity, float productPrice, Product product, Order order) throws MyIllegalArgumentException, MyEntityNotFoundException {
        OrderLine orderLine = new OrderLine(quantity, productPrice, order);
        entityManager.persist(orderLine);

        for (int i = 0; i < quantity; i++) {
            OrderLineProductRelation relation = new OrderLineProductRelation(
                    product,
                    orderLine
            );
            entityManager.persist(relation);
        }

        return orderLine;
    }


    public OrderLine findOrFail(long id) {
        return entityManager.getReference(OrderLine.class, id);
    }

}
