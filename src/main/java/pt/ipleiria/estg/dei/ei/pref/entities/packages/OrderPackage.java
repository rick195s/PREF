package pt.ipleiria.estg.dei.ei.pref.entities.packages;

import pt.ipleiria.estg.dei.ei.pref.entities.Order;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "order_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrderPackages",
                query = "SELECT o FROM OrderPackage o ORDER BY o.id" // JPQL
        )})
public class OrderPackage extends ObservablePackage<OrderPackageType> implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderPackage() {

    }

    public OrderPackage(Order order, OrderPackageType orderPackageType) {
        super(orderPackageType);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
