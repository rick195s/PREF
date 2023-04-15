package pt.ipleiria.estg.dei.ei.pref.entities;

import pt.ipleiria.estg.dei.ei.pref.entities.relations.ProductPackageRelation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.ProductPackageType;

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

    @Transient
    // Aux property, real type is in ProductPackageRelation
    private ProductPackageType type;

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

    public ProductPackageType getType() {
        return type;
    }

    public void setType(ProductPackageType type) {
        this.type = type;
    }
}
