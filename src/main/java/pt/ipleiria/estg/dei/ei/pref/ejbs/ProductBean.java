package pt.ipleiria.estg.dei.ei.pref.ejbs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelationPK;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductCategory;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public Product create(String name, ProductCategory category, float price, float weight, int validityRange, float length, float width, float height, HashSet<Long> productPackagesIds){
        Product product = new Product(
                name,
                category,
                price,
                weight,
                validityRange,
                length,
                width,
                height
        );

        entityManager.persist(product);
        // packages order influence in the type of package (primary, secondary, etc-)
        int i = 0;
        for (Long productPackageId : productPackagesIds) {
            if (i>=ProductPackageType.values().length){
                break;
            }

            ProductPackage productPackage = entityManager.find(ProductPackage.class, productPackageId);
            ProductPackageRelation relation = new ProductPackageRelation(
                    new ProductPackageRelationPK(productPackage.getId(), product.getId()),
                    product,
                    productPackage,
                    ProductPackageType.values()[i]
            );
            entityManager.persist(relation);

            i++;
        }

        return product;
    }

    public Product findOrFail(long id){
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


    public List<ProductPackage> getAllProductPackages() {
        return (List<ProductPackage>) entityManager.createNamedQuery("getAllProductPackages").getResultList();
    }

    public void populateProducts() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = objectMapper.readValue(getProductsJson(), new TypeReference<>(){});

        List<ProductPackage> productPackages = getAllProductPackages();
        HashSet<Long> productPackagesByType = new HashSet<>();

        // choose just a max of x packages for each product
        // none of the package type will be repeated
        int max = ProductPackageType.values().length;
        int min = 1;
        for (Product product : products) {
            productPackagesByType.clear();
            for (int i = 0; i < new Random().nextInt(max-min+1)+min; i++) {
                productPackagesByType.add(productPackages.get(new Random().nextInt(productPackages.size())).getId());
            }
            create(product.getName(), product.getCategory(), product.getPrice(), product.getWeight(), product.getValidityRange(), product.getLength(), product.getWidth(), product.getHeight(), productPackagesByType);
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

    public String getProductsJson(){return "[{\"name\":\"brownies\",\"category\":\"FOOD\",\"price\":1.16,\"weight\":0.06,\"validityRange\":7,\"length\":1,\"width\":1,\"height\":1},{\"name\":\"Protein Bar\",\"category\":\"FOOD\",\"price\":2.99,\"weight\":0.06,\"validityRange\":90,\"length\":10,\"width\":5,\"height\":2},{\"name\":\"Granola\",\"category\":\"FOOD\",\"price\":5.99,\"weight\":0.5,\"validityRange\":180,\"length\":15,\"width\":10,\"height\":5},{\"name\":\"Multigrain Snack\",\"category\":\"FOOD\",\"price\":1.99,\"weight\":0.1,\"validityRange\":60,\"length\":5,\"width\":5,\"height\":5},{\"name\":\"Blueberry Muffin\",\"category\":\"FOOD\",\"price\":3.49,\"weight\":0.1,\"validityRange\":7,\"length\":8,\"width\":5,\"height\":5},{\"name\":\"Whole Wheat Bread\",\"category\":\"FOOD\",\"price\":2.99,\"weight\":0.5,\"validityRange\":10,\"length\":20,\"width\":10,\"height\":5},{\"name\":\"BCAA Supplements\",\"category\":\"SPORTS_NUTRITION\",\"price\":24.99,\"weight\":0.5,\"validityRange\":24,\"length\":10,\"width\":10,\"height\":15},{\"name\":\"Pre-Workout Supplements\",\"category\":\"SPORTS_NUTRITION\",\"price\":34.99,\"weight\":0.25,\"validityRange\":12,\"length\":7,\"width\":7,\"height\":10},{\"name\":\"Creatine Monohydrate\",\"category\":\"SPORTS_NUTRITION\",\"price\":19.99,\"weight\":0.3,\"validityRange\":36,\"length\":5,\"width\":5,\"height\":10},{\"name\":\"Whey Protein\",\"category\":\"SPORTS_NUTRITION\",\"price\":29.99,\"weight\":1,\"validityRange\":365,\"length\":20,\"width\":15,\"height\":10},{\"name\":\"Creatine Monohydrate\",\"category\":\"SPORTS_NUTRITION\",\"price\":12.99,\"weight\":0.5,\"validityRange\":730,\"length\":10,\"width\":10,\"height\":10},{\"name\":\"BCAA Powder\",\"category\":\"SPORTS_NUTRITION\",\"price\":24.99,\"weight\":0.3,\"validityRange\":365,\"length\":10,\"width\":10,\"height\":10},{\"name\":\"Vitamin C Supplement\",\"category\":\"SPORTS_NUTRITION\",\"price\":9.99,\"weight\":0.1,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":5},{\"name\":\"Protein Shake\",\"category\":\"SPORTS_NUTRITION\",\"price\":3.99,\"weight\":0.5,\"validityRange\":30,\"length\":10,\"width\":5,\"height\":15},{\"name\":\"Daily Moisturizer\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":14.99,\"weight\":0.2,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":15},{\"name\":\"Body Wash\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":7.99,\"weight\":0.5,\"validityRange\":365,\"length\":10,\"width\":5,\"height\":15},{\"name\":\"Exfoliating Scrub\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":12.99,\"weight\":0.1,\"validityRange\":90,\"length\":5,\"width\":5,\"height\":10},{\"name\":\"Shampoo\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":9.99,\"weight\":0.3,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":15},{\"name\":\"Body Lotion\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":11.99,\"weight\":0.2,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":15},{\"name\":\"Running Shoes\",\"category\":\"CLOTHING_SHOE\",\"price\":79.99,\"weight\":0.6,\"validityRange\":12,\"length\":30,\"width\":10,\"height\":15},{\"name\":\"Sports Leggings\",\"category\":\"CLOTHING_SHOE\",\"price\":24.99,\"weight\":0.25,\"validityRange\":12,\"length\":20,\"width\":15,\"height\":5},{\"name\":\"Athletic Shorts\",\"category\":\"CLOTHING_SHOE\",\"price\":19.99,\"weight\":0.15,\"validityRange\":6,\"length\":20,\"width\":15,\"height\":2},{\"name\":\"Sports Jacket\",\"category\":\"CLOTHING_SHOE\",\"price\":49.99,\"weight\":0.6,\"validityRange\":1095,\"length\":30,\"width\":20,\"height\":5},{\"name\":\"Lifestyle T-shirt\",\"category\":\"CLOTHING_SHOE\",\"price\":19.99,\"weight\":0.2,\"validityRange\":365,\"length\":25,\"width\":15,\"height\":5},{\"name\":\"Sports Bag\",\"category\":\"CLOTHING_SHOE\",\"price\":29.99,\"weight\":0.8,\"validityRange\":1095,\"length\":45,\"width\":25,\"height\":25},{\"name\":\"Meal Prep Bag\",\"category\":\"CLOTHING_SHOE\",\"price\":39.99,\"weight\":0.7,\"validityRange\":365,\"length\":30,\"width\":20,\"height\":20},{\"name\":\"Handheld Blender\",\"category\":\"TECHNOLOGY_HOUSE\",\"price\":29.99,\"weight\":0.7,\"validityRange\":1095,\"length\":20,\"width\":10,\"height\":10},{\"name\":\"Shaker Bottle\",\"category\":\"TECHNOLOGY_HOUSE\",\"price\":9.99,\"weight\":0.2,\"validityRange\":365,\"length\":10,\"width\":10,\"height\":20},{\"name\":\"Water Bottle\",\"category\":\"TECHNOLOGY_HOUSE\",\"price\":14.99,\"weight\":0.3,\"validityRange\":1095,\"length\":10,\"width\":10,\"height\":20}]";}

    public String getProductPackagesJson(){return "[{\"name\":\"duplex PE+AL\",\"cost\":2.99,\"dimension\":\"10x10x5cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"cartão microcanelado\",\"cost\":1.49,\"dimension\":\"15x15x7cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"Papel conformado/ cup cake wrappers\",\"cost\":0.99,\"dimension\":\"6x6x3cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"Cartolina 300g\",\"cost\":0.75,\"dimension\":\"20x20x5cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"PE cristal\",\"cost\":1.25,\"dimension\":\"12x12x8cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"Frasco PET sleeve\",\"cost\":3.99,\"dimension\":\"15x10x10cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"Tampa PP\",\"cost\":0.5,\"dimension\":\"5x5x2cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"DOYPACK\",\"cost\":1.99,\"dimension\":\"10x15x3cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"PP + AL/ PE + AL\",\"cost\":2.49,\"dimension\":\"8x8x8cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"DOYPACK POUCH\",\"cost\":2.99,\"dimension\":\"12x18x5cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"Frasco PET\",\"cost\":2.25,\"dimension\":\"10x15x10cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"Saco PE + AL\",\"cost\":0.99,\"dimension\":\"20x30x5cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"PEFC\",\"cost\":0.25,\"dimension\":\"10x10x1cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"PE laminado\",\"cost\":1.75,\"dimension\":\"10x15x5cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"tetrapak\",\"cost\":1.99,\"dimension\":\"15x20x10cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"Filme PE\",\"cost\":0.5,\"dimension\":\"10x10x1cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"opérculo filme PE\",\"cost\":0.25,\"dimension\":\"5x5x1cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"colher doseadora 70ml PP\",\"cost\":0.15,\"dimension\":\"10x5x1cm\",\"sustainable\":false,\"smart\":false}]\n";}

    public Long count() {
        return entityManager.createQuery("SELECT COUNT(*) FROM " + Product.class.getSimpleName(), Long.class).getSingleResult();
    }
}
