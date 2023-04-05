package pt.ipleiria.estg.dei.ei.pref.ejbs;

import net.datafaker.Faker;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.enumerators.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
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

    @EJB
    OrderLineBean orderLineBean;


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

        simplePackageBean.create(1, 20,"10x10x10", poliAl, PackageType.DUPLEX, PackageCategory.SIMPLE, true, ResistenceType.MEDIUM, false);
        simplePackageBean.create(2, 10,"10x10x10", cartao, PackageType.CARTAO_MICROCANELADO, PackageCategory.SIMPLE, false, ResistenceType.LOW, false);
        simplePackageBean.create(3, 40,"10x10x10", polipla, PackageType.DUPLEX, PackageCategory.COMPLEX, false, ResistenceType.HIGH, true);
        simplePackageBean.create(4, 20,"10x10x10", poliAl, PackageType.DUPLEX, PackageCategory.SIMPLE, false, ResistenceType.MEDIUM, false);
        simplePackageBean.create(5, 50,"10x10x10", polipla, PackageType.DUPLEX, PackageCategory.COMPLEX, true, ResistenceType.HIGH, true);
        System.out.println("Packages created");

        dispatchOrders();
        System.out.println("Orders dispatched");

    }

    private void dispatchOrders() {
        for (int i = 0; i < 200; i++) {
            // random number with min 1 and max 5
            orderLineBean.dispatchOrder(i+1, new Random().nextInt(5)+1);
        }
    }

    private void createOrders() {
        Faker faker = new Faker();
        for (int i = 0; i < 500; i++) {
            orderBean.create(
                    faker.date().past(2, TimeUnit.DAYS).toString(),
                    List.of(entityManager.find(Product.class, 1L), entityManager.find(Product.class, 2L), entityManager.find(Product.class, 3L)),
                    faker.address().cityName(),
                    faker.address().cityName(),
                    OrderState.PENDING,
                    (float) (Math.random() * 10),
                    faker.company().name(),
                    List.of("air", "ground"));
        }
    }

    private void createProducts(){
        Faker faker = new Faker();
        DecimalFormat df = new DecimalFormat("#.##");

        for (int i = 0; i < 50; i++) {
            entityManager.persist(
                    new Product(
                        faker.food().dish(),
                        ProductCategory.FOOD,
                        // random price with max 50
                        Float.parseFloat(df.format(Math.random() * 50)),
                    1,
                    1,
                    1,
                    1,
                    1
                    )
            );
        }
    }
}
