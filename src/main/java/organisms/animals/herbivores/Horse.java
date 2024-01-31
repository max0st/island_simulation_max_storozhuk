package organisms.animals.herbivores;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/herbivores/horse.yaml")
public class Horse extends Herbivore{
    private volatile static int count = 0;
    private final String UID = "HRS" + count;

    public Horse() {
        count++;
    }
}
