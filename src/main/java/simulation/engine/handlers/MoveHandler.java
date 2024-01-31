package simulation.engine.handlers;

import lombok.Getter;
import organisms.animals.Animal;
import simulation.gamefield.Cell;
import simulation.gamefield.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MoveHandler {
    public void moveAllAnimals(Cell cell, GameMap gameMap) {
        for (Animal animal : cell.getAnimals()) {
            moveAnimalMultipleTimes(animal, cell, gameMap);
        }
    }

    private void moveAnimalMultipleTimes(Animal animal, Cell currentCell, GameMap gameMap) {
        int movementSpeed = animal.getMovementSpeed();
        for (int i = 0; i < movementSpeed; i++) {
            moveAnimal(animal, currentCell, gameMap);
        }
    }

    private void moveAnimal(Animal animal, Cell currentCell, GameMap gameMap) {
        Cell targetCell = getTargetCell(currentCell, gameMap);
        if (canMove(animal, targetCell)) {
            animal.move(currentCell, targetCell);
        }
    }


    private <T extends Animal> boolean canMove(T animal, Cell targetCell) {
        if (targetCell == null) {
            return false;
        }

        List<T> animalsInTargetCell = targetCell.getAnimals()
                .stream()
                .filter(animalType -> animalType.getClass().isInstance(animal))
                .map(animalType -> (T) animalType)
                .toList();

        return animalsInTargetCell.size() < animal.getMaxAnimalsPerCell();
    }


    private Cell getTargetCell(Cell currentCell, GameMap gameMap) {
        CoordinatesToMove coordinatesMove = getTargetCellXY(currentCell, gameMap);
        ArrayList<Cell> cells = gameMap.getCELLS();
        Cell targetCell = null;
        for (Cell cell : cells) {
            if (cell.getX() == coordinatesMove.getX() && cell.getY() == coordinatesMove.getY()) {
                targetCell = cell;
                break;
            }
        }
        return targetCell;
    }

    private CoordinatesToMove getTargetCellXY(Cell currentCell, GameMap gameMap) {
        int x = currentCell.getX();
        int y = currentCell.getY();
        int width = gameMap.getWIDTH();
        int height = gameMap.getHEIGHT();

        int minX = Math.max(1, x - 1);
        int minY = Math.max(1, y - 1);
        int maxX = Math.min(width, x + 1);
        int maxY = Math.min(height, y + 1);

        int newX = ThreadLocalRandom.current().nextInt(minX, maxX + 1);
        int newY = ThreadLocalRandom.current().nextInt(minY, maxY + 1);

        while (newX == x && newY == y) {
            newX = ThreadLocalRandom.current().nextInt(minX, maxX + 1);
            newY = ThreadLocalRandom.current().nextInt(minY, maxY + 1);
        }

        return new CoordinatesToMove(newX, newY);
    }


    @Getter
    private class CoordinatesToMove {
        private final int x;
        private final int y;

        public CoordinatesToMove(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
