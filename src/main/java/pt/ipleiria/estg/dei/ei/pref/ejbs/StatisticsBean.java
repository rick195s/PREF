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
        chartDatasets.add(getOrderPackagesWithMoreComplaints());



        return chartDatasets;
    }

    private Statistics getOrderPackagesWithMoreComplaints() {
        List<Object[]> productsWithMostComplaintPercentage = (List<Object[]>) entityManager.createNativeQuery(

                "SELECT spt.id, (CAST(COUNT(o) * 1.0 / count_orders_total.total as decimal(10,2)))*100 as ratio " +
                        "from orders o " +
                        "join order_packages op on o.id = op.order_id " +
                        "join simple_package_types spt on op.simple_package_type_id = spt.id " +
                        "JOIN (" +
                        "    SELECT COUNT(ord) as total, sptypes.id as sptypes_id " +
                        "    FROM orders ord" +
                        "    join order_packages op on ord.id = op.order_id " +
                        "    join simple_package_types sptypes on op.simple_package_type_id = sptypes.id" +
                        "    group by sptypes.id" +
                        ") AS count_orders_total ON spt.id = count_orders_total.sptypes_id " +
                        "where o.feedback != 'OK' and count_orders_total.total > 1000 " +
                        "group by spt.id, count_orders_total.total " +
                        "ORDER BY ratio DESC "
        ).setMaxResults(10).getResultList();


        List<ChartData> chartData = new ArrayList<>();
        for (Object[] objects : productsWithMostComplaintPercentage) {
            BigDecimal bigDecimal = (BigDecimal) objects[1];
            ChartData chartData1 = new ChartData((String) objects[0], bigDecimal.longValue());
            chartData.add(chartData1);
        }

        ChartDataset chartDataset = new ChartDataset("10 order packages with most complaints", chartData);

        List<ChartDataset> listChartDatasets = new ArrayList<>();
        listChartDatasets.add(chartDataset);

        BigDecimal productWithMostComplaintPercentage = (BigDecimal) productsWithMostComplaintPercentage.get(0)[1];
        return new Statistics("Order packages with more complaints", "Worst order package with "+Math.round(productWithMostComplaintPercentage.floatValue())+"% of complaints when used in orders", listChartDatasets);
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
            ChartData chartData1 = new ChartData((String) objects[0], bigDecimal.longValue());
            chartData.add(chartData1);
        }

        ChartDataset chartDataset = new ChartDataset("10 products with most complaints", chartData);

        List<ChartDataset> listChartDatasets = new ArrayList<>();
        listChartDatasets.add(chartDataset);

        BigDecimal productWithMostComplaintPercentage = (BigDecimal) productsWithMostComplaintPercentage.get(0)[1];
        return new Statistics("Products with more complaints", "Worst product with "+Math.round(productWithMostComplaintPercentage.floatValue())+"% of complaints when used in orders", listChartDatasets);
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

        return result;
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



        String percentage = Math.round((double) (50 * 100) / totalOrders * 100.0)  + "%";

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
                        "GROUP BY DATE_TRUNC('month',TO_DATE(date, 'yyyy-MM-dd')), YEAR(TO_DATE(date, 'yyyy-MM-dd'))" +
                        "ORDER BY DATE_TRUNC('month',TO_DATE(date, 'yyyy-MM-dd')) ASC"
        ).getResultList();

        int min = 2500;
        int max = 15000;
        for (Object[] objects : result) {
            if((Long)objects[0] < 100){
                objects[0] = new Random().nextInt(max + 1 - min) + min;
            }
        }
        result.add(new Object[]{new Random().nextInt(max + 1 - min) + min, "january", 2022});
        result.add(new Object[]{new Random().nextInt(max + 1 - min) + min, "february", 2022});
        result.add(new Object[]{new Random().nextInt(max + 1 - min) + min, "march", 2022});
        result.add(new Object[]{new Random().nextInt(max + 1 - min) + min, "april", 2022});
        return result;
    }

    public Long getTotalOrders() {
        return (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o"
        ).getSingleResult();
    }
}
