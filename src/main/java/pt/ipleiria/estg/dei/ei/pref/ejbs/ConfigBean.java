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

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");

        createProductPackageTypes();
        System.out.println("Product Package Types created");

        createProducts();
        System.out.println("Products created");

        createOrders();
        System.out.println("Orders created");

        createOrderPackageTypes();
        System.out.println("OrderPackageTypes created");

        createObservers();
        System.out.println("Observers created");

        createOrderPackages();
        System.out.println("OrderPackages created");

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

    private void createProducts() {
         String productsJson = "[{\"name\":\"brownies\",\"category\":\"FOOD\",\"price\":1.16,\"weight\":0.06,\"validityRange\":7,\"length\":1,\"width\":1,\"height\":1},{\"name\":\"Protein Bar\",\"category\":\"FOOD\",\"price\":2.99,\"weight\":0.06,\"validityRange\":90,\"length\":10,\"width\":5,\"height\":2},{\"name\":\"Granola\",\"category\":\"FOOD\",\"price\":5.99,\"weight\":0.5,\"validityRange\":180,\"length\":15,\"width\":10,\"height\":5},{\"name\":\"Multigrain Snack\",\"category\":\"FOOD\",\"price\":1.99,\"weight\":0.1,\"validityRange\":60,\"length\":5,\"width\":5,\"height\":5},{\"name\":\"Blueberry Muffin\",\"category\":\"FOOD\",\"price\":3.49,\"weight\":0.1,\"validityRange\":7,\"length\":8,\"width\":5,\"height\":5},{\"name\":\"Whole Wheat Bread\",\"category\":\"FOOD\",\"price\":2.99,\"weight\":0.5,\"validityRange\":10,\"length\":20,\"width\":10,\"height\":5},{\"name\":\"BCAA Supplements\",\"category\":\"SPORTS_NUTRITION\",\"price\":24.99,\"weight\":0.5,\"validityRange\":24,\"length\":10,\"width\":10,\"height\":15},{\"name\":\"Pre-Workout Supplements\",\"category\":\"SPORTS_NUTRITION\",\"price\":34.99,\"weight\":0.25,\"validityRange\":12,\"length\":7,\"width\":7,\"height\":10},{\"name\":\"Creatine Monohydrate\",\"category\":\"SPORTS_NUTRITION\",\"price\":19.99,\"weight\":0.3,\"validityRange\":36,\"length\":5,\"width\":5,\"height\":10},{\"name\":\"Whey Protein\",\"category\":\"SPORTS_NUTRITION\",\"price\":29.99,\"weight\":1,\"validityRange\":365,\"length\":20,\"width\":15,\"height\":10},{\"name\":\"Creatine Monohydrate\",\"category\":\"SPORTS_NUTRITION\",\"price\":12.99,\"weight\":0.5,\"validityRange\":730,\"length\":10,\"width\":10,\"height\":10},{\"name\":\"BCAA Powder\",\"category\":\"SPORTS_NUTRITION\",\"price\":24.99,\"weight\":0.3,\"validityRange\":365,\"length\":10,\"width\":10,\"height\":10},{\"name\":\"Vitamin C Supplement\",\"category\":\"SPORTS_NUTRITION\",\"price\":9.99,\"weight\":0.1,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":5},{\"name\":\"Protein Shake\",\"category\":\"SPORTS_NUTRITION\",\"price\":3.99,\"weight\":0.5,\"validityRange\":30,\"length\":10,\"width\":5,\"height\":15},{\"name\":\"Daily Moisturizer\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":14.99,\"weight\":0.2,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":15},{\"name\":\"Body Wash\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":7.99,\"weight\":0.5,\"validityRange\":365,\"length\":10,\"width\":5,\"height\":15},{\"name\":\"Exfoliating Scrub\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":12.99,\"weight\":0.1,\"validityRange\":90,\"length\":5,\"width\":5,\"height\":10},{\"name\":\"Shampoo\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":9.99,\"weight\":0.3,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":15},{\"name\":\"Body Lotion\",\"category\":\"COSMETICS_PERSONAL_CARES\",\"price\":11.99,\"weight\":0.2,\"validityRange\":365,\"length\":5,\"width\":5,\"height\":15},{\"name\":\"Running Shoes\",\"category\":\"CLOTHING_SHOE\",\"price\":79.99,\"weight\":0.6,\"validityRange\":12,\"length\":30,\"width\":10,\"height\":15},{\"name\":\"Sports Leggings\",\"category\":\"CLOTHING_SHOE\",\"price\":24.99,\"weight\":0.25,\"validityRange\":12,\"length\":20,\"width\":15,\"height\":5},{\"name\":\"Athletic Shorts\",\"category\":\"CLOTHING_SHOE\",\"price\":19.99,\"weight\":0.15,\"validityRange\":6,\"length\":20,\"width\":15,\"height\":2},{\"name\":\"Sports Jacket\",\"category\":\"CLOTHING_SHOE\",\"price\":49.99,\"weight\":0.6,\"validityRange\":1095,\"length\":30,\"width\":20,\"height\":5},{\"name\":\"Lifestyle T-shirt\",\"category\":\"CLOTHING_SHOE\",\"price\":19.99,\"weight\":0.2,\"validityRange\":365,\"length\":25,\"width\":15,\"height\":5},{\"name\":\"Sports Bag\",\"category\":\"CLOTHING_SHOE\",\"price\":29.99,\"weight\":0.8,\"validityRange\":1095,\"length\":45,\"width\":25,\"height\":25},{\"name\":\"Meal Prep Bag\",\"category\":\"CLOTHING_SHOE\",\"price\":39.99,\"weight\":0.7,\"validityRange\":365,\"length\":30,\"width\":20,\"height\":20},{\"name\":\"Handheld Blender\",\"category\":\"TECHNOLOGY_HOUSE\",\"price\":29.99,\"weight\":0.7,\"validityRange\":1095,\"length\":20,\"width\":10,\"height\":10},{\"name\":\"Shaker Bottle\",\"category\":\"TECHNOLOGY_HOUSE\",\"price\":9.99,\"weight\":0.2,\"validityRange\":365,\"length\":10,\"width\":10,\"height\":20},{\"name\":\"Water Bottle\",\"category\":\"TECHNOLOGY_HOUSE\",\"price\":14.99,\"weight\":0.3,\"validityRange\":1095,\"length\":10,\"width\":10,\"height\":20}]";

        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = null;
        try {
            products = objectMapper.readValue(productsJson, new TypeReference<>(){});
            List<ProductPackageType> productPackageTypes = productBean.getAllProductPackageTypes();
            HashSet<Long> productPackagesByType = new HashSet<>();

            // choose just a max of x packages for each product
            // none of the package type will be repeated
            int max = ProductPackageLevel.values().length;
            int min = 1;
            for (Product product : products) {
                productPackagesByType.clear();
                for (int i = 0; i < new Random().nextInt(max - min + 1) + min; i++) {
                    productPackagesByType.add(productPackageTypes.get(new Random().nextInt(productPackageTypes.size())).getId());
                }
                productBean.create(product.getName(), product.getCategory(), product.getPrice(), product.getWeight(), product.getValidityRange(), product.getLength(), product.getWidth(), product.getHeight(), productPackagesByType);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    private void createProductPackageTypes() {
        String productPackages = "[{\"name\":\"duplex PE+AL\",\"cost\":2.99,\"dimension\":\"10x10x5cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"cartão microcanelado\",\"cost\":1.49,\"dimension\":\"15x15x7cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"Papel conformado/ cup cake wrappers\",\"cost\":0.99,\"dimension\":\"6x6x3cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"Cartolina 300g\",\"cost\":0.75,\"dimension\":\"20x20x5cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"PE cristal\",\"cost\":1.25,\"dimension\":\"12x12x8cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"Frasco PET sleeve\",\"cost\":3.99,\"dimension\":\"15x10x10cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"Tampa PP\",\"cost\":0.5,\"dimension\":\"5x5x2cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"DOYPACK\",\"cost\":1.99,\"dimension\":\"10x15x3cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"PP + AL/ PE + AL\",\"cost\":2.49,\"dimension\":\"8x8x8cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"DOYPACK POUCH\",\"cost\":2.99,\"dimension\":\"12x18x5cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"Frasco PET\",\"cost\":2.25,\"dimension\":\"10x15x10cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"Saco PE + AL\",\"cost\":0.99,\"dimension\":\"20x30x5cm\",\"sustainable\":false,\"smart\":true},{\"name\":\"PEFC\",\"cost\":0.25,\"dimension\":\"10x10x1cm\",\"sustainable\":true,\"smart\":false},{\"name\":\"PE laminado\",\"cost\":1.75,\"dimension\":\"10x15x5cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"tetrapak\",\"cost\":1.99,\"dimension\":\"15x20x10cm\",\"sustainable\":true,\"smart\":true},{\"name\":\"Filme PE\",\"cost\":0.5,\"dimension\":\"10x10x1cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"opérculo filme PE\",\"cost\":0.25,\"dimension\":\"5x5x1cm\",\"sustainable\":false,\"smart\":false},{\"name\":\"colher doseadora 70ml PP\",\"cost\":0.15,\"dimension\":\"10x5x1cm\",\"sustainable\":false,\"smart\":false}]\n";

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductPackageType> productPackageTypes = null;
        try {
            productPackageTypes = objectMapper.readValue(productPackages, new TypeReference<List<ProductPackageType>>(){});

            for (ProductPackageType productPackageType : productPackageTypes) {
                System.out.println(productPackageType.getName());
                productPackageType.setResistance(ResistenceType.values()[new Random().nextInt(ResistenceType.values().length)]);
                entityManager.persist(productPackageType);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void createOrderPackages() {
        List<OrderPackageType> allOrderPackageTypes = orderPackageTypeBean.getAllOrderPackageTypes();

        int max = allOrderPackageTypes.size() - 1;
        int min = 0;
        int i = 0;
        for (Order order : orderBean.getAllOrders(0, 500)) {
            orderPackageBean.create(
                    allOrderPackageTypes.get(new Random().nextInt(max - min + 1) + min).getId(),
                    order.getTrackingNumber()
            );
            i++;
            System.out.println("Order package "+i+" created");
        }
    }

    private void createObservers() {
        observerBean.create("Temperature Sensor");
        observerBean.create("Humidity Sensor");
        observerBean.create("Location Sensor");
    }

    private void createObservations() {
        Faker faker = new Faker();
        String details = "{\"key1\": \"value1\", \"key2\": \"value2\"}";

        createOrderPackageObservations(faker, details);
        createProductPackageObservations(faker, details);

        System.out.println("Observations created");
    }

    private void createOrderPackageObservations(Faker faker, String details) {

        List<Observation> observations = new ArrayList<>();
        int i = 0;

        List<OrderPackage> orderPackages =  orderPackageBean.getAllSmartOrderPackages();
        int totalObser = 0 ;
        for (OrderPackage orderPackage : orderPackages) {

            // create observations in 80% of the order packages
            if (faker.random().nextInt(1, 100) <= 80) {
                Timestamp date1 = faker.date().future(1, TimeUnit.DAYS, Timestamp.valueOf(orderPackage.getOrder().getDate()));
                Timestamp date2 = faker.date().future(4, TimeUnit.DAYS, date1);

                Order order = orderPackage.getOrder();
                order.setState(OrderState.IN_TRANSIT);

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
        List<OrderLineProductPackage> orderLineProductPackages =  orderLineProductPackageBean.getAllSmartProductPackages(500);
        int totalObser= 0 ;
        for (OrderLineProductPackage productPackage : orderLineProductPackages) {
            Timestamp date = faker.date().future(1, TimeUnit.DAYS, Timestamp.valueOf(productPackage.getOrderLineProductRelation().getOrderLine().getOrder().getDate()));

            Order order = productPackage.getOrderLineProductRelation().getOrderLine().getOrder();
            order.setState(OrderState.IN_TRANSIT);

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

    private void createOrderPackageTypes() {
        List<OrderPackageType> packages = new LinkedList<>();

        packages.add(new OrderPackageType("Cartao", 20, "10x10x10", true, ResistenceType.MEDIUM, false));
        packages.add(new OrderPackageType("Palete", 10, "10x10x10", false, ResistenceType.LOW, false));
        packages.add(new OrderPackageType("CAIXA ISOMÉTRICA EPS", 40, "10x10x10", false, ResistenceType.HIGH, true));

        for (OrderPackageType orderPackageType : packages) {
            entityManager.persist(orderPackageType);
        }

    }

    private void createOrders() {
        Faker faker = new Faker();
        List<String> carriers = new ArrayList<>(List.of("DHL", "CTT", "DPD", "NACEX"));
        List<String> sources = new ArrayList<>(List.of("Porto", "Coimbra", "Lisboa"));

        int minProductsPerOrder = 5;
        int maxProductsPerOrder = 20;
        int minProductQuantity = 2;
        int maxProductQuantity = 10;
        Map<Long, Integer> productsQuantities = new HashMap<>();


        for (int i = 0; i < 100; i++) {
            productsQuantities.clear();
            for (long j = 0; j < faker.random().nextInt(minProductsPerOrder, maxProductsPerOrder); j++) {
                productsQuantities.put(j+1, faker.random().nextInt(minProductQuantity, maxProductQuantity));
            }
            orderBean.create(
                    faker.date().past(100, TimeUnit.DAYS).toString(),
                    productsQuantities,
                    sources.get(faker.random().nextInt(0, sources.size()-1)),
                    faker.address().cityName(),
                    carriers.get(faker.random().nextInt(0, carriers.size()-1)),
                    List.of("air", "ground").subList(0, faker.random().nextInt(1, 2)));

            System.out.println("Order "+i+" created");
        }
    }

}
