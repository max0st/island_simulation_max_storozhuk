package util;

import org.reflections.Reflections;
import organisms.animals.Animal;
import organisms.animals.herbivores.Herbivore;
import organisms.animals.omnivores.Omnivore;
import organisms.animals.predators.Predator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalReflection {
    public Set<Class<? extends Animal>> getAnimalSubclasses() {
        return Stream.concat(getPredatorSubclasses().stream(),
                        Stream.concat(getHerbivoreSubclasses().stream(), getOmnivoreSubclasses().stream()))
                .collect(Collectors.toSet());

    }

    private Set<Class<? extends Herbivore>> getHerbivoreSubclasses() {
        String packageName = "organisms.animals.herbivores";

        Reflections reflections = new Reflections(packageName);

        return new HashSet<>(reflections.getSubTypesOf(Herbivore.class));
    }

    private Set<Class<? extends Omnivore>> getOmnivoreSubclasses() {
        String packageName = "organisms.animals.omnivores";

        Reflections reflections = new Reflections(packageName);

        return new HashSet<>(reflections.getSubTypesOf(Omnivore.class));
    }

    private Set<Class<? extends Predator>> getPredatorSubclasses() {
        String packageName = "organisms.animals.predators";

        Reflections reflections = new Reflections(packageName);

        return new HashSet<>(reflections.getSubTypesOf(Predator.class));
    }
}
