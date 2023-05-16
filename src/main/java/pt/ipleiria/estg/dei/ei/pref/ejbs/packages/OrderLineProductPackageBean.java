package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderLineBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderLineProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelation;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class OrderLineProductPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderLineBean orderLineBean;

    @EJB
    private ProductBean productBean;

    public List<OrderLineProductPackage> getAllProductPackages(int limit) {
        Query getAllSmartOrderLineProductPackages = entityManager.createNamedQuery("getAllOrderLineProductPackages");
        if (limit == 0){
            return (List<OrderLineProductPackage>) getAllSmartOrderLineProductPackages
                    .getResultList();
        }
        return (List<OrderLineProductPackage>) getAllSmartOrderLineProductPackages.setMaxResults(limit).getResultList();
    }


    public List<OrderLineProductPackage> getAllSmartProductPackages(int limit) {
        Query getAllOrderLineProductPackages = entityManager.createNamedQuery("getAllSmartOrderLineProductPackages");
        if (limit == 0){
            return (List<OrderLineProductPackage>) getAllOrderLineProductPackages
                    .getResultList();        }
        return (List<OrderLineProductPackage>) getAllOrderLineProductPackages.setMaxResults(limit).getResultList();

    }

    public OrderLineProductPackage findOrFail(long id) {
        return entityManager.find(OrderLineProductPackage.class, id);
    }

    public OrderLineProductPackage createPrimaryPackage(long orderLineProductRelationId) {
        OrderLineProductRelation relation = entityManager.find(OrderLineProductRelation.class, orderLineProductRelationId);
        if (relation == null) {
            throw new EntityNotFoundException("Order Line - Product Relation not found.");
        }

        ProductPackageType productPackageType = productBean.getPrimaryPackageType(relation.getProduct().getId());
        if (productPackageType == null) {
            throw new EntityNotFoundException("Product package type not found.");
        }

        OrderLineProductPackage orderLineProductPackage = new OrderLineProductPackage(productPackageType, relation);

        entityManager.persist(orderLineProductPackage);

        return orderLineProductPackage;
    }
}
