package organisms.animals.herbivores;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/herbivores/rabbit.yaml")
public class Rabbit extends Herbivore {
    private volatile static int count = 0;
    private final String UID = "RAB" + count;

    public Rabbit() {
        count++;
    }
}
