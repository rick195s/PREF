package pt.ipleiria.estg.dei.ei.pref.ejbs;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.datafaker.Faker;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
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
    ProductBean productBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");

        try {
            productBean.populateProductPackages();
            System.out.println("Product Packages created");

            productBean.populateProducts();
            System.out.println("Products created");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        createOrders();
        System.out.println("Orders created");

        createOrderPackages();
        System.out.println("OrderPackages created");

        dispatchOrders();
        System.out.println("Orders dispatched");

    }

    private void createOrderPackages() {
        List<OrderPackage> packages = new LinkedList<>();

        packages.add(new OrderPackage("Cartao",  20,"10x10x10",  true, ResistenceType.MEDIUM, false));
        packages.add(new OrderPackage( "Palete", 10,"10x10x10",  false, ResistenceType.LOW, false));
        packages.add(new OrderPackage( "CAIXA ISOMÉTRICA EPS", 40,"10x10x10",  false, ResistenceType.HIGH, true));

        for (OrderPackage orderPackage : packages) {
            entityManager.persist(orderPackage);
        }

    }

    private void dispatchOrders() {
        // get all orderPackages
        List<OrderPackage> orderPackages = entityManager.createNamedQuery("getAllOrderPackages").getResultList();

        for (int i = 0; i < 200; i++) {
            // random number with min 1 and max 3
            orderBean.dispatchOrder(i+1, orderPackages.get(new Random().nextInt(orderPackages.size())).getId());
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

}
