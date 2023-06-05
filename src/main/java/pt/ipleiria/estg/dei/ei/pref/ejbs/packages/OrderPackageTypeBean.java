package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;
import pt.ipleiria.estg.dei.ei.pref.strategyPattern.BigestNumberObservationsStrategy;
import pt.ipleiria.estg.dei.ei.pref.strategyPattern.PackageSelectionContext;
import pt.ipleiria.estg.dei.ei.pref.strategyPattern.PackageSelectionStrategy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderPackageTypeBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderPackageBean orderPackageBean;

    public OrderPackageType findOrFail(String id) {
        return entityManager.find(OrderPackageType.class, id);
    }

    public List<OrderPackageType> getAllOrderPackageTypes() {
        return (List<OrderPackageType>) entityManager.createNamedQuery("getAllOrderPackageTypes").getResultList();
    }

    public List<OrderPackageType> getAllOrderPackageTypesWithId(List<String> ids) {
        return (List<OrderPackageType>) entityManager.createNamedQuery("getAllOrderPackageTypesWithId")
                .setParameter("ids", ids)
                .getResultList();
    }

    public OrderPackageType suggestPackage(String strategy) {
        List<OrderPackage> orderPackages = orderPackageBean.getAllOrderPackages();
        PackageSelectionContext context = new PackageSelectionContext();
        if (strategy.equals("biggestNumberObservations")) {
            context.setStrategy(new BigestNumberObservationsStrategy());
        }
        OrderPackageType orderPackageType = context.selectPackage(orderPackages);
        return orderPackageType;
    }

    public List<String> getAllStrategies() {
        PackageSelectionContext context = new PackageSelectionContext();
        return context.getAllStrategies();
    }
}
