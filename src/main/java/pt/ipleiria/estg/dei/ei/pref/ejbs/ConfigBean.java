package pt.ipleiria.estg.dei.ei.pref.ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    PackageBean packageBean;

    @EJB
    OrderBean orderBean;


    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");
        packageBean.create(1, "Simple package");
        orderBean.create(1, "2020-01-01", "Client", "Supplier", List.of("Product 1", "Product 2"), "Source", "Destination", "State");

        System.out.println(orderBean.find(1).getProducts().get(0));
    }
}
