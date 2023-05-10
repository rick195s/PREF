package pt.ipleiria.estg.dei.ei.pref.ejbs.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ObservablePackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public ObservablePackage findOrFail(long id) {
        return entityManager.find(ObservablePackage.class, id);
    }

}
