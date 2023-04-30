package pt.ipleiria.estg.dei.ei.pref.ejbs.pattern;

import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ObserverBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Observer findOrFail(long id) {
        return entityManager.find(Observer.class, id);
    }

    public List<Observer> getAllObservers() {
        return (List<Observer>) entityManager.createNamedQuery("getAllObservers").getResultList();
    }

    public Observer create(String type) {
        Observer observer = new Observer(type);
        entityManager.persist(observer);
        return observer;
    }
}
