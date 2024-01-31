package organisms.animals.herbivores;

import common.annotations.ConfigPath;
import common.annotations.SupportsCatterpillarEating;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/herbivores/duck.yaml")
@SupportsCatterpillarEating
public class Duck extends Herbivore {
    private volatile static int count = 0;
    private final String UID = "DUC" + count;

    public Duck() {
        count++;
    }
}
