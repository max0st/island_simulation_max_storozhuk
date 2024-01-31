package organisms.animals.predators;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@ConfigPath(filePath = "config/properties/animals/predators/boa.yaml")
public class Boa extends Predator {
    private volatile static int count = 0;
    private final String UID = "BOA" + count;

    public Boa() {
        count++;
    }
}
