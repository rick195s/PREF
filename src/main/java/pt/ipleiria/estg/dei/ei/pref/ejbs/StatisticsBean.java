package pt.ipleiria.estg.dei.ei.pref.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StatisticsBean {
    @PersistenceContext
    private EntityManager entityManager;

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
}
