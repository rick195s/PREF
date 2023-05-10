package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ResistenceType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        name = "order_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderPackages",
                query = "SELECT o FROM OrderPackage o ORDER BY o.id" // JPQL
        )})
public class OrderPackage extends SimplePackage implements Serializable {

    @ManyToMany(mappedBy = "orderPackages", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders;


    public OrderPackage() {
        this.orders = new LinkedList<>();
    }

    public OrderPackage(String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart) {
        super(name, cost, dimension, isSustainable, resistance, isSmart);
        this.orders = new LinkedList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
