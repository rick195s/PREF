package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(int id, String name) {
        // create simple package and persist it
        SimplePackage simplePackage = new SimplePackage(id, name);

        entityManager.persist(simplePackage);

        System.out.println("Package created!");
    }
}
