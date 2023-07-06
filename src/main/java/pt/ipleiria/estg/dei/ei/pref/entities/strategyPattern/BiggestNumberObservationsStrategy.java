package pt.ipleiria.estg.dei.ei.pref.entities.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackage;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.OrderPackageType;

import java.util.List;

public class BiggestNumberObservationsStrategy implements PackageSelectionStrategy {
    @Override
    public OrderPackageType selectPackage(List<OrderPackage> orderPackages) {
        if (orderPackages.isEmpty()) {
            return null;  // Or handle the empty list case appropriately
        }

        OrderPackage selectedPackage = orderPackages.get(0);
        int biggestNumberObservations = selectedPackage.getObservations().size();

        for (int i = 1; i < orderPackages.size(); i++) {
            OrderPackage currentPackage = orderPackages.get(i);
            //change this for reclamations
            int currentBiggestNumberObservations = currentPackage.getObservations().size();

            //if the observations getted now are bigger than the previous ones
            if (currentBiggestNumberObservations < biggestNumberObservations) {
                biggestNumberObservations = currentBiggestNumberObservations;
                selectedPackage = currentPackage;
            }
        }

        OrderPackageType orderPackageTypeSuggested = selectedPackage.getSimplePackageType();

        return orderPackageTypeSuggested;
    }
}
