package pt.ipleiria.estg.dei.ei.pref.ejbs;


import pt.ipleiria.estg.dei.ei.pref.entities.SimplePackage;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    PackageBean packageBean;


    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EfE!");
        packageBean.create();
    }
}
