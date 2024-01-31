package simulation.engine.handlers;

import organisms.animals.Animal;
import simulation.gamefield.Cell;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReproductionHandler {
    public void reproduceAll(Cell cell) {
        reproduceAllAnimals(cell);
        reproduceAllPlants(cell);
    }

    private void reproduceAllPlants(Cell cell) {
        if (!cell.getPlants().isEmpty()) {
            cell.getPlants().get(0).reproduce(cell);
        }
    }

    private void reproduceAllAnimals(Cell cell) {
        List<Animal> animalsInCell = cell.getAnimals();

        Set<Class<? extends Animal>> uniqueAnimalClasses = animalsInCell.stream()
                .map(Animal::getClass)
                .collect(Collectors.toSet());

        uniqueAnimalClasses.forEach(animalClass -> {
            List<Animal> animalsOfType = animalsInCell.stream()
                    .filter(animalClass::isInstance)
                    .toList();

            if (!animalsOfType.isEmpty()) {
                Animal animalToReproduce = animalsOfType.get(0);
                animalToReproduce.reproduce(cell);
            }
        });
    }
}
