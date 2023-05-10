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
public class OrderPackage extends OrderPackageType implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderPackage() {

    }

    public OrderPackage(String name, double cost, String dimension, boolean isSustainable, ResistenceType resistance, boolean isSmart, Order order) {
        super(name, cost, dimension, isSustainable, resistance, isSmart);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
