package pt.ipleiria.estg.dei.ei.pref.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.product_package_type_product.ProductPackageRelationPK;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageLevel;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Product create(String name, float length, float width, float height, HashSet<Long> productPackagesIds, String type){
        Product product = new Product(
                name,
                length,
                width,
                height,
                type
        );

        entityManager.persist(product);
        // packages order in hash influence in the type of package (primary, secondary, etc-)
        int i = 0;
        for (Long productPackageId : productPackagesIds) {
            if (i>= ProductPackageLevel.values().length){
                break;
            }

            ProductPackageType productPackageType = entityManager.find(ProductPackageType.class, productPackageId);
            ProductPackageRelation relation = new ProductPackageRelation(
                    new ProductPackageRelationPK(productPackageType.getId(), product.getId()),
                    product,
                    productPackageType,
                    ProductPackageLevel.values()[i]
            );
            entityManager.persist(relation);

            i++;
        }

        return product;
    }

    public Product findOrFail(String id){
        Product product = entityManager.find(Product.class, id);
        if(product == null){
            throw new RuntimeException("Product with id " + id + " not found");
        }

        Hibernate.initialize(product.getProductPackageRelations());
        return product;
    }

    public List<Product> getAllProducts(int offset, int limit) {

        List<Product> products = (List<Product>) entityManager.createNamedQuery("getAllProducts")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

        for (Product product : products) {
            Hibernate.initialize(product.getProductPackageRelations());
        }

        return products;
    }


    public List<ProductPackageType> getAllProductPackageTypes() {
        return (List<ProductPackageType>) entityManager.createNamedQuery("getAllProductPackageTypes").getResultList();
    }

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Product.class.getSimpleName(), Long.class).getSingleResult();
    }

    public ProductPackageType getPrimaryPackageType(String productId) {
         return (ProductPackageType) entityManager.createNamedQuery("getPackageTypeOfProduct")
                 .setParameter("productId", productId)
                 .setParameter("level", ProductPackageLevel.PRIMARY)
                 .getSingleResult();
    }
}
