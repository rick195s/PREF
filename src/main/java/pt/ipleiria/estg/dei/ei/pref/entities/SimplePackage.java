package pt.ipleiria.estg.dei.ei.pref.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "simple_packages"
)
public class SimplePackage {
    @Id
    @NotNull
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
