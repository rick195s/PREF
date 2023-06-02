package pt.ipleiria.estg.dei.ei.pref.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import java.util.List;

public class PackageSelectionContext {
    private PackageSelectionStrategy strategy;

    public void setStrategy(PackageSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public OrderPackageType selectPackage(List<OrderPackage> orderPackages) {
        return strategy.selectPackage(orderPackages);
    }
}
