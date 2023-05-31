package pt.ipleiria.estg.dei.ei.pref.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;

import java.util.List;

public class PackageSelectionContext {
    private PackageSelectionStrategy strategy;

    public void setStrategy(PackageSelectionStrategy strategy) {
        this.strategy = strategy;
    }

    public ObservablePackage selectPackage(List<ObservablePackage> observablePackages) {
        return strategy.selectPackage(observablePackages);
    }
}
