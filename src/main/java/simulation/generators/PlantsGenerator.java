package simulation.generators;

import organisms.plants.Plant;
import organisms.plants.PlantFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlantsGenerator {
    private final PlantFactory PLANT_FACTORY = new PlantFactory();

    public List<Plant> generatePlants() {
        List<Plant> plants = new CopyOnWriteArrayList<>();

        int maxPlantsPerCellUpperBound = getMaxPlantsPerCellUpperBound();
        for (int j = 0; j < ThreadLocalRandom.current().nextInt(maxPlantsPerCellUpperBound / 2 - 1, maxPlantsPerCellUpperBound + 1); j++) {
            Plant plant = PLANT_FACTORY.createPlant();
            plants.add(plant);
        }
        return plants;
    }

    private Integer getMaxPlantsPerCellUpperBound() {
        return new Plant().getMaxPlantsPerCell();
    }
}
