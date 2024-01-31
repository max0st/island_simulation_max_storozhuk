package organisms.plants;

import common.annotations.ConfigPath;
import common.enums.PlantType;
import config.PropertiesLoader;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@ConfigPath(filePath = "config/properties/plants/plantTypeProbabilities.yaml")
public class PlantFactory {
    private final PropertiesLoader PROPERTIES_LOADER = new PropertiesLoader(
            PlantFactory.class.getAnnotation(ConfigPath.class).filePath()
    );
    private final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public Plant createPlant() {
        Map<String, Object> plantTypeProbabilities = PROPERTIES_LOADER.loadProperties();

        int commonProbability = (int) plantTypeProbabilities.get("common");
        int poisonousProbability = (int) plantTypeProbabilities.get("coniferous");

        int totalProbability = commonProbability + poisonousProbability;
        int randomValue = RANDOM.nextInt(totalProbability);

        if (randomValue < commonProbability) {
            return createCommonPlant();
        } else {
            return createConiferousPlant();
        }
    }

    private Plant createCommonPlant() {
        Plant commonPlant = new Plant();
        commonPlant.setPlantType(PlantType.COMMON);
        return commonPlant;
    }


    private Plant createConiferousPlant() {
        Plant coniferous = new Plant();
        coniferous.setPlantType(PlantType.CONIFEROUS);
        return coniferous;
    }
}
