package pt.ipleiria.estg.dei.ei.pref.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        name = "simple_packages"
)
public class SimplePackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected String name;

    public SimplePackage() {
    }

    public SimplePackage(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
