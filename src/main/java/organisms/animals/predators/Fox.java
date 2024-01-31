package organisms.animals.predators;

import common.annotations.ConfigPath;
import common.annotations.SupportsCatterpillarEating;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@ConfigPath(filePath = "config/properties/animals/predators/fox.yaml")
@SupportsCatterpillarEating
public class Fox extends Predator {
    private volatile static int count = 0;
    private final String UID = "FOX" + count;

    public Fox() {
        count++;
    }
}
