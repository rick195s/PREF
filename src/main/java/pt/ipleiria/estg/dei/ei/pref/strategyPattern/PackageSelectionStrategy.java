package pt.ipleiria.estg.dei.ei.pref.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;

import java.util.List;

public interface PackageSelectionStrategy {
    ObservablePackage selectPackage(List<ObservablePackage> packages);
}


