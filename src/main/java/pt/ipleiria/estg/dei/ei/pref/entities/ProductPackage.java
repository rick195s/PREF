package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
        name = "product_packages"
)
@NamedQueries({
        @NamedQuery(
                name = "getAllProductPackages",
                query = "SELECT p FROM ProductPackage p ORDER BY p.id" // JPQL
        )})
public class ProductPackage implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "productPackage")
    private List<ProductPackageRelation> productPackageRelations;

    public ProductPackage(String name) {
        this.name = name;
    }

    public ProductPackage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getProductPackagesJson(){return "[{\"name\": \"duplex PE+AL\"}, {\"name\": \"cartão microcanelado\"}, {\"name\": \"Papel conformado/ cup cake wrappers\"}, {\"name\": \"Cartolina 300g\"}, {\"name\": \"PE cristal\"}, {\"name\": \"Frasco PET sleeve\"}, {\"name\": \"Tampa PP\"}, {\"name\": \"DOYPACK\"}, {\"name\": \"PP + AL/ PE + AL\"}, {\"name\": \"DOYPACK POUCH\"}, {\"name\": \"Frasco PET\"}, {\"name\": \"Saco PE + AL\"}, {\"name\": \"PEFC\"}, {\"name\": \"PE laminado\"}, {\"name\": \"tetrapak\"}, {\"name\": \"Filme PE\"}, {\"name\": \"opérculo filme PE\"}, {\"name\": \"colher doseadora 70ml PP\"}]";}
}
