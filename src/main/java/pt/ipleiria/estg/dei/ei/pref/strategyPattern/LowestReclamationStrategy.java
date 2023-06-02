package pt.ipleiria.estg.dei.ei.pref.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import java.util.List;

public class LowestReclamationStrategy implements PackageSelectionStrategy {
    @Override
    public OrderPackageType selectPackage(List<OrderPackage> orderPackages) {
        if (orderPackages.isEmpty()) {
            return null;  // Or handle the empty list case appropriately
        }

        OrderPackage selectedPackage = orderPackages.get(0);
        //change this for reclamations
        int lowestReclamations = selectedPackage.getObservations().size();

        for (int i = 1; i < orderPackages.size(); i++) {
            OrderPackage currentPackage = orderPackages.get(i);
            //change this for reclamations
            int currentReclamations = currentPackage.getObservations().size();

            //if the reclamations getted now are lower than the lowest reclamations registered
            if (currentReclamations < lowestReclamations) {
                lowestReclamations = currentReclamations;
                selectedPackage = currentPackage;
            }
        }

        OrderPackageType orderPackageTypeSuggested = selectedPackage.getSimplePackageType();

        return orderPackageTypeSuggested;
    }
}
