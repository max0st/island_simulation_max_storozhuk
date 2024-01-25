package organisms.animals;

import organisms.enums.Gender;
import organisms.interfaces.Movable;
import organisms.interfaces.Organism;

public abstract class Animal implements Organism, Movable {
    protected Gender gender;
}
