package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartData;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.ChartDataset;
import pt.ipleiria.estg.dei.ei.pref.entities.statistics.Statistics;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class StatisticsBean {
    @PersistenceContext
    private EntityManager entityManager;

    LocalDate today = LocalDate.now();

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

    public Object getStatisticsDashboardUsers(String role) {
        Object result = null;
        switch (role) {
            case "LOGISTICS_MANAGER":
                result = getStatisticsLogisticsManager();
                break;
        }
        return result;
    }

    public Object getStatisticsLogisticsManager(){
        List<Object> chartDatasets = new ArrayList<>();

        // Primeiro objeto
        Statistics statistics1 = new Statistics("Percentage of Orders In Transit", getPercentageOfOrdersInTransit().toString()+" %", new ArrayList<>());
        chartDatasets.add(statistics1);

        // Segundo objeto
        List<ChartData> chartData1 = new ArrayList<>();
        addDataToChartData(chartData1, OrderState.RETURNED);

        ChartDataset chartDataset1 = new ChartDataset("Number of Orders Returned", chartData1);

        List<ChartDataset> listChartDatasets1 = new ArrayList<>();
        listChartDatasets1.add(chartDataset1);

        Statistics statistics2 = new Statistics("Percentage of Orders Returned", getPercentageOfOrdersReturned() + " %", listChartDatasets1);
        chartDatasets.add(statistics2);

        //terceiro objeto
        List<ChartData> chartData2 = new ArrayList<>();
        addDataToChartData(chartData2, OrderState.COMPLAINED);

        ChartDataset chartDataset2 = new ChartDataset("Number of Orders Complained", chartData2);

        List<ChartDataset> listChartDatasets2 = new ArrayList<>();
        listChartDatasets2.add(chartDataset2);

        Statistics statistics3 = new Statistics("Percentage of Orders Complained", getPercentageOfOrdersComplained() + " %", listChartDatasets2);
        chartDatasets.add(statistics3);

        return chartDatasets;
    }

    public List<ChartData> addDataToChartData(List<ChartData> data, OrderState ordersState) {
        int count = 0;
        while (data.size() < 5) {
            LocalDate date = today.minusDays(count);
            // Verificar se a data é um dia útil (não é final de semana e não está na lista de feriados)
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                String formattedDate = date.format(formatter);
                data.add(new ChartData(formattedDate, getOrdersFromDay(formattedDate, ordersState)));
            }
            count++;
        }
        return data;
    }
    //get orders from a day
    public Long getOrdersFromDay(String date, OrderState ordersState){
        return (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.state = :ordersState AND o.date = :date"
        ).setParameter("ordersState", ordersState)
                .setParameter("date", date)
                .getSingleResult();
    }

    public Long getPercentageOfOrdersInTransit() {
        Long ordersInTransit = (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.state = 'IN_TRANSIT'"
        ).getSingleResult();

        Long totalOrders = getTotalOrders();

        return ordersInTransit * 100 / totalOrders;
    }

    public Long getPercentageOfOrdersReturned() {
        //obter numero de orders in transit
        Long ordersReturned = (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.state = 'RETURNED'"
        ).getSingleResult();

        Long totalOrders = getTotalOrders();

        return ordersReturned * 100 / totalOrders;
    }

    public Long getPercentageOfOrdersComplained() {
        //obter numero de orders in transit
        Long ordersComplained = (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o " +
                        "WHERE o.state = 'COMPLAINED'"
        ).getSingleResult();

        Long totalOrders = getTotalOrders();

        return ordersComplained * 100 / totalOrders;
    }

    public Long getTotalOrders() {
        return (Long) entityManager.createQuery(
                "SELECT COUNT(o) " +
                        "FROM Order o"
        ).getSingleResult();
    }
}
