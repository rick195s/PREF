package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderLineProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
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
        OrderLine orderLine = new OrderLine(quantity, productPrice, order, "");
        entityManager.persist(orderLine);

        Hibernate.initialize(product.getProductPackageRelations());
        for (int i = 0; i < quantity; i++) {
            OrderLineProductRelation relation = new OrderLineProductRelation(
                    product,
                    orderLine
            );
            entityManager.persist(relation);

            for (ProductPackageType productPackageType : product.getProductPackages()) {
                OrderLineProductPackage newProductPackage = new OrderLineProductPackage(
                        productPackageType,
                        relation
                );

                newProductPackage.setOrderLineProductRelation(relation);
                entityManager.persist(newProductPackage);
            }

        }

        return orderLine;
    }


    public OrderLine findOrFail(long id) {
        return entityManager.getReference(OrderLine.class, id);
    }

}
