package pt.ipleiria.estg.dei.ei.pref.strategyPattern;

import pt.ipleiria.estg.dei.ei.pref.entities.packages.ObservablePackage;

import java.util.List;

public class LowestReclamationStrategy implements PackageSelectionStrategy {
    @Override
    public ObservablePackage selectPackage(List<ObservablePackage> observablePackages) {
        if (observablePackages.isEmpty()) {
            return null;  // Or handle the empty list case appropriately
        }

        ObservablePackage selectedPackage = observablePackages.get(0);
        /*int lowestReclamations = selectedPackage.getReclamations();

        for (int i = 1; i < observablePackages.size(); i++) {
            ObservablePackage currentPackage = observablePackages.get(i);
            int currentReclamations = currentPackage.getReclamations();

            if (currentReclamations < lowestReclamations) {
                lowestReclamations = currentReclamations;
                selectedPackage = currentPackage;
            }
        }
*/
        return selectedPackage;
    }
}
