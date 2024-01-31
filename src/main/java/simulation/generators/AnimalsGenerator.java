package simulation.generators;

import common.annotations.ConfigPath;
import config.PropertiesLoader;
import organisms.animals.Animal;
import util.AnimalReflection;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalsGenerator {
    private final AnimalReflection ANIMAL_REFLECTION = new AnimalReflection();
    private final Set<Class<? extends Animal>> SUBCLASSES = ANIMAL_REFLECTION.getAnimalSubclasses();

    public static void main(String[] args) {
        List<Animal> animals = new AnimalsGenerator().generateAnimals();
        animals.forEach(e -> System.out.println(e.toString()));
        System.out.println(animals.size());
    }

    public List<Animal> generateAnimals() {
        List<Animal> animals = new CopyOnWriteArrayList<>();

        for (Class<? extends Animal> animalSubclass : SUBCLASSES) {
            try {

                int maxAnimalsPerCellUpperBound = getMaxAnimalsPerCellUpperBound(animalSubclass);
                for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, maxAnimalsPerCellUpperBound + 1); j++) {
                    Animal newAnimal = animalSubclass.newInstance();
                    animals.add(newAnimal);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return animals;
    }

    private Integer getMaxAnimalsPerCellUpperBound(Class<? extends Animal> animalSubclass) {
        return (int) new PropertiesLoader(animalSubclass.getAnnotation(ConfigPath.class).filePath()).loadProperties().get("maxAnimalsPerCell");
    }
}
