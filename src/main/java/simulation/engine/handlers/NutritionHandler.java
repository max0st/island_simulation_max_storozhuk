package simulation.engine.handlers;

import organisms.animals.Animal;
import simulation.gamefield.Cell;

import java.util.List;

public class NutritionHandler {
    public void handleNutrition(Cell cell) {
        List<Animal> animalsInCell = cell.getAnimals();

        for (Animal animal : animalsInCell) {
            double ateWeight = animal.eat(cell);
            updateSaturation(animal, ateWeight);
        }
    }

    private void updateSaturation(Animal animal, double ateWeight) {
        double foodRequired = animal.getFoodRequiredForSaturation();
        double currentSaturation = animal.getSaturation();

        double newSaturation = Math.min(currentSaturation + ateWeight, foodRequired);

        animal.setSaturation(newSaturation);
    }

    public void checkAndHandleDeath(Cell cell) {
        cell.getAnimals().stream()
                .filter(animal2 -> animal2.getSaturation() == 0.0)
                .forEach(cell::removeAnimal);
    }

    public void decreaseSaturation(Cell cell) {
        cell.getAnimals().stream()
                .filter(animal2 -> animal2.getSaturation() > 0.0)
                .forEach(Animal::decreaseSaturation);
    }
}
