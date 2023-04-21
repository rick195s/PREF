package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.*;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Stateless
public class PackageLogBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private SimplePackageBean simplePackageBean;

    public PackageLog create(/*long simplePackageId, */String location, float temperature, float humidity) throws MyEntityNotFoundException {
        PackageLog packageLog = new PackageLog(
                Calendar.getInstance().getTime().toString(),
                location,
                temperature,
                humidity
        );

        /*SimplePackage simplePackage = simplePackageBean.findOrFail(simplePackageId);
        if (simplePackage == null) {
            throw new MyEntityNotFoundException("Package not found");
        }

        packageLog.setSimplePackage(simplePackage);*/

        entityManager.persist(packageLog);

        return packageLog;
    }

    public List<PackageLog> getAllPackageLogs(long simplePackageId) throws MyEntityNotFoundException {
        SimplePackage simplePackage = simplePackageBean.findOrFail(simplePackageId);
        if (simplePackage == null) {
            throw new MyEntityNotFoundException("Package not found");
        }
        return (List<PackageLog>) entityManager.createNamedQuery("getAllPackageLogs")
                .setParameter("simplePackageId", simplePackageId)
                .getResultList();
    }
}
