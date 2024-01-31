package organisms.animals.predators;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@ConfigPath(filePath = "config/properties/animals/predators/lynx.yaml")
public class Lynx extends Predator {
    private volatile static int count = 0;
    private final String UID = "LNX" + count;

    public Lynx() {
        count++;
    }
}
