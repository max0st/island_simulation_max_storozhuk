package simulation.gamefield;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import organisms.animals.Animal;
import organisms.interfaces.Organism;
import organisms.plants.Plant;
import simulation.generators.AnimalsGenerator;
import simulation.generators.PlantsGenerator;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class Cell {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final AnimalsGenerator ANIMALS_GENERATOR = new AnimalsGenerator();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final PlantsGenerator PLANTS_GENERATOR = new PlantsGenerator();
    private int x;
    private int y;
    private ConcurrentHashMap<Class<? extends Organism>, CopyOnWriteArrayList<? extends Organism>> cellMap;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.cellMap = new ConcurrentHashMap<>();
        populateCell(PLANTS_GENERATOR.generatePlants(), ANIMALS_GENERATOR.generateAnimals());
    }

    public void populateCell(List<Plant> plants, List<Animal> animals) {
        this.cellMap.put(Plant.class, new CopyOnWriteArrayList<>(plants));
        this.cellMap.put(Animal.class, new CopyOnWriteArrayList<>(animals));
    }

    public CopyOnWriteArrayList<Animal> getAnimals() {
        return (CopyOnWriteArrayList<Animal>) this.cellMap.get(Animal.class);
    }

    public CopyOnWriteArrayList<Plant> getPlants() {
        return (CopyOnWriteArrayList<Plant>) this.cellMap.get(Plant.class);
    }

    public void addAnimal(Animal animal) {
        List<Animal> animalList = getAnimals();
        animalList.add(animal);
        this.cellMap.put(Animal.class, new CopyOnWriteArrayList<>(animalList));
    }


    public void removeAnimal(Animal animal) {
        List<Animal> animalList = getAnimals();
        animalList.remove(animal);
        this.cellMap.put(Animal.class, new CopyOnWriteArrayList<>(animalList));
    }

    public void addPlant(Plant plant) {
        List<Plant> plantList = getPlants();
        plantList.add(plant);
        this.cellMap.put(Plant.class, new CopyOnWriteArrayList<>(plantList));
    }

    public void removePlant(Plant plant) {
        List<Plant> plantList = getPlants();
        plantList.remove(plant);
        this.cellMap.put(Plant.class, new CopyOnWriteArrayList<>(plantList));
    }


    public CopyOnWriteArrayList<? extends Organism> getOrganisms() {
        CopyOnWriteArrayList<Organism> allOrganisms = new CopyOnWriteArrayList<>();
        allOrganisms.addAll(getAnimals());
        allOrganisms.addAll(getPlants());
        return allOrganisms;


    }
}