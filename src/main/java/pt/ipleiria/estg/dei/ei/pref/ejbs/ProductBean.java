package pt.ipleiria.estg.dei.ei.pref.ejbs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelationPK;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyIllegalArgumentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Product create(Product product){
        entityManager.persist(product);

        List<ProductPackage> packages = getAllProductPackages();

        ProductPackage productPackage =  packages.get(new Random().nextInt(packages.size()));
        ProductPackageRelation relation = new ProductPackageRelation(
                new ProductPackageRelationPK(productPackage.getId(), product.getId()),
                product,
                productPackage,
                ProductPackageType.PRIMARY
        );

        entityManager.persist(relation);

        return product;
    }


    public List<ProductPackage> getAllProductPackages() {
        return (List<ProductPackage>) entityManager.createNamedQuery("getAllProductPackages").getResultList();
    }

    public void populateProducts() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = objectMapper.readValue(getProductsJson(), new TypeReference<List<Product>>(){});

        for (Product product : products) {
            create(product);
        }
    }

    public void populateProductPackages() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductPackage> productPackages = objectMapper.readValue(getProductPackagesJson(), new TypeReference<List<ProductPackage>>(){});

        for (ProductPackage productPackage : productPackages) {
            System.out.println(productPackage.getName());
            entityManager.persist(productPackage);
        }
    }

    public static String getProductsJson(){return "[\n{\"name\": \"brownies\",\"category\": \"FOOD\",\"price\": 1.16,\"weight\": 0.060,\"validityRange\": 7,\"length\": 1,\"width\": 1,\"height\": 1},{\"name\": \"Protein Bar\",\"category\": \"FOOD\",\"price\": 2.99,\"weight\": 0.06,\"validityRange\": 90,\"length\": 10,\"width\": 5,\"height\": 2},{\"name\": \"Granola\",\"category\": \"FOOD\",\"price\": 5.99,\"weight\": 0.5,\"validityRange\": 180,\"length\": 15,\"width\": 10,\"height\": 5},{\"name\": \"Multigrain Snack\",\"category\": \"FOOD\",\"price\": 1.99,\"weight\": 0.1,\"validityRange\": 60,\"length\": 5,\"width\": 5,\"height\": 5},{\"name\": \"Blueberry Muffin\",\"category\": \"FOOD\",\"price\": 3.49,\"weight\": 0.1,\"validityRange\": 7,\"length\": 8,\"width\": 5,\"height\": 5},{\"name\": \"Whole Wheat Bread\",\"category\": \"FOOD\",\"price\": 2.99,\"weight\": 0.5,\"validityRange\": 10,\"length\": 20,\"width\": 10,\"height\": 5},{\"name\": \"BCAA Supplements\",\"category\": \"SPORTS_NUTRITION\",\"price\": 24.99,\"weight\": 0.500,\"validityRange\": 24,\"length\": 10,\"width\": 10,\"height\": 15},{\"name\": \"Pre-Workout Supplements\",\"category\": \"SPORTS_NUTRITION\",\"price\": 34.99,\"weight\": 0.250,\"validityRange\": 12,\"length\": 7,\"width\": 7,\"height\": 10},{\"name\": \"Creatine Monohydrate\",\"category\": \"SPORTS_NUTRITION\",\"price\": 19.99,\"weight\": 0.300,\"validityRange\": 36,\"length\": 5,\"width\": 5,\"height\": 10},{\"name\": \"Whey Protein\",\"category\": \"SPORTS_NUTRITION\",\"price\": 29.99,\"weight\": 1,\"validityRange\": 365,\"length\": 10,\"width\": 10,\"height\": 20}]";}

    public static String getProductPackagesJson(){return "[{\"name\": \"duplex PE+AL\"}, {\"name\": \"cartão microcanelado\"}, {\"name\": \"Papel conformado/ cup cake wrappers\"}, {\"name\": \"Cartolina 300g\"}, {\"name\": \"PE cristal\"}, {\"name\": \"Frasco PET sleeve\"}, {\"name\": \"Tampa PP\"}, {\"name\": \"DOYPACK\"}, {\"name\": \"PP + AL/ PE + AL\"}, {\"name\": \"DOYPACK POUCH\"}, {\"name\": \"Frasco PET\"}, {\"name\": \"Saco PE + AL\"}, {\"name\": \"PEFC\"}, {\"name\": \"PE laminado\"}, {\"name\": \"tetrapak\"}, {\"name\": \"Filme PE\"}, {\"name\": \"opérculo filme PE\"}, {\"name\": \"colher doseadora 70ml PP\"}]";}
}
