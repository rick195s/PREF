package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create() {
        SimplePackage simplePackage = new SimplePackage(1, "Simple Package");
        entityManager.persist(entityManager.merge(simplePackage));
        System.out.println("Package created!");
    }
}
