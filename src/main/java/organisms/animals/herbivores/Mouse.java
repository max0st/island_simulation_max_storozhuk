package organisms.animals.herbivores;

import common.annotations.ConfigPath;
import common.annotations.SupportsCatterpillarEating;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/herbivores/mouse.yaml")
@SupportsCatterpillarEating
public class Mouse extends Herbivore {
    private volatile static int count = 0;
    private final String UID = "MOS" + count;

    public Mouse() {
        count++;
    }
}
