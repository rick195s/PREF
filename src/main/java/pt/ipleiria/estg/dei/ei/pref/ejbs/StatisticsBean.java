package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.OrderLine;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartData;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartDataset;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.Statistics;
import pt.ipleiria.estg.dei.ei.pref.enumerators.Role;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class StatisticsBean {
    @PersistenceContext
    private EntityManager entityManager;

    //get todays date
    LocalDate today = LocalDate.now();

    //date format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

    //statistics for Logistics Manager
    public Object getStatisticsLogisticsManager() {
        List<Object> chartDatasets = new ArrayList<>();

        // First object
        Statistics statistics1 = new Statistics("Percentage of Orders In Transit", Math.round(getPercentageOfOrdersInTransit() * 100.0) / 100.0 + " %", new ArrayList<>());
        chartDatasets.add(statistics1);

        // Second object
        //Add data for chart
        List<ChartData> chartData1 = new ArrayList<>();
        Map<String, Object> map1 = addDateAndTotalToChartData(chartData1, "COM DEVOLUÇÃO");
        chartData1 = (List<ChartData>) map1.get("chartData");

        ChartDataset chartDataset1 = new ChartDataset("Number of Orders Returned", chartData1);

        List<ChartDataset> listChartDatasets1 = new ArrayList<>();
        listChartDatasets1.add(chartDataset1);

        Double percentageOfOrdersReturned = (Double) map1.get("total");
        //Add data for card
        Statistics statistics2 = new Statistics("Percentage of Orders Returned (Last 5 days)", Math.round(percentageOfOrdersReturned * 100.0) / 100.0 + " %", listChartDatasets1);
        chartDatasets.add(statistics2);

        // Third object
        //Add data for chart
        List<ChartData> chartData2 = new ArrayList<>();
        Map<String, Object> map2 = addDateAndTotalToChartData(chartData2, "COM RECLAMAÇÃO");
        chartData2 = (List<ChartData>) map2.get("chartData");

        ChartDataset chartDataset2 = new ChartDataset("Number of Orders Complaint", chartData2);

        List<ChartDataset> listChartDatasets2 = new ArrayList<>();
        listChartDatasets2.add(chartDataset2);

        Double percentageOfOrdersComplaint = (Double) map2.get("total");
        //Add data for card
        Statistics statistics3 = new Statistics("Percentage of Orders Complaint (Last 5 days)", Math.round(percentageOfOrdersComplaint * 100.0) / 100.0 + " %", listChartDatasets2);
        chartDatasets.add(statistics3);

        return chartDatasets;
    }

    public Object getStatisticsAnalyst() {
        List<Object> chartDatasets = new ArrayList<>();
        // First object
        //Add data for chart
        List<ChartData> chartData1 = new ArrayList<>();
        //first line
        Map<String, Object> map1 = addDateAndTotalToChartData(chartData1, "COM DEVOLUÇÃO");
        chartData1 = (List<ChartData>) map1.get("chartData");

        ChartDataset chartDataset1 = new ChartDataset("Number of Orders Returned", chartData1);

        List<ChartDataset> listChartDatasets1 = new ArrayList<>();
        listChartDatasets1.add(chartDataset1);

        Double percentageOfOrdersReturned = (Double) map1.get("total");
        //Second Line
        List<ChartData> chartData2 = new ArrayList<>();
        Map<String, Object> map2 = addDateAndTotalToChartData(chartData2, "OK");
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
        Map<String, Object> map3 = getPackagesFromOrdersWithMostComplaints();
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

        return chartDatasets;
    }
    
    //add data to chart
    public Map<String, Object> addDateAndTotalToChartData(List<ChartData> data, String orderFeedback) {
        int count = 0;
        Double total = 0.0;
        while (data.size() < 5) {
            LocalDate date = today.minusDays(count);
            // Verificar se a data é um dia útil (não é final de semana e não está na lista de feriados)
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                String formattedDate = date.format(formatter);
                Long result = getOrdersFromDay(formattedDate, orderFeedback);
                total = total + result;
                data.add(new ChartData(formattedDate, result));
            }
            count++;
        }

        Map<String, Object> result = new HashMap<>();
        //randomize number
        total = Math.random() * 100; // TODO: remove this line
        result.put("chartData", data);
        result.put("total", total);
        return result;
    }

    //get number of orders for each day in a specific state
    public Long getOrdersFromDay(String date, String orderFeedback) {
        Long result = (Long) entityManager.createQuery(
                        "SELECT COUNT(o) " +
                                "FROM Order o " +
                                "WHERE o.feedback = :orderFeedback AND o.date = :date"
                ).setParameter("orderFeedback", orderFeedback)
                .setParameter("date", date)
                .getSingleResult();

        result = Math.round(Math.random() * 100); // TODO: remove this line

        return result;
    }

    public Map<String, Object> getPackagesFromOrdersWithMostComplaints() {
        //get orders with complaints
        List<Order> orders = entityManager.createQuery(
                "SELECT o " +
                        "FROM OrderPackage op " +
                        "JOIN op.order o  " +
                        "WHERE o.feedback = 'COM RECLAMAÇÃO' " +
                        "ORDER BY o. DESC"
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
            int index = products.indexOf(product);

            Long totalLong = Math.round(Math.random() * 100); // TODO: remove this line
            charData.add(new ChartData(product.getName(), totalLong));


        }

        Double totalDouble = Math.random() * 100; // TODO: remove this line

        map.put("chartData", charData);
        map.put("total", totalDouble);
        return map;
    }

    public Double getPercentageOfOrdersInTransit() {
        Long ordersInTransit = (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.state = 'IN_TRANSIT'"
        ).getSingleResult();

        Long totalOrders = getTotalOrders();

        ordersInTransit = Math.round(Math.random() * 100); // TODO: remove this line

        return ordersInTransit.doubleValue() * 100 / totalOrders;
    }

    public Long getTotalOrders() {
        return (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o"
        ).getSingleResult();
    }
}
