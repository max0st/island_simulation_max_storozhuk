package organisms.animals.omnivores;

import common.annotations.ConfigPath;
import common.annotations.SupportsCatterpillarEating;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/omnivores/boar.yaml")
@SupportsCatterpillarEating
public class Boar extends Omnivore {
    private volatile static int count = 0;
    private final String UID = "BOR" + count;

    public Boar() {
        count++;
    }
}
