package organisms.animals.herbivores;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/herbivores/hoatzin.yaml")
public class Hoatzin extends Herbivore {
    private volatile static int count = 0;
    private final String UID = "HZN" + count;

    public Hoatzin() {
        count++;
    }
}
