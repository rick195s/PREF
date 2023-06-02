package pt.ipleiria.estg.dei.ei.pref.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import java.util.List;

public interface PackageSelectionStrategy {
    OrderPackageType selectPackage(List<OrderPackage> orderPackages);
}


