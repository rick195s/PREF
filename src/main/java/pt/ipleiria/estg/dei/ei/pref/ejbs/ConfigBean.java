package pt.ipleiria.estg.dei.ei.pref.ejbs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.OrderLineProductPackageBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.OrderPackageBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.OrderPackageTypeBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObserverBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderLineProductPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.ProductPackageType;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observer;
import pt.ipleiria.estg.dei.ei.pref.entities.relations.order_line_product.OrderLineProductRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.*;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
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
    ProductBean productBean;

    @EJB
    ObservationBean observationBean;

    @EJB
    ObserverBean observerBean;

    @EJB
    OrderPackageBean orderPackageBean;

    @EJB
    OrderPackageTypeBean orderPackageTypeBean;

    @EJB
    OrderLineProductPackageBean orderLineProductPackageBean;

    //@PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");

        createObservers();
        System.out.println("Observers created");

        createProductPackages();
        System.out.println("ProductPackages created");

        createObservations();
        System.out.println("Observations created");

    }

    private void createProductPackages() {
        List<OrderLineProductRelation> orderLineProductRelations = (List<OrderLineProductRelation>) entityManager.createNamedQuery("getAllOrderLineProductRelations").setMaxResults(3000).getResultList();
        int i = 0;
        System.out.println("Tamanho order line product relations: "+ orderLineProductRelations.size());
        for (OrderLineProductRelation orderLineProductRelation : orderLineProductRelations) {
            orderLineProductPackageBean.createPrimaryPackage(orderLineProductRelation.getId());
            i++;
            System.out.println("ProductPackage " + i + " created");
        }
    }

    private void createObservers() {
        observerBean.create("Temperature Sensor");
        observerBean.create("Humidity Sensor");
        observerBean.create("Location Sensor");
    }

    private void createObservations() {
        Faker faker = new Faker();
        String details = "{\"custom_column\": \"value1\"}";

        createOrderPackageObservations(faker, details);
        createProductPackageObservations(faker, details);

        System.out.println("Observations created");
    }

    private void createOrderPackageObservations(Faker faker, String details) {

        List<Observation> observations = new ArrayList<>();
        int i = 0;

        List<OrderPackage> orderPackages =  orderPackageBean.getAllOrderPackages();
        int totalObser = 0 ;
        for (OrderPackage orderPackage : orderPackages) {

            // create observations in 80% of the order packages
            if (faker.random().nextInt(1, 100) <= 80) {
                Timestamp date1 = faker.date().future(1, TimeUnit.DAYS, Timestamp.valueOf(orderPackage.getOrder().getDate()));
                Timestamp date2 = faker.date().future(4, TimeUnit.DAYS, date1);

                Order order = orderPackage.getOrder();

                observations.add(new Observation(PhenomenonType.LOCATION, new Observer(1), date1.toString(), details, new OrderPackage(orderPackage.getId()), faker.address().cityName()));
                observations.add(new Observation(PhenomenonType.LOCATION, new Observer(1), date2.toString(), details, new OrderPackage(orderPackage.getId()), faker.address().cityName()));

                for (int i1 = 0; i1 < faker.random().nextInt(5, 10); i1++) {
                    observations.add(new Observation(PhenomenonType.TEMPERATURE, new Observer(1),
                            faker.date().future(4, TimeUnit.DAYS, date1).toString(),
                            details, new OrderPackage(orderPackage.getId()), String.valueOf(faker.random().nextInt(5, 30))));

                    observations.add(new Observation(PhenomenonType.HUMIDITY, new Observer(2),
                            faker.date().future(4, TimeUnit.DAYS, date2).toString(),
                            details, new OrderPackage(orderPackage.getId()), String.valueOf(faker.random().nextInt(10, 80))));
                }
                totalObser += observations.size();
                if (totalObser >= orderPackages.size() || observations.size() > 50) {
                    observationBean.createMultipleObservations(observations);
                    observations.clear();
                }
                i++;
                System.out.println("Order package observation " + i + " created");
            }
        }
    }

    private void createProductPackageObservations(Faker faker, String details) {
        int minTemp = 10;
        int maxTemp = 30;
        int minHumidity = 10;
        int maxHumidity = 80;

        int i1 = 0;
        List<Observation> observations = new ArrayList<>();
        List<OrderLineProductPackage> orderLineProductPackages =  orderLineProductPackageBean.getAllProductPackages(500);
        int totalObser= 0 ;
        for (OrderLineProductPackage productPackage : orderLineProductPackages) {
            Timestamp date = faker.date().future(1, TimeUnit.DAYS, Timestamp.valueOf(productPackage.getOrderLineProductRelation().getOrderLine().getOrder().getDate()));

            Order order = productPackage.getOrderLineProductRelation().getOrderLine().getOrder();

            for (int i = 0; i < faker.random().nextInt(5, 10); i++) {

                int temperature = faker.random().nextInt(minTemp, maxTemp);
                int humidity = faker.random().nextInt(minHumidity, maxHumidity);

                observations.add(new Observation(PhenomenonType.TEMPERATURE, new Observer(1),
                        faker.date().future(4, TimeUnit.DAYS, date).toString(),
                        details, new OrderLineProductPackage(productPackage.getId()), String.valueOf(temperature)));
                observations.add(new Observation(PhenomenonType.TEMPERATURE, new Observer(2),
                        faker.date().future(4, TimeUnit.DAYS, date).toString(),
                        details, new OrderLineProductPackage(productPackage.getId()), String.valueOf(humidity)));
            }
            totalObser += observations.size();
            if (totalObser >= orderLineProductPackages.size() ||  observations.size() > 50){
                observationBean.createMultipleObservations(observations);
                observations.clear();
            }
            i1++;
            System.out.println("Product package observation "+i1+" created");
        }
    }



}
