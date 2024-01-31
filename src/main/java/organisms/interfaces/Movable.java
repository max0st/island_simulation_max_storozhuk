package organisms.interfaces;

import simulation.gamefield.Cell;

public interface Movable {
    void move(Cell currentCell, Cell targetCell);
}
