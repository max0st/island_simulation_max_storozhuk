package config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class PropertiesLoader {
    private final String filePath;

    public PropertiesLoader(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, Object> loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (input != null) {
                Yaml yaml = new Yaml();
                return yaml.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Integer> getAnimalChances(Map<String, Object> properties, String key) {
        return getPropertyValue(properties, key, Map.class);
    }


    public <T> T getPropertyValue(Map<String, Object> properties, String key, Class<T> type) {
        Object value = properties.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        } else {
            throw new RuntimeException("Invalid or missing property: " + key);
        }
    }
}