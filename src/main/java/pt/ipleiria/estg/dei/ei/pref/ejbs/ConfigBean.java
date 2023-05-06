package pt.ipleiria.estg.dei.ei.pref.ejbs;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.datafaker.Faker;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObserverBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.enumerators.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @EJB
    ObservationBean observationBean;

    @EJB
    ObserverBean observerBean;

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

        createObservers();
        System.out.println("Observers created");

        createObservation();
        System.out.println("Observations created");

    }

    private void createObservers(){
        observerBean.create("Temperature Sensor");
        observerBean.create("Humidity Sensor");
        observerBean.create("Location Sensor");
    }

    private void createObservation(){
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = date.format(formatter);

        observationBean.create(PhenomenonType.TEMPERATURE, 1, dateString,"{\"key1\": \"value1\", \"key2\": \"value2\"}", 1, "21");
        observationBean.create(PhenomenonType.TEMPERATURE, 1, dateString, "{\"key1\": \"value1\", \"key2\": \"value2\"}", 1, "23");
        observationBean.create(PhenomenonType.HUMIDITY, 2, dateString, "{\"key1\": \"value1\", \"key2\": \"value2\"}", 2, "18");
        observationBean.create(PhenomenonType.HUMIDITY, 2, dateString, "{\"key1\": \"value1\", \"key2\": \"value2\"}", 2, "19");
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
            orderBean.dispatchOrder(i+1);
        }
    }

    private void createOrders() {
        Faker faker = new Faker();

        Map<Long, Integer> productsQuantities = new HashMap<>();
        productsQuantities.put(1L, 1);
        productsQuantities.put(2L, 2);
        productsQuantities.put(3L, 3);
        productsQuantities.put(4L, 4);

        for (int i = 0; i < 500; i++) {
            orderBean.create(
                    faker.date().past(2, TimeUnit.DAYS).toString(),
                    productsQuantities,
                    faker.address().cityName(),
                    faker.address().cityName(),
                    faker.company().name(),
                    List.of("air", "ground"));
        }
    }

}
