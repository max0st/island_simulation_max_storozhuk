package organisms.animals.omnivores;

import common.annotations.ConfigPath;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@ConfigPath(filePath = "config/properties/animals/omnivores/bear.yaml")
public class Bear extends Omnivore {
    private volatile static int count = 0;
    private final String UID = "BER" + count;
    @Setter
    private boolean isAsleep = false;

    public Bear() {
        count++;
    }
}
