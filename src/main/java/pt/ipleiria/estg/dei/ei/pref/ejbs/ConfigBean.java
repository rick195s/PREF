package pt.ipleiria.estg.dei.ei.pref.ejbs;

import net.datafaker.Faker;
import pt.ipleiria.estg.dei.ei.pref.enumerators.OrderState;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton
public class ConfigBean {

    List<PackageMaterialType> poliAl = List.of(PackageMaterialType.POLIETILENO, PackageMaterialType.ALUMINIO);
    List<PackageMaterialType> cartao = List.of(PackageMaterialType.CARTAO);
    List<PackageMaterialType> polipla = List.of(PackageMaterialType.POLIETILENO, PackageMaterialType.PLASTICO);

    @EJB
    OrderBean orderBean;

    @EJB
    SimplePackageBean simplePackageBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");

        createOrders();

        System.out.println("Orders created");

        simplePackageBean.create(1, "duplex PE+AL", "10x10x10", poliAl, PackageCategory.SIMPLE);
        simplePackageBean.create(2, "cartão microcanelado", "10x10x10", cartao, PackageCategory.SIMPLE);
        simplePackageBean.create(3, "PE cristal", "10x10x10", polipla, PackageCategory.SIMPLE);
        simplePackageBean.create(4, "PE laminado", "10x10x10", poliAl, PackageCategory.SIMPLE);
        simplePackageBean.create(5, "DOYPACK PP+AL/PE+AL", "10x10x10", polipla, PackageCategory.SIMPLE);

    }

    private void createOrders() {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            orderBean.create(i,
                    faker.date().past(2, TimeUnit.DAYS).toString(),
                    List.of("Product 1", "Product 2"),
                    faker.address().cityName(),
                    faker.address().cityName(),
                    OrderState.PENDING);
        }
    }
}
