package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartData;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartDataset;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.Statistics;
import pt.ipleiria.estg.dei.ei.pref.enumerators.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Stateless
public class StatisticsBean {
    @PersistenceContext
    private EntityManager entityManager;

    //get todays date
    LocalDate today = LocalDate.now();

    //date format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Object getTemperatureByCarrier(String carrier) {
        // get the average temperature of all orders grouped by carrier and destination to a object
        return entityManager.createQuery(
                        "SELECT o.feedback, AVG(q.value) " +
                                "FROM MeasurementObservation m " +
                                "JOIN m.quantity q " +
                                "JOIN m.observablePackage op " +
                                "JOIN op.order o " +
                                "WHERE o.carrier = :carrier AND m.phenomenonType = 'TEMPERATURE' AND TYPE(op) = OrderPackage " +
                                "GROUP BY o.feedback"

                ).setParameter("carrier", carrier)
                .getResultList();
    }

    public Object getStatisticsDashboardUsers(Role role) {
        Object result = null;
        switch (role) {
            case LOGISTICS_MANAGER:
                result = getStatisticsLogisticsManager();
                break;
            case ANALYST:
                result = getStatisticsAnalyst();
                break;
        }
        return result;
    }

    public Object getStatisticsLogisticsManager(){
        //statistics for Logistics Manager
        List<Statistics> chartDatasets = new ArrayList<>();

        chartDatasets.add(getPercentageOfOrdersInTransit());

        chartDatasets.add(getPercentageOfOrdersReturned());

        chartDatasets.add(getPercentageOfOrdersComplaint());

        return chartDatasets;
    }


    public Object getStatisticsAnalyst() {
        List<Object> chartDatasets = new ArrayList<>();
        chartDatasets.add(getPercentageOfOrdersComplaintByMonth());
        chartDatasets.add(getProductsWithMoreComplaints());

/*

        // First object
        //Add data for chart
        List<ChartData> chartData1 = new ArrayList<>();
        //first line
        Map<String, Object> map1 = addDateAndTotalToChartData( "COM DEVOLUÇÃO");
        chartData1 = (List<ChartData>) map1.get("chartData");

        ChartDataset chartDataset1 = new ChartDataset("Number of Orders Returned", chartData1);

        List<ChartDataset> listChartDatasets1 = new ArrayList<>();
        listChartDatasets1.add(chartDataset1);

        Double percentageOfOrdersReturned = (Double) map1.get("total");
        //Second Line
        List<ChartData> chartData2 = new ArrayList<>();
        Map<String, Object> map2 = addDateAndTotalToChartData( "OK");
        chartData2 = (List<ChartData>) map2.get("chartData");

        ChartDataset chartDataset2 = new ChartDataset("Number of Orders Delivered", chartData2);
        listChartDatasets1.add(chartDataset2);

        Double percentageOfOrdersDelivered = (Double) map2.get("total");

        //Add data for card
        Statistics statistics1 = new Statistics("Percentage of Orders Returned VS Delivered (Last 5 days)", Math.round(percentageOfOrdersReturned * 100.0) / 100.0 + " % returned VS " + Math.round(percentageOfOrdersDelivered * 100.0) / 100.0 + " % delivered", listChartDatasets1);
        chartDatasets.add(statistics1);

        // Second object - not implemented // TODO
        //Add data for chart
        List<ChartData> chartData3 = new ArrayList<>();
        Map<String, Object> map3 = getOrderPackagesWithMostComplaints();
        chartData3 = (List<ChartData>) map3.get("chartData");

        ChartDataset chartDataset3 = new ChartDataset("Number of Complaints", chartData3);

        List<ChartDataset> listChartDatasets2 = new ArrayList<>();
        listChartDatasets2.add(chartDataset3);

        Double percentageOfOrdersComplaint = (Double) map3.get("total");
        //Add data for card
        Statistics statistics2 = new Statistics("Percentage of Complaints for the 10 Packages with the Most Complaints", Math.round(percentageOfOrdersComplaint * 100.0) / 100.0 + " %", listChartDatasets2);
        chartDatasets.add(statistics2);

        // Third object - not implemented // TODO
        List<ChartData> chartData4 = new ArrayList<>();
        Map<String, Object> map4 = getProductsFromOrdersWithMostComplaints();
        chartData4 = (List<ChartData>) map4.get("chartData");

        ChartDataset chartDataset4 = new ChartDataset("Number of Complaints", chartData4);

        List<ChartDataset> listChartDatasets3 = new ArrayList<>();
        listChartDatasets3.add(chartDataset4);

        Double percentageOfProductsComplaint = (Double) map4.get("total");

        Statistics statistics3 = new Statistics("Percentage of Complaints for the 10 Products with the Most Complaints", Math.round(percentageOfProductsComplaint * 100.0) / 100.0 + " %", listChartDatasets3);
        chartDatasets.add(statistics3);
*/

        return chartDatasets;
    }

    private Statistics getProductsWithMoreComplaints() {

        List<Object[]> productsWithMostComplaintPercentage = (List<Object[]>) entityManager.createNativeQuery(
                "SELECT p.name,  (CAST(COUNT(o) * 1.0 / count_orders_ok.total as decimal(10,2)))*100 as ratio "
                + "FROM orders o "
                + "JOIN order_lines ol ON o.id = ol.order_id "
                + "JOIN order_line_product_relations olpr ON ol.id = olpr.order_line_id "
                + "JOIN products p ON olpr.product_id = p.id "
                + "JOIN ( "
                        + "SELECT COUNT(ord) as total, products.id as product_id "
                        + "FROM orders ord "
                        + "JOIN order_lines ol ON ord.id = ol.order_id "
                        + "JOIN order_line_product_relations olpr ON ol.id = olpr.order_line_id "
                        + "JOIN products ON olpr.product_id = products.id "
                        + "GROUP BY products.id "
                        + ") AS count_orders_ok ON p.id = count_orders_ok.product_id "
                + "WHERE o.feedback != 'OK' AND count_orders_ok.total > 1000 "
                + "GROUP BY p.name, count_orders_ok.total "
                + "ORDER BY ratio DESC "
                        ).setMaxResults(10).getResultList();


        List<ChartData> chartData = new ArrayList<>();
        for (Object[] objects : productsWithMostComplaintPercentage) {
            BigDecimal bigDecimal = (BigDecimal) objects[1];
            System.out.println(bigDecimal.longValue());

            ChartData chartData1 = new ChartData((String) objects[0], bigDecimal.longValue());
            chartData.add(chartData1);
        }

        ChartDataset chartDataset = new ChartDataset("Percentage of 10 products complaints with a minimum of 1000 orders", chartData);

        List<ChartDataset> listChartDatasets = new ArrayList<>();
        listChartDatasets.add(chartDataset);

        BigDecimal productWithMostComplaintPercentage = (BigDecimal) productsWithMostComplaintPercentage.get(0)[1];
        return new Statistics("Products with more complaints", "Product with "+Math.round(productWithMostComplaintPercentage.floatValue())+"% of complaints with min. 1000 orders", listChartDatasets);
    }

    private Statistics getPercentageOfOrdersComplaintByMonth() {

        List<Object[]> ordersNotOk = (List<Object[]>) entityManager.createQuery(
                        "SELECT COUNT(o), TO_CHAR(TO_DATE(date, 'yyyy-MM-dd'), 'month') " +
                                "FROM Order o " +
                                "WHERE o.feedback != :orderFeedback " +
                                "GROUP BY TO_CHAR(TO_DATE(date, 'yyyy-MM-dd'), 'month')"
                ).setParameter("orderFeedback", "OK")
                .getResultList();

        Long totalOrdersNotOk = (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.feedback != :orderFeedback "
        ).setParameter("orderFeedback", "OK").getSingleResult();

        Long totalOrders = (Long) entityManager.createQuery(
                        "SELECT COUNT(o) " +
                                "FROM Order o "
                ).getSingleResult();

        List<ChartData> chartData = new ArrayList<>();
        for (Object[] objects : ordersNotOk) {
            ChartData chartData1 = new ChartData((String) objects[1], (Long) objects[0]);
            chartData.add(chartData1);

        }

        ChartDataset chartDataset = new ChartDataset("Number of Complaints by month", chartData);

        List<ChartDataset> listChartDatasets = new ArrayList<>();
        listChartDatasets.add(chartDataset);

        return new Statistics("Total Complaints", Math.round(( (float) totalOrdersNotOk /totalOrders )*100) + "% ("+ totalOrdersNotOk +" in "+ totalOrders+" orders)", listChartDatasets);
    }

    //add data to chart

    public Map<String, Object> addDateAndTotalToChartData(String orderFeedback) {
        List<ChartData> data = new ArrayList<>();

        int count = 0;
        Double total = 0.0;

        String lastOrderDateString = (String) entityManager.createQuery(
                        "SELECT MAX(o.date) FROM Order o WHERE o.feedback = :orderFeedback").setParameter("orderFeedback", orderFeedback)
                .getSingleResult();

        LocalDate lastOrderDate = LocalDate.parse( lastOrderDateString.split(" ")[0], formatter);

        while (data.size() < 5) {
            LocalDate endDate = lastOrderDate.minusDays(count);
            LocalDate startDate = lastOrderDate.minusDays(count + 1);
            // Verificar se a data é um dia útil (não é final de semana e não está na lista de feriados)
            if (startDate.getDayOfWeek() != DayOfWeek.SATURDAY && startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                Long result = getOrdersFromDay(startDate.format(formatter), endDate.format(formatter), orderFeedback);
                total = total + result;
                data.add(new ChartData(startDate.format(formatter), result));
            }
            count++;
        }

        Map<String, Object> result = new HashMap<>();
        //randomize number
       // total = Math.random() * 100; // TODO: remove this line
        result.put("chartData", data);
        result.put("total", total);
        return result;
    }
    //get number of orders for each day in a specific state

    public Long getOrdersFromDay(String startDate, String endDate, String orderFeedback) {
        Long result = (Long) entityManager.createQuery(
                        "SELECT COUNT(o) " +
                                "FROM Order o " +
                                "WHERE o.feedback = :orderFeedback AND o.date >= :startDate AND o.date < :endDate"
                ).setParameter("orderFeedback", orderFeedback)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();

        //result = Math.round(Math.random() * 100); // TODO: remove this line

        return result;
    }
    public Map<String, Object> getOrderPackagesWithMostComplaints() {
        //get orders with complaints
        List<OrderPackage> orderPackages = entityManager.createQuery(
                "SELECT op " +
                        "FROM OrderPackage op "
        ).setMaxResults(10).getResultList();


        //ver quais os packages que se repetem mais vezes nestas orders
        Map<String, Object> map = new HashMap<>();
        List<OrderPackageType> orderPackageTypes = new ArrayList<>();
        List<Double> total = new ArrayList<>();

       /* for (Order order : orders) {
            for (OrderPackage orderPackage : order.getOrderPackages()) {
                if (orderPackageTypes.contains(orderPackage.getSimplePackageType())) {
                    int index = orderPackageTypes.indexOf(orderPackage.getSimplePackageType());
                    total.set(index, total.get(index) + 1);
                } else {
                    orderPackageTypes.add(orderPackage.getSimplePackageType());
                    total.add(1.0);
                }
            }
        }*/

        List<ChartData> charData = new ArrayList<>();
        for (OrderPackageType orderPackageType : orderPackageTypes) {

            Long totalLong = Math.round(Math.random() * 100); // TODO: remove this line
            charData.add(new ChartData(orderPackageType.getId(), totalLong));


        }

        Double totalDouble = Math.random() * 100; // TODO: remove this line

        map.put("chartData", charData);
        map.put("total", totalDouble);

        return map;
    }

    public Map<String, Object> getProductsFromOrdersWithMostComplaints() {
        //get orders with complaints
        List<Order> orders = entityManager.createQuery(
                "SELECT o " +
                        "FROM Order o " +
                        "WHERE o.feedback = 'COM RECLAMAÇÃO' " +
                        "ORDER BY o.id DESC"
        ).setMaxResults(10).getResultList();


        //ver quais os packages que se repetem mais vezes nessas orders
        Map<String, Object> map = new HashMap<>();
        List<Product> products = new ArrayList<>();
        List<Double> total = new ArrayList<>();

       /* for (Order order : orders) {
            for (OrderLine orderLine : order.getOrderLines()) {
                if (products.contains(orderLine.getProduct())) {
                    int index = products.indexOf(orderLine.getProduct());
                    total.set(index, total.get(index) + 1);
                } else {
                    products.add(orderLine.getProduct());
                    total.add(1.0);
                }
            }
        }*/

        //add 10 random products
        products = entityManager.createQuery(
                "SELECT p " +
                        "FROM Product p " +
                        "ORDER BY p.id DESC"
        ).setMaxResults(10).getResultList();


        List<ChartData> charData = new ArrayList<>();
        for (Product product : products) {
            Long totalLong = Math.round(Math.random() * 100); // TODO: remove this line
            charData.add(new ChartData(product.getName(), totalLong));
        }

        Double totalDouble = Math.random() * 100; // TODO: remove this line

        map.put("chartData", charData);
        map.put("total", totalDouble);
        return map;
    }

    private Statistics getPercentageOfOrdersComplaint() {
        // Third object
        //Add data for chart
        Map<String, Object> map2 = addDateAndTotalToChartData("COM RECLAMAÇÃO");

        ChartDataset chartDataset2 = new ChartDataset("Number of Orders Complaint", (List<ChartData>) map2.get("chartData"));

        List<ChartDataset> listChartDatasets2 = new ArrayList<>();
        listChartDatasets2.add(chartDataset2);

        String percentageOfOrdersComplaint =  Math.round((Double) map2.get("total") )  + "%";

        return new Statistics("Percentage of Orders Complaint (Last 5 days)", percentageOfOrdersComplaint, listChartDatasets2);
    }

    public Statistics getPercentageOfOrdersInTransit() {
        Long ordersInTransit = (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.state = 'IN_TRANSIT'"
        ).getSingleResult();

        Long totalOrders = getTotalOrders();

        ordersInTransit = Math.round(Math.random() * 100); // TODO: remove this line

        String percentage = Math.round(ordersInTransit.doubleValue() * 100 / totalOrders * 100.0)  + "%";

        return new Statistics("Percentage of Orders In Transit", percentage, new ArrayList<>());
    }

    private Statistics getPercentageOfOrdersReturned() {
        Map<String, Object> map1 = addDateAndTotalToChartData("COM DEVOLUÇÃO");
        ChartDataset chartDataset1 = new ChartDataset("Number of Orders Returned", (List<ChartData>) map1.get("chartData"));

        Double percentageOfOrdersReturned = (Double) map1.get("total");
        //Add data for card
        LinkedList<ChartDataset> chartDatasets = new LinkedList<>();
        chartDatasets.add(chartDataset1);
        return new Statistics("Percentage of Orders Returned (Last 5 days)", Math.round(percentageOfOrdersReturned )  + "%", chartDatasets);
    }

    public Object getOrdersComparation() {
        List<ChartDataset> chartDatasets = new ArrayList<>();

        List<Object[]> result =  entityManager.createQuery(
                "SELECT COUNT(o), TO_CHAR(DATE_TRUNC('month',TO_DATE(date, 'yyyy-MM-dd')), 'month'), YEAR(TO_DATE(date, 'yyyy-MM-dd')) " +
                        "FROM Order o " +
                        "GROUP BY DATE_TRUNC('month',TO_DATE(date, 'yyyy-MM-dd')), YEAR(TO_DATE(date, 'yyyy-MM-dd'))"
        ).getResultList();

        return result;
    }

    public Long getTotalOrders() {
        return (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o"
        ).getSingleResult();
    }
}
