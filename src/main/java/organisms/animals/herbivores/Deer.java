package organisms.animals.herbivores;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/herbivores/deer.yaml")
public class Deer extends Herbivore {
    private volatile static int count = 0;
    private final String UID = "DER" + count;

    public Deer() {
        count++;
    }
}
