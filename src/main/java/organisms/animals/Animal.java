package organisms.animals;

import common.annotations.ConfigPath;
import common.enums.Gender;
import config.PropertiesLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import organisms.interfaces.Movable;
import organisms.interfaces.Organism;
import organisms.plants.Plant;
import simulation.gamefield.Cell;
import util.AnimalReflection;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@ToString(exclude = {"PROPERTIES_LOADER", "chanceToEat", "ANIMAL_SUBCLASSES"})
public abstract class Animal implements Organism, Movable {
    private final Set<Class<? extends Animal>> ANIMAL_SUBCLASSES = new AnimalReflection().getAnimalSubclasses();
    private final Set<Class<? extends Organism>> ORGANISM_SUBCLASSES;
    private final PropertiesLoader PROPERTIES_LOADER;
    protected Gender gender;
    protected double weight;
    protected int maxAnimalsPerCell;
    @Setter
    protected int movementSpeed;
    @Setter
    protected double saturation;
    protected double foodRequiredForSaturation;
    protected Map<String, Integer> chanceToEat;

    public Animal() {
        this.ORGANISM_SUBCLASSES = new HashSet<>(ANIMAL_SUBCLASSES);
        ORGANISM_SUBCLASSES.add(Plant.class);
        this.gender = getRandomGender();
        this.saturation = 0;
        this.PROPERTIES_LOADER = new PropertiesLoader(this.getClass().getAnnotation(ConfigPath.class).filePath());
        initializeFromProperties();
    }

    private void initializeFromProperties() {
        Map<String, Object> properties = PROPERTIES_LOADER.loadProperties();
        if (properties != null) {
            this.weight = (double) properties.get("weight");
            this.maxAnimalsPerCell = (int) properties.get("maxAnimalsPerCell");
            this.movementSpeed = (int) properties.get("movementSpeed");
            this.foodRequiredForSaturation = (double) properties.get("foodRequiredForSaturation");
            this.chanceToEat = PROPERTIES_LOADER.getAnimalChances(properties, "chanceToEat");
        } else {
            throw new RuntimeException("Failed to load properties from YAML file.");
        }
    }

    public double eat(Cell currentCell) {
        List<? extends Organism> organisms = currentCell.getOrganisms();

        for (String organismType : ORGANISM_SUBCLASSES.stream().map(Class::getSimpleName).toList()) {
            List<? extends Organism> organismsOfType = organisms.stream()
                    .filter(animal -> animal.getClass().getSimpleName().equals(organismType))
                    .toList();

            if (!organismsOfType.isEmpty()) {
                Organism organismToEat = organismsOfType.get(ThreadLocalRandom.current().nextInt(organismsOfType.size()));

                int chanceToEat = this.chanceToEat.getOrDefault(organismType, 0);
                if (ThreadLocalRandom.current().nextInt(100) < chanceToEat) {
                    if (organismToEat instanceof Animal animalToEat) {
                        currentCell.removeAnimal(animalToEat);
                        return animalToEat.getWeight();
                    } else if (organismToEat instanceof Plant plantToEat) {
                        currentCell.removePlant(plantToEat);
                        return plantToEat.getWeight();
                    }
                }
            }
        }
        return 0;
    }


    @Override
    public void move(Cell currentCell, Cell targetCell) {
        if (currentCell == null || targetCell == null) {
            throw new IllegalArgumentException("Both source and target cells must be specified.");
        }

        if (!currentCell.getCellMap().get(Animal.class).contains(this)) {
            throw new IllegalStateException("The animal is not in the current cell.");
        }

        currentCell.removeAnimal(this);
        targetCell.addAnimal(this);
    }


    private int canReproduce(CopyOnWriteArrayList<? extends Animal> animals) {
        int noChild = 0;
        int minMaleCountToReproduce = 1;
        int maleCount = countAnimalsByGender(animals, Gender.MALE);
        int femaleCount = countAnimalsByGender(animals, Gender.FEMALE);

        return maleCount < minMaleCountToReproduce ? noChild : femaleCount;
    }

    private int countAnimalsByGender(List<? extends Animal> organisms, Gender gender) {
        return (int) organisms.stream()
                .filter(organism -> (organism.getClass() == this.getClass()) && (organism).getGender() == gender)
                .count();
    }

    @Override
    public void reproduce(Cell cell) {
        int childQuantity = canReproduce(cell.getAnimals());
        Class<? extends Animal> classType = this.getClass();
        try {
            for (int i = 0; i < childQuantity; i++) {
                Animal newChild = classType.newInstance();
                cell.addAnimal(newChild);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void decreaseSaturation() {
        if (saturation - getSaturation() < 0)
            this.setSaturation(0);
        else {
            this.setSaturation(saturation - getSaturation());
        }
    }

    private Gender getRandomGender() {
        return ThreadLocalRandom.current().nextBoolean() ? Gender.MALE : Gender.FEMALE;
    }
}
