package pt.ipleiria.estg.dei.ei.pref.ejbs;

import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageMaterialType;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PackageCategory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.List;

@Startup
@Singleton
public class ConfigBean {

    List<PackageMaterialType> poliAl = List.of(PackageMaterialType.POLIETILENO, PackageMaterialType.ALUMINIO);
    List<PackageMaterialType> cartao = List.of(PackageMaterialType.CARTAO);
    List<PackageMaterialType> polipla = List.of(PackageMaterialType.POLIETILENO, PackageMaterialType.PLASTICO);

    @EJB
    OrderBean orderBean;

    SimplePackageBean simplePackageBean;

    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");

        packageBean.create(1, "Simple package");
        orderBean.create(1, "2020-01-01", "Client", "Supplier", List.of("Product 1", "Product 2"), "Source", "Destination", "State");

        System.out.println(orderBean.find(1).getProducts().get(0));

        simplePackageBean.create(1, "duplex PE+AL", "10x10x10", poliAl, PackageCategory.SIMPLE);
        simplePackageBean.create(2, "cartão microcanelado", "10x10x10", cartao, PackageCategory.SIMPLE);
        simplePackageBean.create(3, "PE cristal", "10x10x10", polipla, PackageCategory.SIMPLE);
        simplePackageBean.create(4, "PE laminado", "10x10x10", poliAl, PackageCategory.SIMPLE);
        simplePackageBean.create(5, "DOYPACK PP+AL/PE+AL", "10x10x10", polipla, PackageCategory.SIMPLE);

    }
}
