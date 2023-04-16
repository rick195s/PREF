package pt.ipleiria.estg.dei.ei.pref.ejbs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelationPK;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

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
            productPackage.setResistance(ResistenceType.values()[new Random().nextInt(ResistenceType.values().length)]);
            entityManager.persist(productPackage);
        }
    }

    public String getProductsJson(){return "[\n{\"name\": \"brownies\",\"category\": \"FOOD\",\"price\": 1.16,\"weight\": 0.060,\"validityRange\": 7,\"length\": 1,\"width\": 1,\"height\": 1},{\"name\": \"Protein Bar\",\"category\": \"FOOD\",\"price\": 2.99,\"weight\": 0.06,\"validityRange\": 90,\"length\": 10,\"width\": 5,\"height\": 2},{\"name\": \"Granola\",\"category\": \"FOOD\",\"price\": 5.99,\"weight\": 0.5,\"validityRange\": 180,\"length\": 15,\"width\": 10,\"height\": 5},{\"name\": \"Multigrain Snack\",\"category\": \"FOOD\",\"price\": 1.99,\"weight\": 0.1,\"validityRange\": 60,\"length\": 5,\"width\": 5,\"height\": 5},{\"name\": \"Blueberry Muffin\",\"category\": \"FOOD\",\"price\": 3.49,\"weight\": 0.1,\"validityRange\": 7,\"length\": 8,\"width\": 5,\"height\": 5},{\"name\": \"Whole Wheat Bread\",\"category\": \"FOOD\",\"price\": 2.99,\"weight\": 0.5,\"validityRange\": 10,\"length\": 20,\"width\": 10,\"height\": 5},{\"name\": \"BCAA Supplements\",\"category\": \"SPORTS_NUTRITION\",\"price\": 24.99,\"weight\": 0.500,\"validityRange\": 24,\"length\": 10,\"width\": 10,\"height\": 15},{\"name\": \"Pre-Workout Supplements\",\"category\": \"SPORTS_NUTRITION\",\"price\": 34.99,\"weight\": 0.250,\"validityRange\": 12,\"length\": 7,\"width\": 7,\"height\": 10},{\"name\": \"Creatine Monohydrate\",\"category\": \"SPORTS_NUTRITION\",\"price\": 19.99,\"weight\": 0.300,\"validityRange\": 36,\"length\": 5,\"width\": 5,\"height\": 10},{\"name\": \"Whey Protein\",\"category\": \"SPORTS_NUTRITION\",\"price\": 29.99,\"weight\": 1,\"validityRange\": 365,\"length\": 10,\"width\": 10,\"height\": 20}]";}

    public String getProductPackagesJson(){return "[{\"name\":\"duplex PE+AL\",\"cost\":2.99,\"dimension\":\"10x10x5cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"cartão microcanelado\",\"cost\":1.49,\"dimension\":\"15x15x7cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"Papel conformado/ cup cake wrappers\",\"cost\":0.99,\"dimension\":\"6x6x3cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"Cartolina 300g\",\"cost\":0.75,\"dimension\":\"20x20x5cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"PE cristal\",\"cost\":1.25,\"dimension\":\"12x12x8cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"Frasco PET sleeve\",\"cost\":3.99,\"dimension\":\"15x10x10cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"Tampa PP\",\"cost\":0.5,\"dimension\":\"5x5x2cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"DOYPACK\",\"cost\":1.99,\"dimension\":\"10x15x3cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"PP + AL/ PE + AL\",\"cost\":2.49,\"dimension\":\"8x8x8cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"DOYPACK POUCH\",\"cost\":2.99,\"dimension\":\"12x18x5cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"Frasco PET\",\"cost\":2.25,\"dimension\":\"10x15x10cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"Saco PE + AL\",\"cost\":0.99,\"dimension\":\"20x30x5cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"PEFC\",\"cost\":0.25,\"dimension\":\"10x10x1cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"PE laminado\",\"cost\":1.75,\"dimension\":\"10x15x5cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"tetrapak\",\"cost\":1.99,\"dimension\":\"15x20x10cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"Filme PE\",\"cost\":0.5,\"dimension\":\"10x10x1cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"opérculo filme PE\",\"cost\":0.25,\"dimension\":\"5x5x1cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"colher doseadora 70ml PP\",\"cost\":0.15,\"dimension\":\"10x5x1cm\",\"sustainable\":false,\"smart\":false}]\n";}
}
