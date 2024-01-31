package organisms.plants;

import common.annotations.ConfigPath;
import common.enums.PlantType;
import config.PropertiesLoader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import organisms.interfaces.Organism;
import simulation.gamefield.Cell;

import java.util.List;
import java.util.Map;

@Getter
@ToString(exclude = "PROPERTIES_LOADER")
@ConfigPath(filePath = "config/properties/plants/plant.yaml")
public class Plant implements Organism {
    private final PropertiesLoader PROPERTIES_LOADER;
    private int weight;
    @Setter
    private int maxPlantsPerCell;
    @Setter
    private PlantType plantType;

    public Plant() {
        PROPERTIES_LOADER = new PropertiesLoader((this.getClass().getAnnotation(ConfigPath.class).filePath()));
        initializeFromProperties();
    }

    private void initializeFromProperties() {
        Map<String, Object> properties = PROPERTIES_LOADER.loadProperties();
        if (properties != null) {
            weight = PROPERTIES_LOADER.getPropertyValue(properties, "weight", Integer.class);
            maxPlantsPerCell = PROPERTIES_LOADER.getPropertyValue(properties, "maxPlantsPerCell", Integer.class);
        } else {
            throw new RuntimeException("Failed to load properties from YAML file.");
        }
    }

    private int countPlants(List<Plant> plants) {
        return plants.size();
    }

    @Override
    public void reproduce(Cell cell) {
        PlantFactory plantFactory = new PlantFactory();
        int currentPlantCount = countPlants(cell.getPlants());

        if (currentPlantCount < getMaxPlantsPerCell()) {
            int remainingCapacity = getMaxPlantsPerCell() - currentPlantCount;
            int childQuantity = Math.min(remainingCapacity, countPlants(cell.getPlants()) / 2);

            for (int i = 0; i < childQuantity; i++) {
                Plant plant = plantFactory.createPlant();
                cell.addPlant(plant);
            }
        }
    }
}