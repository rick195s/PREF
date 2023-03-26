package pt.ipleiria.estg.dei.ei.pref.ejbs;

import net.datafaker.Faker;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductCategory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton
public class ConfigBean {

    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    OrderBean orderBean;

    @EJB
    SimplePackageBean simplePackageBean;

    List<PackageMaterialType> poliAl = List.of(PackageMaterialType.POLIETILENO, PackageMaterialType.ALUMINIO);
    List<PackageMaterialType> cartao = List.of(PackageMaterialType.CARTAO);
    List<PackageMaterialType> polipla = List.of(PackageMaterialType.POLIETILENO, PackageMaterialType.PLASTICO);

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");

        createProducts();
        System.out.println("Products created");

        createOrders();
        System.out.println("Orders created");

        simplePackageBean.create(1, "duplex PE+AL", "10x10x10", poliAl, PackageCategory.SIMPLE);
        simplePackageBean.create(2, "cartão microcanelado", "10x10x10", cartao, PackageCategory.SIMPLE);
        simplePackageBean.create(3, "PE cristal", "10x10x10", polipla, PackageCategory.SIMPLE);
        simplePackageBean.create(4, "PE laminado", "10x10x10", poliAl, PackageCategory.SIMPLE);
        simplePackageBean.create(5, "DOYPACK PP+AL/PE+AL", "10x10x10", polipla, PackageCategory.SIMPLE);

    }

    private void createOrders() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            orderBean.create(
                    faker.date().past(2, TimeUnit.DAYS).toString(),
                    List.of(entityManager.find(Product.class, 1L)),
                    faker.address().cityName(),
                    faker.address().cityName(),
                    OrderState.PENDING);
        }
    }

    private void createProducts(){
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            entityManager.persist(new Product(faker.food().dish(), ProductCategory.FOOD));
        }
    }
}
